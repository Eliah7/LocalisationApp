package com.example.application.views.illustration;

import com.example.application.dca.Main;
import com.example.application.dca.math.Matrix;

import java.util.*;
import java.util.stream.Collectors;

public class LoadNetworkState {
    public static LoadNetwork loadNetworkData() {
        LoadNetwork loadNetwork = new LoadNetwork();
        Matrix loadData = new Matrix(Main.loadData);
        Matrix lineData = new Matrix(Main.lineData);
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

//                nodeChildrenMap.get(node.getNodeNumber()).forEach(node1 -> {
//                    node1.setParent(node);
//                });
            }
        }

        // ADD NODES TO NETWORK
        loadNetwork.setNodes(nodes);

        return loadNetwork;
    }

}
