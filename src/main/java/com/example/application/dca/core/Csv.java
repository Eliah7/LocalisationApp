package com.example.application.dca.core;
/*
 * © Adverra All rights reserved.
 *
 *
 * Date: 10/23/2020
 * Time: 10:38 AM
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
public class Csv {
    private final String path;
    private String[][] rows;

    public Csv(String path) {
        this.path = path;
        this.rows = load(path);
    }

    private String[][] load(String path) {
        File file = new File(path);
        Scanner sc = null;
        List<String[]> rows = new LinkedList<>();
        try {
            sc = new Scanner(file);
            sc.useDelimiter("\n");   //sets the delimiter pattern
            while (sc.hasNext())  //returns a boolean value
            {
                String line = sc.next();
                String[] p = line.trim().split(",");
                String[] cols = new String[p.length];
                for (int i = 0; i < cols.length; i++) {
                    cols[i] = (p[i].trim());
                }
                rows.add(cols);
            }
            sc.close();  //closes the scanne
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return rows.toArray(new String[][]{});
    }

    public String getPath() {
        return path;
    }

    public int getRowSize() {
        return rows.length;
    }

    public int getColumnSize() {
        return rows.length > 0 ? rows[0].length : 0;
    }

    public String[] getColumn(int col) {
        String[] cols = new String[rows.length];
        for (int i = 0; i < rows.length; i++) {
            cols[i] = rows[i][col];
        }
        return cols;
    }

    public String getValue(int row, int column) {
        return rows[row][column];
    }
}
