package com.example.application.views.illustration;

import com.example.application.dca.core.PowerFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PowerflowService {

    public PowerflowService(@Autowired LoadNetworkService loadNetworkService){
        System.out.println("Powerflow Service created");
        loadNetworkService.loadNetworkObservable.subscribe(loadNetwork -> {
            System.out.println("The results of powerflow " + 34343);
        });
    }

}
