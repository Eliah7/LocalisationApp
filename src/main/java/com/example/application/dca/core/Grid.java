package com.example.application.dca.core;
/*
 * © Udsm All rights reserved.
 *
 *
 * Date: 7/22/2020
 * Time: 10:27 AM
 */

import com.example.application.dca.math.Matrix;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ramadhan Juma <ramaj93@yahoo.com>
 * @since 0.0.1
 */
public class Grid{
    private final Map<Integer, Bus> nodes = new HashMap<>();
    private final Map<Integer, Line> lines = new HashMap<>();
    private int activeNodes = 0;
    private int lineIndex = 1;

    private int status = 0;
    private ChangeListener changeListener;
    private double availablePower;
    private double baseMva = 0;
    private double baseKva = 0;

    public static final int STATUS_OK = 0;
    public static final int REQUEST_POWER = 1;
    public static final int RETURN_POWER = 2;
    public static final int SHEDDING_LOAD = 3;
    public static final int ACCEPTING_LOAD = 4;
    public static final int GRANT_POWER = 5;
    public static final int AVAILABLE_POWER = 6;


    public interface ChangeListener {
        void onChange(Grid grid);
    }

    public Grid() {

    }


    public void setBaseKva(double baseKva) {
        this.baseKva = baseKva;
    }

    public void setBaseMva(double baseMva) {
        this.baseMva = baseMva;
    }


//    public double getAvailablePower() {
//        return getPower() - getLoad();
//    }


    public Grid addNode(int id, Bus node) {
        nodes.put(id, node);
        return this;
    }

    public Line connect(int from, int to, double resistance, double reactance) {
        Line line = new Line(lineIndex, from, to, this);
        line.setResistance(resistance);
        line.setReactance(reactance);
        lines.put(lineIndex, line);
        lineIndex++;
        return line;
    }

    public int getNodeSize() {
        return nodes.size();
    }

    public int getLineIndex() {
        return lineIndex;
    }

    public void switchStatus(int id, int status) {
        nodes.get(id).setStatus(status);
    }

    public int getNodeStatus(int index) {
        return nodes.get(index).getStatus();
    }

    public double getNodeResistance(int index) {
        return nodes.get(index).getRealPower();
    }

    public double getNodeReactance(int index) {
        return nodes.get(index).getStatus();
    }

//    public int getId() {
//        return id;
//    }

    public int getActiveNodes() {
        return activeNodes;
    }

    public double getLoad() {
        double load = 0;
        for (int i = 1; i <= nodes.size(); i++) {
            load += nodes.get(i).getRealPower();
        }
        return load;
    }

    private Matrix generateBusDataMatrix() {
        Matrix matrix = new Matrix(nodes.size(), 5);
        for (int id : nodes.keySet()) {
            Bus bus = nodes.get(id);
            matrix.setAt(bus.getId(), 1, bus.getId());
            matrix.setAt(bus.getId(), 2, bus.getRealPower());
            matrix.setAt(bus.getId(), 3, bus.getReactivePower());
            matrix.setAt(bus.getId(), 4, bus.getStatus());
            matrix.setAt(bus.getId(), 5, bus.getPriority());
        }
        return matrix;
    }

    private Matrix generateLineDataMatrix() {
        Matrix matrix = new Matrix(lines.size(), 5);
        for (int id : lines.keySet()) {
            Line bus = lines.get(id);
            matrix.setAt(bus.getId(), 1, bus.getId());
            matrix.setAt(bus.getId(), 2, bus.getInBusId());
            matrix.setAt(bus.getId(), 3, bus.getOutBusId());
            matrix.setAt(bus.getId(), 4, bus.getResistance());
            matrix.setAt(bus.getId(), 5, bus.getReactance());
        }
        return matrix;
    }

    public double[][] generateBusDataArray() {
        double[][] matrix = new double[nodes.size()][5];
        int index = 0;
        for (int id : nodes.keySet()) {
            Bus bus = nodes.get(id);
            matrix[index][0] = bus.getId();
            matrix[index][1] = bus.getRealPower();
            matrix[index][2] = bus.getReactivePower();
            matrix[index][3] = bus.getStatus();
            matrix[index][4] = bus.getPriority();
            index++;
        }
        return matrix;
    }

    public double[][] generateLineDataArray() {
        double[][] matrix = new double[lines.size()][5];
        int index = 0;
        for (int id : lines.keySet()) {
            Line bus = lines.get(id);
            matrix[index][0] = bus.getId();
            matrix[index][1] = bus.getInBusId();
            matrix[index][2] = bus.getOutBusId();
            matrix[index][3] = bus.getResistance();
            matrix[index][4] = bus.getReactance();
            index++;
        }
        return matrix;
    }

    public double calculatePowerLoss() {
        Dlf pf = new Dlf(generateBusDataMatrix(), generateLineDataMatrix(), (int) baseMva, baseKva, 1);
        return pf.calculate();
    }


    public Double runPowerFlow() {
        PowerFlow pf = new PowerFlow(generateBusDataMatrix(), generateBusDataMatrix(), 1);
        double pl = pf.calculate();
        return pl;
    }

    public Bus getBusAt(Integer index) {
        return nodes.get(index);
    }

    public Line getLineAt(int index) {
        return lines.get(index);
    }


//    public void setBusRealPower(int index, double resistance) {
//        Bus line = getBusAt(index);
//        if (line != null) {
//            line.setRealPower(resistance);
//            optimizeWithStatus();
//            if (changeListener != null) {
//                changeListener.onChange(this);
//            }
//        }
//    }
//
//    public void setBusReactivePower(int index, double reactance) {
//        Bus line = getBusAt(index);
//        if (line != null) {
//            line.setReactivePower(reactance);
//            optimizeWithStatus();
//            if (changeListener != null) {
//                changeListener.onChange(this);
//            }
//        }
//    }

}
