package com.example.application.dca.core;
/*
 * © Adverra All rights reserved.
 *
 *
 * Date: 10/15/2020
 * Time: 2:56 PM
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * @author Ramadhan Juma <ramaj93@yahoo.com>
 * @since 0.0.1
 */
public class CsvData extends AbstractData {
    public double[][] lineData;
    public double[][] busData;

    public CsvData(String file) {
        loadBd(file + "-bd.csv");
        loadLd(file + "-ld.csv");
    }


    private double[][] load(String path) {
        File file = new File(path);
        Scanner sc = null;
        List<double[]> rows = new LinkedList<>();
        try {
            sc = new Scanner(file);
            sc.useDelimiter("\n");   //sets the delimiter pattern
            while (sc.hasNext())  //returns a boolean value
            {
                String line = sc.next();
                String[] p = line.trim().split(",");
                double[] cols = new double[p.length];
                for (int i = 0; i < cols.length; i++) {
                    cols[i] = Double.parseDouble(p[i].trim());
                }
                rows.add(cols);
            }
            sc.close();  //closes the scanne
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return  rows.toArray(new double[][]{});
    }

    private void loadLd(String path) {
        lineData = load(path);
    }

    private void loadBd(String path) {
        busData = load(path);
    }

    @Override
    public double[][] getLineData() {
        return lineData;
    }

    @Override
    public double[][] getBusData() {
        return busData;
    }
}
