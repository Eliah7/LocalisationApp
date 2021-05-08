package com.example.application.views.illustration;

import com.vaadin.componentfactory.model.NetworkEdge;
import com.vaadin.componentfactory.model.NetworkEdgeImpl;

import java.util.ArrayList;
import java.util.List;

public class LoadNetwork {
    private List<Node> nodes;
    private List<NetworkEdgeImpl> edges;

    public LoadNetwork() {
        this.nodes = new ArrayList<>();
        this.edges = new ArrayList<>();
    }

    public LoadNetwork(List nodes) {
        this.nodes = nodes;
        this.edges = this.getEdges();
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
