package com.example.application.views.illustration;

import com.example.application.views.networkinformation.NetworkInformationView;
import com.vaadin.componentfactory.Network;
import com.vaadin.componentfactory.model.NetworkEdgeImpl;
import com.vaadin.componentfactory.model.NetworkNodeImpl;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class NetworkView extends VerticalLayout {

    private Network<NetworkNodeImpl, NetworkEdgeImpl> network = new Network<>(NetworkNodeImpl.class, NetworkEdgeImpl.class);

    // Network component for editing the template
    private VerticalLayout templateNetworkContainer = new VerticalLayout();
    private TextField labelField;

    private LoadNetworkService loadNetworkService;
    private LoadNetwork loadNetwork; // could be an observable that updates ui when state changes
    private VerticalLayout networkContainer = new VerticalLayout();

    @Autowired
    public NetworkView(LoadNetworkService loadNetworkService) {
        this.loadNetworkService = loadNetworkService;
        this.loadNetwork = this.loadNetworkService.getLoadNetwork();
        exampleGraph();
        this.loadNetworkService.loadNetworkObservable.subscribe(loadNetwork1 -> {
            this.loadNetwork = loadNetwork1;
            System.out.println("2222222");
            exampleGraph();
        });

    }

    private void exampleGraph() {
        network.setWidthFull();

        setPadding(false);
        setSpacing(false);

        network.setRightPanelOpened(false);
        network.setHeight("100vh");

        network.setTemplatePanelVisible(false);
        network.setLeftPanelOpened(false);
        networkContainer.addAndExpand(network);
//
//        if (!network.getNodes().isEmpty()){
//            network.deleteNodes(network.getNodes());
//        }
//        if (!network.getEdges().isEmpty()){
//            network.deleteEdges(network.getEdges());
//        }

        network.addNodes(this.loadNetwork.getNodes());
        network.addEdges(this.loadNetwork.getEdges());

        templateNetworkContainer.setPadding(false);
        templateNetworkContainer.setSpacing(false);
        add(networkContainer, templateNetworkContainer);
    }



}
