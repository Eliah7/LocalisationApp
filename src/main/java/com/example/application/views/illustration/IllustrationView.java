package com.example.application.views.illustration;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.example.application.views.main.MainView;
import com.vaadin.flow.component.dependency.CssImport;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "localisation/illustration", layout = MainView.class)
@PageTitle("Illustration")
@CssImport("./views/illustration/illustration-view.css")
public class IllustrationView extends Div {

    private NetworkView networkView;

    @Autowired
    public IllustrationView(NetworkView networkView) {
        addClassName("illustration-view");
        // use LoadNetworkState to rebuild the state upon change in network structure
        add(networkView);
    }
}

