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
            Dlf dfl = new Dlf(loadNetwork.getLoadData(), loadNetwork.getLineData(), 1);
            System.out.println("The results of powerflow " + dfl.calculate());
            List powerValues = Arrays.asList(dfl.getPowerValues());
            powerValues.stream().forEach(System.out::println);
//            System.out.println("The results of powerflow " + dfl.getPowerValues());
        });
    }

}
