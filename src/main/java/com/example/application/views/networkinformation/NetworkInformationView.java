package com.example.application.views.networkinformation;

import java.util.Optional;

import com.example.application.data.entity.Node;
import com.example.application.data.service.NodeService;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.artur.helpers.CrudServiceDataProvider;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.example.application.views.main.MainView;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.TemplateRenderer;
import com.vaadin.flow.component.checkbox.Checkbox;

@Route(value = "localisation/info/:nodeID?/:action?(edit)", layout = MainView.class)
@RouteAlias(value = "", layout = MainView.class)
@PageTitle("Network Information")
public class NetworkInformationView extends Div implements BeforeEnterObserver {

    private final String NODE_ID = "nodeID";
    private final String NODE_EDIT_ROUTE_TEMPLATE = "localisation/info/%d/edit";

    private Grid<Node> grid = new Grid<>(Node.class, false);

    private TextField nodeNumber;
    private Checkbox nodeStatus;
    private TextField loadValue;

    private Button cancel = new Button("Cancel");
    private Button save = new Button("Save");

    private BeanValidationBinder<Node> binder;

    private Node node;

    private NodeService nodeService;

    public NetworkInformationView(@Autowired NodeService nodeService) {
        addClassNames("network-information-view", "flex", "flex-col", "h-full");
        this.nodeService = nodeService;
        // Create UI
        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        // Configure Grid
        grid.addColumn("nodeNumber").setAutoWidth(true);
        TemplateRenderer<Node> nodeStatusRenderer = TemplateRenderer.<Node>of(
                "<iron-icon hidden='[[!item.nodeStatus]]' icon='vaadin:check' style='width: var(--lumo-icon-size-s); height: var(--lumo-icon-size-s); color: var(--lumo-primary-text-color);'></iron-icon><iron-icon hidden='[[item.nodeStatus]]' icon='vaadin:minus' style='width: var(--lumo-icon-size-s); height: var(--lumo-icon-size-s); color: var(--lumo-disabled-text-color);'></iron-icon>")
                .withProperty("nodeStatus", Node::isNodeStatus);
        grid.addColumn(nodeStatusRenderer).setHeader("Node Status").setAutoWidth(true);

        grid.addColumn("loadValue").setAutoWidth(true);
        grid.setDataProvider(new CrudServiceDataProvider<>(nodeService));
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeightFull();

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(String.format(NODE_EDIT_ROUTE_TEMPLATE, event.getValue().getId()));
            } else {
                clearForm();
                UI.getCurrent().navigate(NetworkInformationView.class);
            }
        });

        // Configure Form
        binder = new BeanValidationBinder<>(Node.class);

        // Bind fields. This where you'd define e.g. validation rules
        binder.forField(nodeNumber).withConverter(new StringToIntegerConverter("Only numbers are allowed"))
                .bind("nodeNumber");
        binder.forField(loadValue).withConverter(new StringToIntegerConverter("Only numbers are allowed"))
                .bind("loadValue");

        binder.bindInstanceFields(this);

        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(e -> {
            try {
                if (this.node == null) {
                    this.node = new Node();
                }
                binder.writeBean(this.node);

                nodeService.update(this.node);
                clearForm();
                refreshGrid();
                Notification.show("Node details stored.");
                UI.getCurrent().navigate(NetworkInformationView.class);
            } catch (ValidationException validationException) {
                Notification.show("An exception happened while trying to store the node details.");
            }
        });

    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<Integer> nodeId = event.getRouteParameters().getInteger(NODE_ID);
        if (nodeId.isPresent()) {
            Optional<Node> nodeFromBackend = nodeService.get(nodeId.get());
            if (nodeFromBackend.isPresent()) {
                populateForm(nodeFromBackend.get());
            } else {
                Notification.show(String.format("The requested node was not found, ID = %d", nodeId.get()), 3000,
                        Notification.Position.BOTTOM_START);
                // when a row is selected but the data is no longer available,
                // refresh grid
                refreshGrid();
                event.forwardTo(NetworkInformationView.class);
            }
        }
    }

    private void createEditorLayout(SplitLayout splitLayout) {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setClassName("flex flex-col");
        editorLayoutDiv.setWidth("400px");

        Div editorDiv = new Div();
        editorDiv.setClassName("p-l flex-grow");
        editorLayoutDiv.add(editorDiv);

        FormLayout formLayout = new FormLayout();
        nodeNumber = new TextField("Node Number");
        nodeStatus = new Checkbox("Node Status");
        nodeStatus.getStyle().set("padding-top", "var(--lumo-space-m)");
        loadValue = new TextField("Load Value");
        Component[] fields = new Component[]{nodeNumber, nodeStatus, loadValue};

        for (Component field : fields) {
            ((HasStyle) field).addClassName("full-width");
        }
        formLayout.add(fields);
        editorDiv.add(formLayout);
        createButtonLayout(editorLayoutDiv);

        splitLayout.addToSecondary(editorLayoutDiv);
    }

    private void createButtonLayout(Div editorLayoutDiv) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setClassName("w-full flex-wrap bg-contrast-5 py-s px-l");
        buttonLayout.setSpacing(true);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save, cancel);
        editorLayoutDiv.add(buttonLayout);
    }

    private void createGridLayout(SplitLayout splitLayout) {
        Div wrapper = new Div();
        wrapper.setId("grid-wrapper");
        wrapper.setWidthFull();
        splitLayout.addToPrimary(wrapper);
        wrapper.add(grid);
    }

    private void refreshGrid() {
        grid.select(null);
        grid.getDataProvider().refreshAll();
    }

    private void clearForm() {
        populateForm(null);
    }

    private void populateForm(Node value) {
        this.node = value;
        binder.readBean(this.node);

    }
}
