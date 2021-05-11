package com.example.application.views.illustration;

import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.DataProviderListener;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.shared.Registration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
public class LoadNetworkService implements DataProvider<Node, Integer> {
    private LoadNetworkState loadNetworkState;
    private LoadNetwork loadNetwork;

    public LoadNetworkService(@Autowired LoadNetworkState loadNetworkState) {
        this.loadNetworkState = loadNetworkState;
        this.loadNetwork = this.loadNetworkState.loadNetworkData();
    }

    @Override
    public boolean isInMemory() {
        return true;
    }

    @Override
    public int size(Query<Node, Integer> query) {
        int limit = query.getLimit();
        int offset = query.getOffset();
        int end = loadNetwork.getNodes().size() > (offset + limit) ? (offset + limit) : loadNetwork.getNodes().size();
        return loadNetwork.getNodes().subList(offset, end).size();
    }

    @Override
    public Stream<Node> fetch(Query<Node, Integer> query) {
        int limit = query.getLimit();

        int offset = query.getOffset();
        int end = loadNetwork.getNodes().size() > (offset + limit) ? (offset + limit) : loadNetwork.getNodes().size();
        return loadNetwork.getNodes().subList(offset, end).stream();
    }

    @Override
    public void refreshItem(Node node) {

    }

    @Override
    public void refreshAll() {

    }

    @Override
    public Object getId(Node item) {
        return item.getId();
    }

    @Override
    public Registration addDataProviderListener(DataProviderListener<Node> dataProviderListener) {
        return null;
    }
}
