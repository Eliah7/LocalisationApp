package com.example.application.dca.utils;

public class Matrix {

    public static double[][] zeros(int r, int c) {
        double[][] d = new double[r][c];
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                d[i][j] = 0;
            }
        }
        return d;
    }

    public static double[][] max(double[][] matrix) {
        double[][] items = new double[matrix.length][1];
        for (int i = 0; i < matrix.length; i++) {
            double max = 0;
            for (double v : matrix[i]) {
                if (v > max) {
                    max = v;
                }
            }
            items[i][0] = max;
        }
        return items;
    }

    public static double max(double[] matrix) {
        double max = 0;
        for (double v : matrix) {
            if (v > max) {
                max = v;
            }
        }
        return max;
    }

    public static double min(double[][] matrix) {
        double max = 0;
        for (double[] c : matrix) {
            for (double v : c) {
                if (v < max) {
                    max = v;
                }
            }
        }
        return max;
    }

    public static int numRows(double[][] matrix) {
        return matrix.length;
    }

    public static int numColumns(double[][] matrix) {
        if (matrix.length > 0) {
            return matrix[0].length;
        }
        return 0;
    }

    public static double[][] extractColumns(double[][] matrix, int start, int end) {
        int rows = numRows(matrix);
        int cols = (end - start) + 1;
        double[][] vals = new double[rows][cols];
        start = start - 1;
        end = end - 1;
        for (int i = 0; i < rows; i++) {
            if (end + 1 - start >= 0) System.arraycopy(matrix[i], start, vals[i], start - start, end + 1 - start);
        }
        return vals;
    }

    public static String print(double[][] matrix) {
        StringBuilder sb = new StringBuilder();
        for (double[] z : matrix) {
            for (double d : z) {
                sb.append(" ").append(d);
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public static String print(double[] matrix) {
        StringBuilder sb = new StringBuilder();
        for (double z : matrix) {
            sb.append(" ").append(z);
        }
        sb.append("\n");
        return sb.toString();
    }
}
