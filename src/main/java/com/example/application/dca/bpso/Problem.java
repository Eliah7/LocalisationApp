package com.example.application.dca.bpso;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.example.application.dca.Main;
import com.example.application.dca.core.PowerFlow;
import com.example.application.dca.math.Matrix;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

/**
 * @author Merve
 */
public class Problem {

    String name;
    double result = 0;
    int dimension;
    double[] values;
    double[] weights;
    private double maxCapacity;
    Item[] items;

    public Problem(Integer dimension, Item[] items, double maxCapacity) {
        this.items = items;
        this.dimension = dimension;
        this.maxCapacity = maxCapacity;
    }


    double getFitness(Particle p) {
        return solveProblem(p);
    }

    double[][] deepCopy(double[][] data) {
        double[][] d = new double[data.length][];
        for (int i = 0; i < data.length; i++) {
            d[i] = Arrays.copyOf(data[i], data[i].length);
        }
        return d;
    }

    double solveProblem(Particle particle) {
        double[][] ld = deepCopy(Main.lineData);
        double[][] bd = deepCopy(Main.loadData);
        double MVab = PowerFlow.defaultMva * 1000;
        double demand = 0;

        for (int i = 0; i < particle.position.length; i++) {
            bd[i][4] = particle.position[i];
            if (bd[i][4] == 0) {
                demand += bd[i][1];
                bd[i][1] = 0;
                bd[i][2] = 0;
            }
        }
        if (MVab - demand > 0) {
            double val = maxLoadObjective(bd);
            Map<Integer, Double> d = runPf(bd, ld);
            double max = max(d.values());
            double min = min(d.values());
            if (min > 0.9 && max < 1.1) {
                return val;
            }
        }
        return 9000000000.00;
    }

    double max(Collection<Double> d) {
        double val = -1;
        for (Double i : d) {
            if (i > val) {
                val = i;
            }
        }
        return val;
    }

    double min(Collection<Double> d) {
        double val = 9000000000.00;
        for (Double i : d) {
            if (i < val) {
                val = i;
            }
        }
        return val;
    }

    Map<Integer, Double> runPf(double[][] bd, double[][] ld) {
        PowerFlow pf = new PowerFlow(new Matrix(bd), new Matrix(ld), 2);
        pf.calculate();
        return pf.getBusAbsoluteVoltages();
    }

    double maxLoadObjective(double[][] ld) {
        double ret = 0;
        for (int i = 0; i < ld.length; i++) {
            double p = ld[i][1];
            double n = ld[i][4];
            double pr = ld[i][5];
            ret += (p * n * pr * pr);
        }

        return ret;
    }

    double pfObjective(double[][] bd, double[][] ld) {
        double fitnessResult = 0;
        //System.out.println();
        PowerFlow pf = new PowerFlow(new Matrix(bd), new Matrix(ld), 2);
        double pl = pf.calculate();
        Map<Integer, Double> v = pf.getBusAbsoluteVoltages();
        System.out.println("Pf:" + pl);
        for (int i : v.keySet()) {
            double iv = v.get(i);
            if (iv > 0.95 && iv < 1.05) {
                fitnessResult = pl;
            } else {
                fitnessResult = 9000000000.00;
            }
        }

        return fitnessResult;
    }

}
