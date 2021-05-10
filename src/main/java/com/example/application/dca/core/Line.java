package com.example.application.dca.core;
/*
 * © Udsm All rights reserved.
 *
 *
 * Date: 7/22/2020
 * Time: 8:07 AM
 */

/**
 * @author Ramadhan Juma <ramaj93@yahoo.com>
 * @since 0.0.1
 */
public class Line {
    private final int id;
    private int inBusId;
    private int outBusId;
    private double resistance;
    private double reactance;
    private Bus inBus;
    private Bus outBus;
    private Grid grid;

    public Line(int id, int in, int out, Grid grid) {
        this.id = id;
        this.inBusId = in;
        this.outBusId = out;
        this.grid = grid;
    }


    public int getId() {
        return id;
    }

    public int getInBusId() {
        return inBusId;
    }

    public void setInBusId(int inBusId) {
        this.inBusId = inBusId;
    }

    public int getOutBusId() {
        return outBusId;
    }

    public void setOutBusId(int outBusId) {
        this.outBusId = outBusId;
    }

    public double getResistance() {
        return resistance;
    }

    public void setResistance(double resistance) {
        this.resistance = resistance;
    }

    public double getReactance() {
        return reactance;
    }

    public void setReactance(double reactance) {
        this.reactance = reactance;
    }

    public Grid getGrid() {
        return grid;
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }
}
