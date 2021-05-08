package com.example.application.views.illustration;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.example.application.views.main.MainView;
import com.vaadin.flow.component.dependency.CssImport;

@Route(value = "localisation/illustration", layout = MainView.class)
@PageTitle("Illustration")
@CssImport("./views/illustration/illustration-view.css")
public class IllustrationView extends Div {

    public IllustrationView() {
        addClassName("illustration-view");
        LoadNetwork loadNetwork = LoadNetworkState.loadNetworkData(); // use LoadNetworkState to rebuild the state upon change in network structure
        add(new NetworkView(loadNetwork));
    }
}

