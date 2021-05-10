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
public class Bus  {
    private final int id;
    private int zone;
    private int status;
    private double realPower;
    private double reactivePower;
    private double priority;

    public Bus(int id) {
        this.id = id;
    }


    public int getId() {
        return id;
    }


    public double getRealPower() {
        return realPower;
    }

    public void setRealPower(double realPower) {
        this.realPower = realPower;
    }

    public double getReactivePower() {
        return reactivePower;
    }

    public void setReactivePower(double reactivePower) {
        this.reactivePower = reactivePower;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public double getPriority() {
        return priority;
    }

    public void setPriority(double priority) {
        this.priority = priority;
    }

    public void setZone(int zone) {
        this.zone = zone;
    }

    public int getZone() {
        return zone;
    }
}
