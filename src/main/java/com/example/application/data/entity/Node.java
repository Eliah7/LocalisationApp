package com.example.application.data.entity;

import javax.persistence.Entity;

import com.example.application.data.AbstractEntity;

@Entity
public class Node extends AbstractEntity {

    private Integer nodeNumber;
    private boolean nodeStatus;
    private Integer loadValue;

    public Integer getNodeNumber() {
        return nodeNumber;
    }
    public void setNodeNumber(Integer nodeNumber) {
        this.nodeNumber = nodeNumber;
    }
    public boolean isNodeStatus() {
        return nodeStatus;
    }
    public void setNodeStatus(boolean nodeStatus) {
        this.nodeStatus = nodeStatus;
    }
    public Integer getLoadValue() {
        return loadValue;
    }
    public void setLoadValue(Integer loadValue) {
        this.loadValue = loadValue;
    }

}
