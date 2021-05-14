package com.example.application.views.illustration;

import com.vaadin.componentfactory.Network;
import com.vaadin.componentfactory.model.NetworkEdgeImpl;
import com.vaadin.componentfactory.model.NetworkNodeImpl;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NetworkView extends VerticalLayout {

    private Network<NetworkNodeImpl, NetworkEdgeImpl> network = new Network<>(NetworkNodeImpl.class, NetworkEdgeImpl.class);

    // Network component for editing the template
    private VerticalLayout templateNetworkContainer = new VerticalLayout();
    private TextField labelField;

    private LoadNetworkState loadNetworkState;
    private LoadNetwork loadNetwork; // could be an observable that updates ui when state changes
    private VerticalLayout networkContainer = new VerticalLayout();

    @Autowired
    public NetworkView(LoadNetworkState loadNetworkState) {
        this.loadNetworkState = loadNetworkState;
        this.loadNetwork = this.loadNetworkState.getLoadNetwork();
        refreshNodeStatus();
        drawGraph();

        this.loadNetworkState.loadNetworkObservable.subscribe(loadNetwork1 -> {
            this.loadNetwork = loadNetwork1;
            refreshNodeStatus();
            System.out.println("2222222");
            drawGraph();
        });

    }

    private void refreshNodeStatus() {
        List<Double> powerValues = this.loadNetwork.getPowerValues();
        if (powerValues.size() == this.loadNetwork.getPowerValues().size()) {
            for (int i = 0; i < powerValues.size(); i++) {
                Node node = (Node) this.loadNetwork.getNodes().get(i);
                if (powerValues.get(i) > 1.1 || powerValues.get(i) < 0.9 || Double.isNaN(powerValues.get(i))) {
                    node.setNodeStatus(false);
                } else {
                    if (node.getParent() != null){
                        node.setNodeStatus(!node.getParent().getNodeStatus() ? false : true);
                    } else{
                        node.setNodeStatus(true);
                    }
                }
                this.loadNetwork.getNodes().set(i, node);
            }
        }
    }

    public void drawGraph() {
        network.setWidthFull();

        setPadding(false);
        setSpacing(false);

        network.setRightPanelOpened(false);
        network.setHeight("100vh");
        network.setTemplatePanelVisible(false);
        network.setLeftPanelOpened(false);
        networkContainer.addAndExpand(network);

        network.addNodes(this.loadNetwork.getNodes());
        network.addEdges(this.loadNetwork.getEdges());

        templateNetworkContainer.setPadding(false);
        templateNetworkContainer.setSpacing(false);
        add(networkContainer, templateNetworkContainer);
    }


}
