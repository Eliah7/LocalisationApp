package com.example.application.views.illustration;

import com.example.application.dca.Main;
import com.example.application.dca.core.Factory;
import com.example.application.dca.core.Grid;
import com.example.application.dca.math.Matrix;

import java.util.*;
import java.util.stream.Collectors;

public class LoadNetworkState {
    public static LoadNetwork loadNetworkData() {
        Double height = 100.0;
        Double width = 100.0;

        LoadNetwork loadNetwork = new LoadNetwork();
        Grid grid = Factory.loadCsvNetwork( "/Users/elia/Desktop/localisation-app/src/main/java/com/example/application/views/data/bbq-village");
        Matrix loadData = new Matrix(grid.generateBusDataArray());
        Matrix lineData = new Matrix(grid.generateLineDataArray());
        List<Node> nodes = new ArrayList<Node>();
        Map<Integer, List<Node>> nodeChildrenMap = new HashMap<>();


        // CREATE NODES
        for (int i = 1; i <= loadData.getRowSize(); i++) {
            Integer nodeNumber = (int) loadData.getAt(i, 1);
            Double load = loadData.getAt(1, 2);
            Status status = (int) loadData.getAt(1, 4) == 0 ? Status.OFF : Status.ON;

            Node node = new Node(nodeNumber, load, status);
            nodes.add(node);
        }

        // ADD CHILDREN TO NODES
        for (int i = 1; i <= lineData.getRowSize(); i++) {
            Integer nodeNumber = (int) lineData.getAt(i, 2);
            Integer mappedTo = (int) lineData.getAt(i, 3);

            if (nodeChildrenMap.containsKey(nodeNumber)) {


                try{
                    List<Node> children = nodeChildrenMap.get(nodeNumber);
                    children.add(
                            nodes.stream()
                                    .filter(node -> node.getNodeNumber().equals(mappedTo))
                                    .findAny()
                                    .orElseThrow(() -> new Exception("Node not present"))
                    );
                    nodeChildrenMap.put(nodeNumber, children);
                } catch (Exception e){
//                    e.printStackTrace();
                    System.out.println("Node absent");
                }
            } else {
                try {
                    List<Node> children = new ArrayList<>();

                    children.add(
                            nodes.stream()
                                    .filter(node -> node.getNodeNumber().equals(mappedTo))
                                    .findAny()
                                    .orElseThrow(() -> new Exception("Node not present"))
                    );
                    nodeChildrenMap.put(nodeNumber, children);
                } catch (Exception e){
//                    e.printStackTrace();
                    System.out.println("Node absent");
                }

            }
        }


        for (Node node : nodes) {
            if(nodeChildrenMap.keySet().contains(node.getNodeNumber())){
                node.setChildren(
                        nodeChildrenMap.get(
                                node.getNodeNumber()
                        ));
            }
        }

        // Draw nodes from root
        try{
            Node root = nodes.stream()
                    .filter(node -> node.getNodeNumber() == 1)
                    .findAny()
                    .orElseThrow(() -> new Exception("No node with 1"));

            Double rootX = Double.valueOf(root.getNodeNumber() - 1);
            Double rootY = Double.valueOf(-height);
            root.setX(rootX);
            root.setY(rootY);

            root = drawChildren(root, rootX, rootY);

        } catch (Exception e){
            e.printStackTrace();
        }

        // ADD NODES TO NETWORK
        loadNetwork.setNodes(nodes);

        return loadNetwork;
    }

    static Node drawChildren(Node node, Double parentX, Double parentY){
        List<Node> children = node.getChildren();
        int n = children.size();

        for(int i=0; i < children.size(); i++){
            children.get(i).setX(
                    parentX + ((n - i) + 1) * 20 * n / (i+1)
            );
            children.get(i).setY(parentY + 30);
            children.set(
                    i,
                    drawChildren(children.get(i), children.get(i).getX(), children.get(i).getY())
            );
        }

        return node;
    }

}
