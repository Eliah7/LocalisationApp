package com.example.application.views.illustration;

import com.vaadin.componentfactory.model.NetworkEdgeImpl;
import com.vaadin.componentfactory.model.NetworkNodeImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class Node extends NetworkNodeImpl {
    public static final int SPACE = 10;
    private Integer nodeNumber;
    private Double load;
    private Status status;
    private List<Node> children;

    public Node(Integer nodeNumber, Double load) {
        this.setId(UUID.randomUUID().toString());
        this.setLabel(nodeNumber.toString());
        this.setX(nodeNumber * Math.random() * SPACE);
        this.setY(nodeNumber * Math.random() * SPACE);
        this.status = Status.ON;
        this.setColor();
        this.load = load;
        this.children = new ArrayList<>();
    }

    public Node(Integer nodeNumber, Double load, Status status) {
        this.nodeNumber = nodeNumber;
        this.load = load;
        this.status = status;
        this.setX(nodeNumber * Math.random() * SPACE);
        this.setY(nodeNumber * Math.random() * SPACE);
        this.children = new ArrayList<>();
        this.setColor();
    }

    public Node(Integer nodeNumber, Double load, Status status, Double X, Double Y) {
        this.nodeNumber = nodeNumber;
        this.load = load;
        this.status = status;
        this.setX(X);
        this.setY(Y);
        this.children = new ArrayList<>();
        this.setColor();
    }

    public Node(Double load, List<Node> children) {
        this.load = load;
        this.children = children;
        this.status = Status.ON;
        this.setColor();
    }

    private void setColor(){
        this.setComponentColor(status == Status.ON ? ComponentColor.BLUE : ComponentColor.RED);
    }

    public Double getLoad() {
        return load;
    }

    public void setLoad(Double load) {
        this.load = load;
    }

    public void addChild(Node child){
        children.add(child);
    }

    public List<Node> getChildren() {
        return children;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }
}
