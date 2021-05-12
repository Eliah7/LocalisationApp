package com.example.application.views.illustration;

import com.vaadin.componentfactory.model.NetworkEdgeImpl;
import com.vaadin.componentfactory.model.NetworkNodeImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class Node extends NetworkNodeImpl {
    public static final int SPACE = 30;
    private Integer nodeNumber;
    private Double load;
    private Status status;
    private Boolean nodeStatus;
    private List<Node> children;
    private Node parent;

    public Node(Integer nodeNumber, Double load) {
        this.setId(UUID.randomUUID().toString());
        this.setLabel(nodeNumber.toString());
        this.nodeNumber = nodeNumber;
        this.setX(nodeNumber * Math.random() * SPACE);
        this.setY(nodeNumber * Math.random() * SPACE);
        this.status = Status.ON;
        this.nodeStatus = this.status == Status.ON;
        this.setColor();
        this.load = load;
        this.children = new ArrayList<>();
    }

    public Node(Integer nodeNumber, Double load, Status status) {
        this.setLabel(nodeNumber.toString());
        this.nodeNumber = nodeNumber;
        this.setId(UUID.randomUUID().toString());
        this.load = load;
        this.status = status;
        this.nodeStatus = this.status == Status.ON;

//        this.setX(nodeNumber * Math.random() * SPACE);
//        this.setY(nodeNumber * Math.random() * SPACE);
        this.children = new ArrayList<>();
        this.setColor();
    }

    public Node(Integer nodeNumber, Double load, Status status, Double X, Double Y) {
        this.nodeNumber = nodeNumber;
        this.load = load;
        this.status = status;
        this.nodeStatus = this.status == Status.ON ;
        this.setX(X);
        this.setY(Y);
        this.children = new ArrayList<>();
        this.setColor();
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    private void setColor(){

        this.setComponentColor(status == Status.ON ? ComponentColor.RED : ComponentColor.RED);
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

    public Integer getNodeNumber() {
        return nodeNumber;
    }

    public static int getSPACE() {
        return SPACE;
    }

    public Status getStatus() {
        return status;
    }

    public Boolean getNodeStatus() {
        return nodeStatus;
    }

    public void setNodeNumber(Integer nodeNumber) {
        this.nodeNumber = nodeNumber;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setNodeStatus(Boolean nodeStatus) {
        this.nodeStatus = nodeStatus;
    }

    public static boolean isNodeStatus(Node node) {
        return node.getNodeStatus();
    }
}
