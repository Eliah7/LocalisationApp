package com.example.application.views.illustration;

import com.example.application.dca.core.Grid;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.DataProviderListener;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.shared.Registration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import rx.subjects.PublishSubject;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class LoadNetworkState implements DataProvider<Node, Integer> {
    private LoadNetworkService loadNetworkService;
    private LoadNetwork loadNetwork;
    public PublishSubject<LoadNetwork> loadNetworkObservable;

    public LoadNetworkState(@Autowired LoadNetworkService loadNetworkService) {
        this.loadNetworkService = loadNetworkService;
        this.loadNetwork = this.loadNetworkService.loadNetworkData("/Users/elia/Desktop/localisation-app/src/main/java/com/example/application/views/data/kimweri");
        this.loadNetworkObservable = PublishSubject.create();
        this.loadNetworkObservable.onNext(this.loadNetwork);

        this.loadNetworkObservable.subscribe(loadNetwork1 -> {
           this.loadNetwork = loadNetwork1;
        });
    }

    @Override
    public boolean isInMemory() {
        return true;
    }

    @Override
    public int size(Query<Node, Integer> query) {
//        this.loadNetworkState.loadNetworkObservable.
//        LoadNetworkState.loadNetworkObservable = Observable.just(this.loadNetworkState.loadNetworkData("/Users/elia/Desktop/localisation-app/src/main/java/com/example/application/views/data/bus33"));
        int limit = query.getLimit();
        int offset = query.getOffset();
        int end = loadNetwork.getNodes().size() > (offset + limit) ? (offset + limit) : loadNetwork.getNodes().size();
        return loadNetwork.getNodes().subList(offset, end).size();
    }

    @Override
    public Stream<Node> fetch(Query<Node, Integer> query) {
//        LoadNetworkState.loadNetworkObservable.subscribe(System.out::println);
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

    public Optional<Node> get(Integer nodeNumber) {
        List<Node> nodeList = loadNetwork.getNodes();

        return nodeList.stream().filter(node -> node.getNodeNumber() == nodeNumber).findAny();
    }

    @Override
    public Object getId(Node item) {
        return item.getId();
    }

    @Override
    public Registration addDataProviderListener(DataProviderListener<Node> dataProviderListener) {
        return null;
    }


    public void updateNode(Node bean) {
        List<Node> nodeList = loadNetwork.getNodes();

        Node currentNode = nodeList.stream().filter(node -> node.getNodeNumber() == bean.getNodeNumber()).findFirst().get();

        int index = nodeList.indexOf(currentNode);
        nodeList.set(index, bean);

        loadNetwork.setNodes(nodeList);

//        System.out.println(loadNetwork);
        loadNetworkObservable.onNext(loadNetwork);
    }

    public List<Node> getAll(){
        return this.loadNetwork.getNodes();
    }

    public LoadNetwork getLoadNetwork(){
        return loadNetwork;
    }
}
