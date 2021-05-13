package com.example.application.views.illustration;

import com.example.application.dca.core.Grid;
import com.example.application.dca.math.Matrix;
import com.vaadin.componentfactory.model.NetworkEdge;
import com.vaadin.componentfactory.model.NetworkEdgeImpl;
import com.vaadin.componentfactory.model.NetworkNodeImpl;

import java.util.ArrayList;
import java.util.List;

public class LoadNetwork {
    private List<Node> nodes;
    private List<NetworkEdgeImpl> edges;
    private Grid grid;

    public Grid getGrid() {
        return grid;
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    public LoadNetwork() {
        this.nodes = new ArrayList<>();
        this.edges = new ArrayList<>();
    }

    public LoadNetwork(List nodes) {
        this.nodes = nodes;
        this.edges = this.getEdges();
    }

    public Matrix getLoadData(){
        Matrix loadData = new Matrix(grid.generateBusDataArray());
        for (int i = 1; i < loadData.getRowSize(); i++) {
            loadData.setAt(i, 2, nodes.get(i).getLoad());

            loadData.setAt(i, 3, nodes.get(i).getNodeStatus() ? 1 : 0);
        }

        return loadData;
    }

    public Matrix getLineData(){
        Matrix lineData = new Matrix(grid.generateLineDataArray());

        return lineData;
    }

    public List getNodes() {
        return nodes;
    }

    public void setNodes(List nodes) {
        this.nodes = nodes;
    }

    public void addNode(Node node) {
        this.nodes.add(node);
    }

    public List<NetworkEdgeImpl> getEdges() {
        this.nodes.stream().forEach(
                node -> {
                    node.getChildren().stream().forEach(
                            child -> {
                                NetworkEdgeImpl edge = new NetworkEdgeImpl();
                                edge.setFrom(node.getId());
                                edge.setTo(child.getId());
                                this.edges.add(edge);
                            }
                    );
                }
        );
        return this.edges;
    }
}
