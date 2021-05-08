package com.example.application.dca.core;

public class Line {
    private int index;
    private int id;
    private int parentId;
    private double resistance;
    private double reactance;
    private double load;
    private double distance;
    private int priority = 0;
    private double voltage = 0;
    private double realPower = -1;

    private double reactivePower = -1;

    public Line(String lineData) {
        String[] parts = lineData.split("\t");
        for (int i = 0; i < parts.length; i++) {
            String data = parts[i];
            switch (i) {
                case 0:
                    index = Integer.parseInt(data);
                    break;
                case 1:
                    id = Integer.parseInt(data);
                    break;
                case 2:
                    parentId = Integer.parseInt(data);
                    break;
                case 3:
                    resistance = Double.parseDouble(data);
                    break;
                case 4:
                    reactance = Double.parseDouble(data);
                    break;
                case 5:
                    distance = Double.parseDouble(data);
                    break;
                case 6:
                    load = Double.parseDouble(data);
                    break;
                default:
                    break;
            }
        }
    }

    public Line(int id, int parentId, double resistance, double reactance) {
        this.id = id;
        this.parentId = parentId;
        this.resistance = resistance;
        this.reactance = reactance;
    }

    public Line(int id, int parentId) {
        this(id, parentId, 0, 0);
    }

    public Line(int id) {
        this(id, -1);
    }

    public int getIndex() {
        return index;
    }

    public int getId() {
        return id;
    }

    public int getParentId() {
        return parentId;
    }

    public void setLoad(double load) {
        this.load = load;
    }

    public double getResistance() {
        return resistance;
    }

    public double getReactance() {
        return reactance;
    }

    public double getLoad() {
        return load;
    }

    public double getDistance() {
        return distance;
    }

    public double getRealPower() {
        return realPower;
    }

    public double getReactivePower() {
        return reactivePower;
    }

    public int getType() {
        if (parentId == -1) {
            return 1;
        } else if (realPower != -1 && reactivePower != -1) {
            return 0;
        }
        return 2;
    }
}
