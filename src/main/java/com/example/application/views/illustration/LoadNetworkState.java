package com.example.application.views.illustration;

import com.example.application.dca.Main;
import com.example.application.dca.math.Matrix;

public class LoadNetworkState {
    public static LoadNetwork loadNetworkData() {
        LoadNetwork loadNetwork = new LoadNetwork();
        Matrix loadData = new Matrix(Main.loadData);


        // CREATE NODES
        for(int i = 1; i <= loadData.getColumnSize(); i++){
            Integer nodeNumber = (int) loadData.getAt(i, 1);
            Double load = loadData.getAt(1, 2);
            Node node = new Node(nodeNumber, load);
            loadNetwork.addNode(node);
        }

        Node node1 = new Node(1, 343.3);
        Node node2 = new Node(2, 33.3);
        Node node3 = new Node(3, 33.3);

        // ADD CHILDREN TO NODES
        node1.addChild(node2);
        node2.addChild(node3);
        node3.addChild(node1);

        // ADD NODES TO NETWORK
        loadNetwork.addNode(node1);
        loadNetwork.addNode(node2);
        loadNetwork.addNode(node3);

        return loadNetwork;
    }
}
