package com.example.application.dca.core;


import com.example.application.dca.interfaces.PGOAnalyzer;
import com.example.application.dca.math.Complex;
import com.example.application.dca.math.ComplexMatrix;
import com.example.application.dca.math.Matrix;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PowerFlow extends PGOAnalyzer {
    private double baseMva;
    public static double defaultMva = 2;
    public static double defaultKvb = 12.66;

    public PowerFlow(Matrix busData, Matrix lineData, int centralBus) {
        this(busData, lineData, centralBus, defaultMva);
    }

    public PowerFlow(Matrix busData, Matrix lineData, int centralBus, double baseMva) {
        this.busData = busData;
        this.lineData = lineData;
        this.centralBus = centralBus;
        this.baseMva = baseMva;
    }

    static void print(Object value) {
        System.out.println(value);
    }

    public double calculate() {
        Matrix F = lineData.extract(2, 3);
        Matrix m = F.max();
        int N = (int) m.maxValue();
        double baseKv = defaultKvb;
        double zbase = (baseKv * baseKv) / baseMva;
        Matrix f = Matrix.makeVector(1, N).transpose();
        List<Number> lh = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            lh.add((double) F.position(i + 1).find().getColumnSize());
        }
        Matrix h = new Matrix(lh).transpose();
        Matrix k = f.merge(h);
        List<Integer>[] items = F.position(centralBus).findRowColumn();
        List<Integer> c = items[0];
        List<Integer> r = items[1];
        for (int i = 0; i < c.size(); i++) {
            if (r.get(i) == 2) {
                lineData = lineData.setValues(c.get(i), 2, lineData.getAt(c.get(i), 3), lineData.getAt(c.get(i), 2));
            }
        }
        Matrix nld = lineData.extractRows(c);
        lineData = lineData.removeRows(c);
        int t = (int) f.position(centralBus).find().getAt(1, 1);
        k = k.setValue(t, 2, k.getAt(t, 2) - c.size());
        int j = c.size();
        int i = 1;
        double sum = k.sumColumns(2);
        Matrix ld = lineData.extract(2, 3);
        while (k.sumColumns(2) > 0) {
            Matrix z = lineData.extract(2, 3).position(nld.getAt(i, 3));
            List<Integer>[] xe = z.findRowColumn();
            if (xe[0].size() != 0) {
                //print(xe[0]);
                Matrix b = lineData.extractRows(xe[0]);
                //print(b);
                lineData = lineData.removeRows(xe[0]);
                t = (int) f.position(nld.getAt(i, 3)).find().getAt(1, 1);
                k = k.setValue(t, 2, k.getAt(t, 2) - (xe[0].size() + 1));
                Matrix q = b.extract(3, 3).position(nld.getAt(i, 3)).find();
                if (!q.isEmpty()) {
                    int d = (int) q.getAt(1, 1);
                    b = b.setValues(d, 2, b.getAt(d, 3), b.getAt(d, 2));
                }
                nld = nld.append(j + 1, j + xe[0].size(), b);
                //print(nld);
                j = j + xe[0].size();
            } else if (xe[1].size() == 0 && k.getAt((int) nld.getAt(i, 3), 2) > 0) {
                k = k.setValue((int) nld.getAt(i, 3), 2, k.getAt((int) nld.getAt(i, 3), 2) - 1);
            }
            i++;
        }
        lineData = nld;
        List<Complex> S = new ArrayList<>();
        for (int ii = 1; ii <= busData.getRowSize(); ii++) {
            S.add(new Complex(busData.getAt(ii, 2), busData.getAt(ii, 3)).dividedBy(baseMva * 1000));
        }
        VB = new ComplexMatrix(busData.getRowSize(), 1, new Complex(1));
        List<Complex> Z = new ArrayList<>();
        for (int jj = 1; jj <= lineData.getRowSize(); jj++) {
            Z.add(new Complex(lineData.getAt(jj, 4), lineData.getAt(jj, 5)).dividedBy(zbase));
        }
        IB = new ComplexMatrix(lineData.getRowSize(), 1);
        int it = 10;
        for (int tt = 0; tt < it; tt++) {
            List<Complex> I = new ArrayList<>();
            for (int zi = 0; zi < S.size(); zi++) {
                I.add(S.get(zi).dividedBy(VB.getAt(zi + 1, 1)).conj());
            }
            for (int fi = lineData.getRowSize(); fi >= 1; fi--) {
                List<Integer>[] ce = lineData.extract(2, 3).position(lineData.getAt(fi, 3)).findRowColumn();
                if (ce[0].size() - 1 == 0) {
                    IB = IB.setValue((int) lineData.getAt(fi, 1), 1, I.get((int) lineData.getAt(fi, 3) - 1));
                } else {
                    int zi = (int) lineData.getAt(fi, 3);
                    Complex ca = I.get(zi - 1);
                    Complex cs = IB.getAt((int) lineData.getAt(ce[0].get(0), 1), 1).plus(IB.getAt((int) lineData.getAt(ce[0].get(1), 1), 1));
                    Complex cm = IB.getAt((int) lineData.getAt(fi, 1), 1);
                    Complex cx = ca.plus(cs).minus(cm);
                    IB = IB.setValue((int) lineData.getAt(fi, 1), 1, cx);
                }
            }
            //f sweep
            for (int nn = 1; nn <= lineData.getRowSize(); nn++) {
                int ii = (int) lineData.getAt(nn, 2);
                int jj = (int) lineData.getAt(nn, 1);
                int ss = (int) lineData.getAt(nn, 3);
                Complex a = VB.getAt(ii, 1);
                Complex b = IB.getAt(jj, 1).multiply(Z.get(nn - 1));
                Complex e = a.minus(b);
                VB = VB.setValue(ss, 1, e);
            }
        }

        double pls = 0;
        for (int q = 0; q < IB.getRowSize(); q++) {
            double d = IB.getAt(q + 1, 1).abs();
            pls += (Z.get(q).re * d * d);
        }
        return pls * 100000;
    }

    public ComplexMatrix getBusCurrents() {
        return IB;
    }

    public ComplexMatrix getBusVoltages() {
        return VB;
    }

    public Map<Integer, Double> getBusAbsoluteCurrents() {
        Map<Integer, Double> map = new HashMap<>();
        for (int i = 1; i <= IB.getRowSize(); i++) {
            map.put(i, IB.getAt(i, 1).abs());
        }
        return map;
    }

    public Map<Integer, Double> getBusAbsoluteVoltages() {
        Map<Integer, Double> map = new HashMap<>();
        for (int i = 1; i <= VB.getRowSize(); i++) {
            map.put(i, VB.getAt(i, 1).abs());
        }
        return map;
    }

}
