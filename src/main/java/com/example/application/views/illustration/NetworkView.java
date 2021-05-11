package com.example.application.views.illustration;

import com.vaadin.componentfactory.Network;
import com.vaadin.componentfactory.model.NetworkEdgeImpl;
import com.vaadin.componentfactory.model.NetworkNodeImpl;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

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
        exampleGraph();
    }

    private void exampleGraph() {
        network.setWidthFull();
        this.loadNetwork = loadNetworkState.loadNetworkData(); ;
        setPadding(false);
        setSpacing(false);
        network.setRightPanelOpened(false);
        network.setHeight("100vh");
//        network.setWidth("100%");
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
