package com.example.application.views.illustration;

import com.vaadin.componentfactory.Network;
import com.vaadin.componentfactory.model.NetworkEdgeImpl;
import com.vaadin.componentfactory.model.NetworkNodeImpl;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

import java.util.Collection;

public class NetworkView extends VerticalLayout {

    private Network<NetworkNodeImpl, NetworkEdgeImpl> network = new Network<>(NetworkNodeImpl.class, NetworkEdgeImpl.class);

    // Network component for editing the template
    private VerticalLayout templateNetworkContainer = new VerticalLayout();
    private TextField labelField;
    private LoadNetwork loadNetwork; // could be an observable that updates ui when state changes
    private VerticalLayout networkContainer = new VerticalLayout();

    public NetworkView() {

        exampleGraph();
    }

    private void exampleGraph() {
        network.setWidthFull();
        this.loadNetwork = LoadNetworkState.loadNetworkData(); ;
        setPadding(false);
        setSpacing(false);
        setSizeFull();

//        network.addNetworkUpdateTemplateListener(event -> openTemplateEditor(event.getTemplate()));
        networkContainer.add(new H3("Edit Network"));
        network.setRightPanelOpened(false);
        network.setLeftPanelOpened(false);
        networkContainer.addAndExpand(network);
        network.addNodes(this.loadNetwork.getNodes());
        network.addEdges(this.loadNetwork.getEdges());
        templateNetworkContainer.setPadding(false);
        templateNetworkContainer.setSpacing(false);
        add(networkContainer, templateNetworkContainer);
    }



}
