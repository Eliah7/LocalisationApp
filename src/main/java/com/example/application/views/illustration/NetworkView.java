package com.example.application.views.illustration;

import com.hs18.vaadin.addon.graph.GraphJSComponent;
import com.hs18.vaadin.addon.graph.listener.GraphJsLeftClickListener;
import com.vaadin.componentfactory.Network;
import com.vaadin.componentfactory.model.NetworkEdgeImpl;
import com.vaadin.componentfactory.model.NetworkNodeImpl;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class NetworkView extends VerticalLayout {

    private Network<NetworkNodeImpl, NetworkEdgeImpl> network = new Network<>(NetworkNodeImpl.class,NetworkEdgeImpl.class);

    // Network component for editing the template
    private VerticalLayout templateNetworkContainer = new VerticalLayout();
    private TextField labelField;
    private VerticalLayout networkContainer = new VerticalLayout();

    public NetworkView() {
        exampleGraph();
    }

    private void exampleGraph() {
        setPadding(false);
        setSpacing(false);
        setSizeFull();
        network.setWidthFull();
//        network.addNetworkUpdateTemplateListener(event -> openTemplateEditor(event.getTemplate()));
        networkContainer.add(new H3("Edit Network"));
        networkContainer.addAndExpand(network);

        templateNetworkContainer.setPadding(false);
        templateNetworkContainer.setSpacing(false);
        add(networkContainer, templateNetworkContainer);
    }

}
