package com.example.application.data.service;

import com.example.application.data.entity.Node;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NodeRepository extends JpaRepository<Node, Integer> {

}