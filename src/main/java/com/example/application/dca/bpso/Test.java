/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.application.dca.bpso;

/**
 * @author Merve
 */
public class Test {

    public static void main(String[] args) {
        Item items[] = new Item[33];
        double values[] = {0, 500, 450, 1200, 300, 60, 1000, 200, 60, 60, 225, 600, 60, 600, 60, 60, 60, 450, 450, 900, 900, 450, 90, 2100, 60, 60, 300, 1200, 200, 750, 210, 300,300};
        double weights[] = {0, 500, 450, 1200, 300, 60, 1000, 200, 60, 60, 225, 600, 60, 600, 60, 60, 60, 450, 450, 900, 900, 450, 90, 2100, 60, 60, 300, 1200, 200, 750, 210, 300,300};
        double maxCapacity = 15;

        for (int i = 0; i < items.length; i++) {
            Item item = new Item(values[i], weights[i]);
            items[i] = item;
            //  System.out.println(item);
        }

        Problem p = new Problem(33, items, maxCapacity);
        PSO psoForProblem = new PSO(33, p, 100);
        psoForProblem.solve();
    }
}
