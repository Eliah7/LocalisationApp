/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.application.dca.bpso;

/**
 * @author FSMBLM1
 */
public class Item {
    double value;
    double weight;

    public Item(double value, double weight) {
        this.value = value;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "value: " + value + " weight: " + weight;
    }


}
