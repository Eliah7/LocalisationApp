package com.example.application.dca.math;

import java.util.*;

public class Matrix {
    private double[][] data;

    public static double[][] array(int r, int c, double value) {
        double[][] d = new double[r][c];
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                d[i][j] = value;
            }
        }
        return d;
    }

    public Matrix(int row, int col, double defaultValue) {
        this(array(row, col, defaultValue));
    }

    public Matrix(int row, int col) {
        this(row, col, 0);
    }

    public Matrix(double[][] data) {
        this.data = data;
    }

    public Matrix(double[] data) {
        this.data = new double[1][data.length];
        for (int i = 0; i < data.length; i++) {
            this.data[0][i] = data[i];
        }
    }

    public Matrix(int[][] data) {
        this.data = new double[data.length][data[0].length];
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                this.data[i][j] = data[i][j];
            }
        }
    }

    public Matrix(int[] data) {
        this.data = new double[1][data.length];
        for (int i = 0; i < data.length; i++) {
            this.data[0][i] = data[i];
        }
    }

    synchronized public Matrix copyColumn(Matrix matrix, int source, int dest) {
        for (int i = 0; i < getRowSize(); i++) {
            data[i][dest - 1] = matrix.getData(i, source - 1);
        }
        return this;
    }

    public Matrix copyColumn(Matrix matrix, int column) {
        for (int i = 0; i < getRowSize(); i++) {
            data[i][column - 1] = matrix.getData(i, 0);
        }
        return this;
    }

    public Matrix(List<Number> data) {
        this.data = new double[1][data.size()];
        for (int i = 0; i < data.size(); i++) {
            Number n = data.get(i);
            if (n instanceof Double) {
                this.data[0][i] = (double) n;
            } else {
                this.data[0][i] = Double.parseDouble("" + n + "");
            }
        }
    }

    public double[][] getData() {
        return data;
    }

    public int getRowSize() {
        return data.length;
    }

    public int getColumnSize() {
        return data.length > 0 ? data[0].length : 0;
    }

    public Matrix max() {
        double[][] items = new double[1][getColumnSize()];
        for (int i = 0; i < getColumnSize(); i++) {
            double max = 0;
            for (int j = 0; j < getRowSize(); j++) {
                if (data[j][i] > max) {
                    max = data[j][i];
                }
            }
            items[0][i] = max;
        }
        return new Matrix(items);
    }

    private void setData(int row, int column, double complex) {
        data[row][column] = complex;
    }

    public Matrix setAt(int row, int column, double value) {
        setData(row - 1, column - 1, value);
        return this;
    }

    public Matrix extractRows(Collection<Integer> rows) {
        double[][] d = new double[rows.size()][getColumnSize()];
        int r = 0;
        for (int i : rows) {
            for (int j = 0; j < getColumnSize(); j++) {
                d[r][j] = getData(i - 1, j);
            }
            r++;
        }
        return new Matrix(d);
    }

    public Matrix removeRows(Collection<Integer> rows) {
        double[][] d = new double[getRowSize() - rows.size()][getColumnSize()];
        int r = 0;
        for (int i = 0; i < getRowSize(); i++) {
            if (!rows.contains(i + 1)) {
                for (int j = 0; j < getColumnSize(); j++) {
                    d[r][j] = getData(i, j);
                }
                r++;
            }
        }
        return new Matrix(d);
    }

    public Matrix extract(int startRow, int endRow, int startColumn, int endColumn) {
        startRow -= 1;
        endRow -= 1;
        startColumn -= 1;
        endColumn -= 1;
        int rowLength = (endRow - startRow) + 1;
        int colLength = (endColumn - startColumn) + 1;
        double[][] vals = new double[rowLength][colLength];
        for (int i = startRow; i < rowLength; i++) {
            System.arraycopy(data[i], startColumn, vals[i], 0, colLength);
        }
        return new Matrix(vals);
    }

    public Matrix extract(int startRow, int startColumn, int endColumn) {
        return extract(startRow, getRowSize(), startColumn, endColumn);
    }

    public Matrix extract(int startColumn, int endColumn) {
        return extract(1, startColumn, endColumn);
    }

    public double maxValue() {
        double max = 0;
        for (int i = 0; i < getRowSize(); i++) {
            for (double v : data[i]) {
                if (v > max) {
                    max = v;
                }
            }
        }
        return max;
    }

    public static Matrix makeVector(double start, double end) {
        int len = (int) (end - start) + 1;
        double[][] vector = new double[1][len];
        double c = start;
        for (int j = 0; j < len; j++) {
            vector[0][j] = c;
            c++;
        }
        return new Matrix(vector);
    }

    public Matrix transpose() {
        double[][] data = new double[getColumnSize()][getRowSize()];
        for (int i = 0; i < getRowSize(); i++) {
            for (int j = 0; j < getColumnSize(); j++) {
                data[j][i] = this.data[i][j];
            }
        }
        return new Matrix(data);
    }

    synchronized private double getData(int row, int column) {
        return data[row][column];
    }

    public double getAt(int row, int column) {
        return getData(row - 1, column - 1);
    }

    public Matrix pos(double value) {
        List<Number> values = new ArrayList<>();
        int rmax = getRowSize();
        for (int i = 0; i < getColumnSize(); i++) {
            for (int j = 0; j < getRowSize(); j++) {
                if (getData(j, i) == value) {
                    values.add(rmax * i + (j + 1));
                }
            }
        }
        return new Matrix(values);
    }

    public Matrix position(double value) {
        double[][] d = new double[getRowSize()][getColumnSize()];
        for (int i = 0; i < getRowSize(); i++) {
            for (int j = 0; j < getColumnSize(); j++) {
                if (getData(i, j) == value) {
                    d[i][j] = 1;
                } else {
                    d[i][j] = 0;
                }
            }
        }
        return new Matrix(d);
    }

    public Matrix copy() {
        double[][] var2 = new double[getRowSize()][getColumnSize()];

        for (int var3 = 0; var3 < this.getRowSize(); ++var3) {
            for (int var4 = 0; var4 < this.getColumnSize(); ++var4) {
                var2[var3][var4] = getData(var3, var4);
            }
        }
        return new Matrix(var2);
    }

    public boolean isEmpty() {
        return getRowSize() == 0 || getColumnSize() == 0;
    }

    public Object clone() {
        return this.copy();
    }

    public Matrix merge(Matrix matrix) {
        int rs = matrix.getRowSize();
        int cs = matrix.getColumnSize();
        int c = getColumnSize();
        int t = cs + c;
        if (rs != getRowSize()) {
            return null;
        }
        double[][] d = new double[rs][t];
        for (int i = 0; i < getRowSize(); i++) {
            for (int j = 0; j < c; j++) {
                d[i][j] = getData(i, j);
            }
        }
        for (int i = 0; i < rs; i++) {
            for (int j = 0; j < cs; j++) {
                d[i][cs + j] = matrix.getData(i, j);
            }
        }
        return new Matrix(d);
    }

    public Matrix append(Matrix matrix) {
        if (getColumnSize() != matrix.getColumnSize()) {
            return null;
        }
        int c = getColumnSize();
        int r = getRowSize();
        int rt = r + matrix.getRowSize();
        double[][] d = new double[rt][c];
        for (int i = 0; i < getRowSize(); i++) {
            for (int j = 0; j < c; j++) {
                d[i][j] = getData(i, j);
            }
        }
        for (int i = 0; i < matrix.getRowSize(); i++) {
            for (int j = 0; j < c; j++) {
                d[i + r][j] = matrix.getData(i, j);
            }
        }
        return new Matrix(d);
    }

    public Matrix append(int startRow, int endRow, Matrix matrix) {
        if (startRow == endRow) {
            return append(matrix);
        } else {
            Matrix m = null;
            for (int i = startRow; i <= endRow; i++) {
                m = append(matrix);
            }
            return m;
        }
    }

    public Matrix find() {
        List<Number> values = new ArrayList<>();
        int rmax = getRowSize();
        for (int i = 0; i < getColumnSize(); i++) {
            for (int j = 0; j < getRowSize(); j++) {
                if (getData(j, i) > 0) {
                    values.add(rmax * i + (j + 1));
                }
            }
        }
        return new Matrix(values);
    }

    public List<Integer>[] findRowColumn() {
        List<Integer> rows = new ArrayList<>();
        List<Integer> cols = new ArrayList<>();
        for (int i = 0; i < getColumnSize(); i++) {
            for (int j = 0; j < getRowSize(); j++) {
                if (getData(j, i) > 0) {
                    rows.add(i + 1);
                    cols.add(j + 1);
                }
            }
        }
        return new List[]{cols, rows};
    }

    public double[][] copyData() {
        double[][] result = new double[data.length][];
        for (int r = 0; r < data.length; r++) {
            result[r] = data[r].clone();
        }
        return result;
    }

    public Matrix setValue(int row, int column, double value) {
        double[][] d = copyData();
        row = row - 1;
        column = column - 1;
        d[row][column] = value;
        return new Matrix(d);
    }


    public Matrix setValues(int row, int startColumn, double... items) {
        double[][] d = copyData();
        int sz = items.length;
        row = row - 1;
        startColumn = startColumn - 1;
        for (int i = 0; i < sz; i++) {
            d[row][i + startColumn] = items[i];
        }
        return new Matrix(d);
    }

    public double sumColumns(int... columns) {
        double sum = 0;
        for (int i = 0; i < getRowSize(); i++) {
            for (int j : columns) {
                sum += getData(i, j - 1);
            }
        }
        return sum;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (double[] z : data) {
            for (double d : z) {
                sb.append(" ").append(d);
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public Matrix setColumn(int column, double value) {
        for (int i = 0; i < getRowSize(); i++) {
            data[i][column - 1] = value;
        }
        return this;
    }

    public ComplexMatrix vectorMultiply(ComplexMatrix matrix) {
        ComplexMatrix matrix1 = new ComplexMatrix(getRowSize(), getColumnSize());
        for (int a = 0; a < getRowSize(); a++) {
            for (int b = 0; b < matrix.getColumnSize(); b++) {
                matrix1.setAt(a + 1, b + 1, matrix.getAt(a + 1, b + 1).multiply(getData(a, b)));
            }
        }
        return matrix1;
    }

    public Matrix vectorMultiply(Matrix matrix) {
        Matrix matrix1 = new Matrix(getRowSize(), getColumnSize());
        for (int a = 0; a < getRowSize(); a++) {
            for (int b = 0; b < matrix.getColumnSize(); b++) {
                matrix1.setAt(a + 1, b + 1, matrix.getAt(a + 1, b + 1) * getData(a, b));
            }
        }
        return matrix1;
    }

    synchronized public ComplexMatrix multiply(ComplexMatrix matrix) {
        ComplexMatrix matrix1 = new ComplexMatrix(getRowSize(), matrix.getColumnSize());

        for (int a = 0; a < getRowSize(); a++) {
            for (int b = 0; b < matrix.getColumnSize(); b++) {
                Complex sum = null;
                for (int ac = 0; ac < getColumnSize(); ac++) {
                    double y = getData(a, ac);
                    Complex d = matrix.getAt(ac + 1, b + 1);
                    Complex x = d.multiply(y);
                    if (sum == null) {
                        sum = x;
                    } else {
                        sum = sum.plus(x);
                    }
                }
                matrix1.setAt(a + 1, b + 1, sum);
            }
        }
        return matrix1;
    }

    public Matrix multiply(Matrix matrix) {
        Matrix matrix1 = new Matrix(getRowSize(), matrix.getColumnSize());

        for (int a = 0; a < getRowSize(); a++) {
            for (int b = 0; b < matrix.getColumnSize(); b++) {
                double sum = 0;
                for (int ac = 0; ac < getColumnSize(); ac++) {
                    double y = getData(a, ac);
                    double d = matrix.getAt(ac + 1, b + 1);
                    double x = d * (y);
                    sum += x;
                }
                matrix1.setAt(a + 1, b + 1, sum);
            }
        }
        return matrix1;
    }
}
