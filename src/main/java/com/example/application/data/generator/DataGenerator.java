package com.example.application.data.generator;

import com.vaadin.flow.spring.annotation.SpringComponent;

import com.example.application.data.service.NodeRepository;
import com.example.application.data.entity.Node;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.vaadin.artur.exampledata.DataType;
import org.vaadin.artur.exampledata.ExampleDataGenerator;

@SpringComponent
public class DataGenerator {

    @Bean
    public CommandLineRunner loadData(NodeRepository nodeRepository) {
        return args -> {
            Logger logger = LoggerFactory.getLogger(getClass());
            if (nodeRepository.count() != 0L) {
                logger.info("Using existing database");
                return;
            }
            int seed = 123;

            logger.info("Generating demo data");

            logger.info("... generating 100 Node entities...");
            ExampleDataGenerator<Node> nodeRepositoryGenerator = new ExampleDataGenerator<>(Node.class,
                    LocalDateTime.of(2021, 5, 6, 0, 0, 0));
            nodeRepositoryGenerator.setData(Node::setId, DataType.ID);
            nodeRepositoryGenerator.setData(Node::setNodeNumber, DataType.NUMBER_UP_TO_10);
            nodeRepositoryGenerator.setData(Node::setNodeStatus, DataType.BOOLEAN_50_50);
            nodeRepositoryGenerator.setData(Node::setLoadValue, DataType.NUMBER_UP_TO_100);
            nodeRepository.saveAll(nodeRepositoryGenerator.create(100, seed));

            logger.info("Generated demo data");
        };
    }

}