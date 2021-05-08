package com.example.application.data.service;

import com.example.application.data.entity.Node;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

@Service
public class NodeService extends CrudService<Node, Integer> {

    private NodeRepository repository;

    public NodeService(@Autowired NodeRepository repository) {
        this.repository = repository;
    }

    @Override
    protected NodeRepository getRepository() {
        return repository;
    }

}
