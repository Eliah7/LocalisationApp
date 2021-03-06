package com.example.application.views.illustration;

import com.example.application.dca.core.Dlf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class DLFService {

    public DLFService(@Autowired LoadNetworkState loadNetworkState){
        System.out.println("Powerflow Service created");

        loadNetworkState.loadNetworkObservable.subscribe(loadNetwork -> {
            loadNetwork.getPowerValues().stream().forEach(System.out::println);
            // TODO: Set status of nodes and update nodeStatus
//            System.out.println("The results of powerflow " + dfl.getPowerValues());
        });
    }

}
