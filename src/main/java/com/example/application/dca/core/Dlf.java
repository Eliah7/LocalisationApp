package com.example.application.dca.core;
/*
 * © CoreIT All rights reserved.
 *
 *
 * Date: 5/21/2020
 * Time: 9:07 AM
 */

import com.example.application.dca.interfaces.PGOAnalyzer;
import com.example.application.dca.math.Complex;
import com.example.application.dca.math.ComplexMatrix;
import com.example.application.dca.math.Matrix;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//import py4j.GatewayServer;

/**
 * @author Ramadhan Juma <ramaj93@yahoo.com>
 * @since 0.0.1
 */
public class Dlf extends PGOAnalyzer {

    public Matrix powerValues;

    public Dlf(Matrix busData, Matrix lineData, int centralBus) {
        this.busData = busData;
        this.lineData = lineData;
        this.centralBus = centralBus;
        this.baseMva = 100.0;
        this.baseKva = 12.66;
    }

    public Dlf(Matrix busData, Matrix lineData, int centralBus, double baseMva, double baseKva) {
        this.busData = busData;
        this.lineData = lineData;
        this.centralBus = centralBus;
        this.baseKva = baseKva;
        this.baseMva = baseMva;
    }

    public Dlf(){

    }

    public void setBaseKva(Double baseKva){
        this.baseKva = baseKva;
    }

    public void setBaseMva(Double baseMva){
        this.baseMva = baseMva;
    }

    public void setBusData(double[][] busData){
        this.busData = new Matrix(busData);
    }

    public void setCentralBus(int centralBus){
        this.centralBus = centralBus;
    }

    public void setLineData(double[][] lineData){
        this.lineData = new Matrix(lineData);
    }

    public static void main(String[] args){
        Dlf app = new Dlf();
//        GatewayServer server = new GatewayServer(app);
//        server.start();
    }

    synchronized public double calculate() {
        Matrix F = lineData.extract(2, 3);
        Matrix M = F.max();
        int N = (int) M.maxValue();
//        double baseMva = 100;
//        double baseKv = 12.66;
        double Zbase = (this.baseKva * this.baseKva) / this.baseMva;
        ComplexMatrix R = new ComplexMatrix(lineData.extract(4, 4)).divide(Zbase);
        ComplexMatrix X = new ComplexMatrix(lineData.extract(5, 5)).divide(Zbase);
        Complex cj = new Complex(0, 1);
        ComplexMatrix Z = R.sum(X.multiply(cj));
        Matrix fr = lineData.extract(2, 2);
        Matrix to = lineData.extract(3, 3);
        int nl = fr.getRowSize();
        Matrix f = Matrix.makeVector(1, N).transpose();
        List<Number> lh = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            lh.add((double) F.position(i + 1).find().getColumnSize());
        }

        Matrix h = new Matrix(lh);
        Matrix k = new Matrix(f.getRowSize(), 2);
        k.copyColumn(f, 1).copyColumn(h.transpose(), 2);
        List<Integer>[] items = F.position(centralBus).findRowColumn();
        List<Integer> c = items[0];
        List<Integer> r = items[1];
        Matrix NLD = lineData.extractRows(c);
        lineData = lineData.removeRows(c);
        int t = (int) f.position(centralBus).find().getAt(1, 1);
        k = k.setValue(t, 2, k.getAt(t, 2) - c.size());
        int j = c.size();
        int i = 1;
        double sum = k.sumColumns(2);
        Matrix ld = lineData.extract(2, 3);

        while (k.sumColumns(2) > 0) {
            Matrix z = lineData.extract(2, 3).position(NLD.getAt(i, 3));
            List<Integer>[] xe = z.findRowColumn();
            if (xe[0].size() != 0) {
                //print(xe[0]);
                Matrix b = lineData.extractRows(xe[0]);
                //print(b);
                lineData = lineData.removeRows(xe[0]);
                t = (int) f.position(NLD.getAt(i, 3)).find().getAt(1, 1);
                k = k.setValue(t, 2, k.getAt(t, 2) - (xe[0].size() + 1));
                Matrix q = b.extract(3, 3).position(NLD.getAt(i, 3)).find();
                if (!q.isEmpty()) {
                    int d = (int) q.getAt(1, 1);
                    b = b.setValues(d, 2, b.getAt(d, 3), b.getAt(d, 2));
                }
                NLD = NLD.append(j + 1, j + xe[0].size(), b);
                //print(nld);
                j = j + xe[0].size();
            } else if (xe[1].size() == 0 && k.getAt((int) NLD.getAt(i, 3), 2) > 0) {
                k = k.setValue((int) NLD.getAt(i, 3), 2, k.getAt((int) NLD.getAt(i, 3), 2) - 1);
            }
            i++;
        }

        lineData = NLD;
        Matrix bibc = new Matrix(lineData.getRowSize(), lineData.getRowSize());
        for (int ii = 1; ii <= lineData.getRowSize(); ii++) {
            int pos = (int) lineData.getAt(ii, 3) - 1;
            if (lineData.getAt(ii, 2) == 1d) {
                bibc.setAt(pos, pos, 1);
            } else {
                int pos2 = (int) lineData.getAt(ii, 2) - 1;
                bibc.copyColumn(bibc, pos2, pos);
                bibc.setAt(pos, pos, 1);
            }
        }

        ComplexMatrix BCBV = bibc.transpose().multiply(Z.diag());
        ComplexMatrix S = new ComplexMatrix(busData, 2, 3).divide(baseMva * 1000);
        ComplexMatrix Vo = new ComplexMatrix(lineData.getRowSize(), 1, new Complex(1));
        ComplexMatrix TRX = BCBV.multiply(bibc);

        S = S.removeRow(1);
        int iteration = 10;
        ComplexMatrix IB = null;
        ComplexMatrix VB = (ComplexMatrix) Vo.clone();
        for (int q = 0; q < iteration; q++) {
            ComplexMatrix I = S.divide(VB).conjugate();
            IB = bibc.multiply(I);
            VB = Vo.minus(TRX.multiply(I));
        }
        IB.getColumnSize();

        ComplexMatrix Vbus = VB.append(new Complex(1));

        ComplexMatrix VFT = new ComplexMatrix(nl, 1);

        for (int l = 1; l <= nl; l++) {
            VFT.setAt(l, 1, Vbus.getAt((int) fr.getAt(l, 1), 1).minus(Vbus.getAt((int) to.getAt(l, 1), 1)));
        }

        Matrix vft = VFT.abs();
        this.powerValues = Vbus.abs();
        double s = 1e5;
        ComplexMatrix Pbrloss = (((vft.vectorMultiply(vft)).vectorMultiply(R)).divide(Z.abs().vectorMultiply(Z.abs()))).multiply(1e5);
        Complex cg = Pbrloss.sumColumns(1);

        return cg.abs();
    }

    public Double[] getPowerValues() {
        double[][] data = powerValues.getData();
        Double[] powerValues = new Double[data.length];

        for (int i = 0; i < data.length ; i++) {
            powerValues[i] = data[i][0];
//            System.out.println(powerValues[i]);
        }
        return powerValues;
    }
}