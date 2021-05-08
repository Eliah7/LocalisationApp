package com.example.application.dca;

import com.example.application.dca.core.Dlf;
import com.example.application.dca.core.PowerFlow;
import com.example.application.dca.math.Matrix;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class Main {

    public static double[][] lineData = {
            {1, 1, 2, 0.0922, 0.0470},
            {2, 2, 3, 0.4930, 0.2511},
            {3, 3, 4, 0.3660, 0.1864},
            {4, 4, 5, 0.3811, 0.1941},
            {5, 5, 6, 0.8190, 0.7070},
            {6, 6, 7, 0.1872, 0.6188},
            {7, 7, 8, 0.7114, 0.2351},
            {8, 8, 9, 1.0300, 0.7400},
            {9, 9, 10, 1.0440, 0.7400},
            {10, 10, 11, 0.1966, 0.0650},
            {11, 11, 12, 0.3744, 0.1298},
            {12, 12, 13, 1.4680, 1.1550},
            {13, 13, 14, 0.5416, 0.7129},
            {14, 14, 15, 0.5910, 0.5260},
            {15, 15, 16, 0.7463, 0.5450},
            {16, 16, 17, 1.2890, 1.7210},
            {17, 17, 18, 0.7320, 0.5740},
            {18, 2, 19, 0.1640, 0.1565},
            {19, 19, 20, 1.5042, 1.3554},
            {20, 20, 21, 0.4095, 0.4784},
            {21, 21, 22, 0.7089, 0.9373},
            {22, 3, 23, 0.4512, 0.3083},
            {23, 23, 24, 0.8980, 0.7091},
            {24, 24, 25, 0.8960, 0.7011},
            {25, 6, 26, 0.2030, 0.1034},
            {26, 26, 27, 0.2842, 0.1447},
            {27, 27, 28, 1.0590, 0.9337},
            {28, 28, 29, 0.8042, 0.7006},
            {29, 29, 30, 0.5075, 0.2585},
            {30, 30, 31, 0.9744, 0.9630},
            {31, 31, 32, 0.3105, 0.3619},
            {32, 32, 33, 0.3410, 0.5302},
    };

    public static double[][] loadData = {
            {1, 0, 0, 0, 1, 1},
            {2, 100, 60, 0, 1, 0.5},
            {3, 90, 40, 0, 1, 0.5},
            {4, 120, 80, 0, 1, 1},
            {5, 60, 30, 0, 1, 0.5},
            {6, 60, 20, 0, 1, 0.1},
            {7, 200, 100, 0, 1, 0.5},
            {8, 200, 100, 0, 1, 0.1},
            {9, 60, 20, 0, 1, 0.1},
            {10, 60, 20, 0, 1, 0.1},
            {11, 45, 30, 0, 1, 0.5},
            {12, 60, 35, 0, 1, 1},
            {13, 60, 35, 0, 1, 0.1},
            {14, 120, 80, 0, 1, 0.5},
            {15, 60, 10, 0, 1, 0.1},
            {16, 60, 20, 0, 1, 0.1},
            {17, 60, 20, 0, 1, 0.1},
            {18, 90, 40, 0, 1, 0.5},
            {19, 90, 40, 0, 1, 0.5},
            {20, 90, 40, 0, 1, 1},
            {21, 90, 40, 0, 1, 1},
            {22, 90, 40, 0, 1, 0.5},
            {23, 90, 50, 0, 1, 0.1},
            {24, 420, 200, 0, 1, 0.5},
            {25, 420, 200, 0, 1, 0.5},
            {26, 60, 25, 0, 1, 0.1},
            {27, 60, 25, 0, 1, 0.1},
            {28, 60, 20, 0, 1, 0.5},
            {29, 120, 70, 0, 1, 1},
            {30, 200, 600, 0, 1, 0.1},
            {31, 150, 70, 0, 1, 0.5},
            {32, 210, 100, 0, 1, 0.1},
            {33, 60, 40, 0, 1, 0.5}
    };

    static void print(Object value) {
        System.out.println(value);
    }

    static void runAgents() {
        StringBuilder sb = new StringBuilder();
        sb.append("Chronometer:ac.udsm.dca.core.Chronometer;");
        sb.append("Grid:ac.udsm.dca.agents.GridAgent(1);");
        sb.append("Load:ac.udsm.dca.agents.LoadAgent(1);");
        String agents = sb.toString();
//
//        Boot.main(new String[]{
//                //"-gui",
//                // "-container",
//                //"FIPACONTAINER-Main-Container",
//                "-host",
//                "localhost",
//                "-name",
//                "PowerSys",
//                // agents,
//        });
    }

    static void pf() {
        Matrix ld = new Matrix(new double[][]{
                {1, 1, 2, 0.123, 0.4127},
                {2, 2, 3, 0.014, 0.6051},
                {3, 3, 4, 0.746, 1.205},
                {4, 4, 5, 0.698, 0.6084},
                {5, 5, 6, 1.983, 1.7276},
                {6, 6, 7, 0.905, 0.7886},
                {7, 7, 8, 2.055, 1.164},
                {8, 8, 9, 4.795, 2.716},
                {9, 9, 10, 5.343, 3.0264},
        });
        Matrix bd = new Matrix(new double[][]{
                {1, 0, 0},
                {2, 1.840, 0.460},
                {3, 0.980, 0.340},
                {4, 1.790, 0.446},
                {5, 1.598, 1.840},
                {6, 1.610, 0.600},
                {7, 0.780, 0.110},
                {8, 1.150, 0.060},
                {9, 0.980, 0.130},
                {10, 1.640, 0.200},
        });
        PowerFlow pf = new PowerFlow(new Matrix(Main.loadData), new Matrix(Main.lineData), 1);
        double pl = pf.calculate();
        System.out.println("power loss:" + pl);
//        // XYSeriesCollection dataset = new XYSeriesCollection();
//        XYSeries iexplorer = new XYSeries("Bus Currents");
//        Map<Integer, Double> bi = pf.getBusAbsoluteCurrents();
//        Map<Integer, Double> bv = pf.getBusAbsoluteVoltages();
//        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
//        for (int b : bi.keySet()) {
//            //  dataset.addValue(bi.get(b), "current", String.valueOf(b));
//        }
//        for (int b : bv.keySet()) {
//            dataset.addValue(bv.get(b), "voltage", String.valueOf(b));
//        }
//        //dataset.addSeries(iexplorer);
//
//        JFreeChart c = ChartFactory.createLineChart(
//                "Line Voltages",
//                "Lines",
//                "Voltages",
//                dataset,
//                PlotOrientation.VERTICAL,
//                true, true, false);
//
//        ChartPanel chartPanel = new ChartPanel(c);
//        Dimension d = new java.awt.Dimension(560, 367);
//
//        chartPanel.setPreferredSize(d);
//        JFrame f = new JFrame();
//        f.setPreferredSize(d);
//        f.setContentPane(chartPanel);
//        f.setVisible(true);
//        f.pack();
//        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//        //setContentPane( chartPanel );
    }

    public static void pf2() {
        Dlf pf = new Dlf(new Matrix(Main.loadData), new Matrix(Main.lineData), 1);
        double pl = pf.calculate();
        System.out.println("power loss:" + pl);
    }

    public static void main(String... args) {
        // pf();
        pf2();
        //new Application(args).run();
    }

//    static JFreeChart runcharts() {
//        return ChartFactory.createLineChart(
//                "chartTitle",
//                "Years",
//                "Number of Schools",
//                createDataset(),
//                PlotOrientation.VERTICAL,
//                true, true, false);
//    }
//
//    private static DefaultCategoryDataset createDataset() {
//        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
//        dataset.addValue(15, "schools", "1970");
//        dataset.addValue(30, "schools", "1980");
//        dataset.addValue(60, "schools", "1990");
//        dataset.addValue(120, "schools", "2000");
//        dataset.addValue(240, "schools", "2010");
//        dataset.addValue(300, "schools", "2014");
//        return dataset;
//    }
}
