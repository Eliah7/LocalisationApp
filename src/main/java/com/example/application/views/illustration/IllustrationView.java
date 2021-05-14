package com.example.application.views.illustration;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.example.application.views.main.MainView;
import com.vaadin.flow.component.dependency.CssImport;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "localisation/illustration", layout = MainView.class)
@PageTitle("Illustration")
@CssImport("./views/illustration/illustration-view.css")
@PreserveOnRefresh
public class IllustrationView extends Div {
    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        networkView.drawGraph();
    }

    private NetworkView networkView;

    @Autowired
    public IllustrationView(@Autowired NetworkView networkView) {
        this.networkView = networkView;
        addClassName("illustration-view");
        // use LoadNetworkState to rebuild the state upon change in network structure
        add(this.networkView);
    }
}

