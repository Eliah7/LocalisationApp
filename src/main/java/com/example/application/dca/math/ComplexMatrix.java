package com.example.application.dca.math;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ComplexMatrix {
    private Complex[][] data;

    public static Complex[][] array(int r, int c, Complex value) {
        Complex[][] d = new Complex[r][c];
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                d[i][j] = value;
            }
        }
        return d;
    }

    public ComplexMatrix(int row, int col, Complex defaultValue) {
        this(array(row, col, defaultValue));
    }

    public ComplexMatrix(int row, int col) {
        this(row, col, new Complex());
    }

    public ComplexMatrix(Complex[][] data) {
        this.data = data;
    }

    public ComplexMatrix(Complex[] data) {
        this.data = new Complex[1][data.length];
        for (int i = 0; i < data.length; i++) {
            this.data[0][i] = data[i];
        }
    }

    public ComplexMatrix(Matrix complex) {
        this.data = new Complex[complex.getRowSize()][complex.getColumnSize()];
        for (int i = 0; i < complex.getRowSize(); i++) {
            for (int j = 0; j < complex.getColumnSize(); j++) {
                data[i][j] = new Complex(complex.getAt(i + 1, j + 1));
            }
        }
    }

    public ComplexMatrix(Matrix complex, int r, int c) {
        this.data = new Complex[complex.getRowSize()][1];
        for (int i = 0; i < complex.getRowSize(); i++) {
            data[i][0] = new Complex(complex.getAt(i + 1, r), complex.getAt(i + 1, c));
        }
    }

    synchronized public Complex[][] getData() {
        return data;
    }

    public int getRowSize() {
        return data.length;
    }

    public int getColumnSize() {
        return data.length > 0 ? data[0].length : 0;
    }

    public ComplexMatrix max() {
        Complex[][] items = new Complex[1][getColumnSize()];
        for (int i = 0; i < getColumnSize(); i++) {
            double max = 0;
            Complex m = null;
            for (int j = 0; j < getRowSize(); j++) {
                if (data[j][i].abs2() > max) {
                    max = data[j][i].abs2();
                    m = data[j][i];
                }
            }
            items[0][i] = m;
        }
        return new ComplexMatrix(items);
    }

    public ComplexMatrix extractRows(Collection<Integer> rows) {
        Complex[][] d = new Complex[rows.size()][getColumnSize()];
        int r = 0;
        for (int i : rows) {
            for (int j = 0; j < getColumnSize(); j++) {
                d[r][j] = getData(i - 1, j);
            }
            r++;
        }
        return new ComplexMatrix(d);
    }

    public ComplexMatrix removeRows(Collection<Integer> rows) {
        Complex[][] d = new Complex[getRowSize() - rows.size()][getColumnSize()];
        int r = 0;
        for (int i = 0; i < getRowSize(); i++) {
            if (!rows.contains(i + 1)) {
                for (int j = 0; j < getColumnSize(); j++) {
                    d[r][j] = getData(i, j);
                }
                r++;
            }
        }
        return new ComplexMatrix(d);
    }

    public ComplexMatrix removeRow(int row) {
        Complex[][] d = new Complex[getRowSize() - 1][getColumnSize()];
        int r = 0;
        for (int i = 0; i < getRowSize(); i++) {
            if (row != (i + 1)) {
                for (int j = 0; j < getColumnSize(); j++) {
                    d[r][j] = getData(i, j);
                }
                r++;
            }
        }
        return new ComplexMatrix(d);
    }

    public ComplexMatrix extract(int startRow, int endRow, int startColumn, int endColumn) {
        startRow -= 1;
        endRow -= 1;
        startColumn -= 1;
        endColumn -= 1;
        int rowLength = (endRow - startRow) + 1;
        int colLength = (endColumn - startColumn) + 1;
        Complex[][] vals = new Complex[rowLength][colLength];
        for (int i = startRow; i < rowLength; i++) {
            System.arraycopy(data[i], startColumn, vals[i], 0, colLength);
        }
        return new ComplexMatrix(vals);
    }

    public ComplexMatrix extract(int startRow, int startColumn, int endColumn) {
        return extract(startRow, getRowSize(), startColumn, endColumn);
    }

    public ComplexMatrix extract(int startColumn, int endColumn) {
        return extract(1, startColumn, endColumn);
    }

    public Complex maxValue() {
        Complex max = new Complex();
        for (int i = 0; i < getRowSize(); i++) {
            for (Complex v : data[i]) {
                if (v.abs2() > max.abs2()) {
                    max = v;
                }
            }
        }
        return max;
    }

    public ComplexMatrix transpose() {
        Complex[][] data = new Complex[getColumnSize()][getRowSize()];
        for (int i = 0; i < getRowSize(); i++) {
            for (int j = 0; j < getColumnSize(); j++) {
                data[j][i] = this.data[i][j];
            }
        }
        return new ComplexMatrix(data);
    }

    synchronized private Complex getData(int row, int column) {
        return data[row][column];
    }

    private void setData(int row, int column, Complex complex) {
        data[row][column] = complex;
    }

    synchronized public Complex getAt(int row, int column) {
        return getData(row - 1, column - 1);
    }

    public ComplexMatrix setAt(int row, int column, Complex value) {
        setData(row - 1, column - 1, value);
        return this;
    }

    public Matrix pos(Complex value) {
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


    public ComplexMatrix position(Complex value) {
        Complex[][] d = new Complex[getRowSize()][getColumnSize()];
        for (int i = 0; i < getRowSize(); i++) {
            for (int j = 0; j < getColumnSize(); j++) {
                if (getData(i, j) == value) {
                    d[i][j] = new Complex(1, 0);
                } else {
                    d[i][j] = new Complex(0, 0);
                }
            }
        }
        return new ComplexMatrix(d);
    }

    public ComplexMatrix copy() {
        Complex[][] var2 = new Complex[getRowSize()][getColumnSize()];

        for (int var3 = 0; var3 < this.getRowSize(); ++var3) {
            for (int var4 = 0; var4 < this.getColumnSize(); ++var4) {
                var2[var3][var4] = getData(var3, var4);
            }
        }
        return new ComplexMatrix(var2);
    }

    public boolean isEmpty() {
        return getRowSize() == 0 || getColumnSize() == 0;
    }

    public Object clone() {
        return this.copy();
    }

    public ComplexMatrix merge(ComplexMatrix matrix) {
        int rs = matrix.getRowSize();
        int cs = matrix.getColumnSize();
        int c = getColumnSize();
        int t = cs + c;
        if (rs != getRowSize()) {
            return null;
        }
        Complex[][] d = new Complex[rs][t];
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
        return new ComplexMatrix(d);
    }

    public ComplexMatrix append(Complex complex) {
        ComplexMatrix matrix = new ComplexMatrix(getRowSize() + 1, getColumnSize());
        matrix.setData(0, 0, complex);
        for (int i = 0; i < getRowSize(); i++) {
            for (int j = 0; j < getColumnSize(); j++) {
                matrix.setData(i + 1, j, getData(i, j));
            }
        }
        return matrix;
    }

    public ComplexMatrix append(ComplexMatrix matrix) {
        if (getColumnSize() != matrix.getColumnSize()) {
            return null;
        }
        int c = getColumnSize();
        int r = getRowSize();
        int rt = r + matrix.getRowSize();
        Complex[][] d = new Complex[rt][c];
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
        return new ComplexMatrix(d);
    }

    public ComplexMatrix append(int startRow, int endRow, ComplexMatrix matrix) {
        if (startRow == endRow) {
            return append(matrix);
        } else {
            ComplexMatrix m = null;
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
                if (getData(j, i).abs2() > 0) {
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
                if (getData(j, i).abs2() > 0) {
                    rows.add(i + 1);
                    cols.add(j + 1);
                }
            }
        }
        return new List[]{cols, rows};
    }

    public Complex[][] copyData() {
        Complex[][] result = new Complex[data.length][];
        for (int r = 0; r < data.length; r++) {
            result[r] = data[r].clone();
        }
        return result;
    }

    public ComplexMatrix setValue(int row, int column, Complex value) {
        Complex[][] d = copyData();
        row = row - 1;
        column = column - 1;
        d[row][column] = value;
        return new ComplexMatrix(d);
    }


    public ComplexMatrix setValues(int row, int startColumn, Complex... items) {
        Complex[][] d = copyData();
        int sz = items.length;
        row = row - 1;
        startColumn = startColumn - 1;
        for (int i = 0; i < sz; i++) {
            d[row][i + startColumn] = items[i];
        }
        return new ComplexMatrix(d);
    }

    public Complex sumColumns(int... columns) {
        System.out.println(columns[0]);
        Complex sum = new Complex();
        for (int i = 0; i < getRowSize(); i++) {
            for (int j : columns) {
                sum = sum.plus(getData(i, j - 1));
            }
        }
        return sum;
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

    public Complex sumRows(int... rows) {
        Complex sum = null;
        for (int i : rows) {
            for (int j = 0; j < getColumnSize(); j++) {
                if (sum == null) {
                    sum = getData(i - 1, j);
                } else {
                    sum = sum.plus(getData(i, j));
                }
            }
        }
        return sum;
    }

    public ComplexMatrix sum(ComplexMatrix matrix) {
        ComplexMatrix complex = new ComplexMatrix(getRowSize(), getColumnSize());
        for (int i = 0; i < getRowSize(); i++) {
            for (int j = 0; j < getColumnSize(); j++) {
                complex.setData(i, j, getAt(i + 1, j + 1).plus(matrix.getAt(i + 1, j + 1)));
            }
        }
        return complex;
    }

    public ComplexMatrix minus(ComplexMatrix matrix) {
        ComplexMatrix complex = new ComplexMatrix(getRowSize(), getColumnSize());
        for (int i = 0; i < getRowSize(); i++) {
            for (int j = 0; j < getColumnSize(); j++) {
                complex.setData(i, j, getAt(i + 1, j + 1).minus(matrix.getAt(i + 1, j + 1)));
            }
        }
        return complex;
    }

    public ComplexMatrix multiply(Complex complex) {
        ComplexMatrix matrix = new ComplexMatrix(getRowSize(), getColumnSize());
        for (int i = 0; i < getRowSize(); i++) {
            for (int j = 0; j < getColumnSize(); j++) {
                matrix.setData(i, j, getData(i, j).multiply(complex));
            }
        }
        return matrix;
    }

    public ComplexMatrix multiply(double value) {
        ComplexMatrix matrix = new ComplexMatrix(getRowSize(), getColumnSize());
        for (int i = 0; i < getRowSize(); i++) {
            for (int j = 0; j < getColumnSize(); j++) {
                matrix.setData(i, j, getData(i, j).multiply(value));
            }
        }
        return matrix;
    }

    public ComplexMatrix multiply(Matrix matrix) {
        ComplexMatrix matrix1 = new ComplexMatrix(getRowSize(), matrix.getColumnSize());
        for (int a = 0; a < getRowSize(); a++) {
            for (int b = 0; b < matrix.getColumnSize(); b++) {
                Complex sum = null;
                for (int ac = 0; ac < getColumnSize(); ac++) {
                    Complex y = getData(a, ac);
                    double d = matrix.getAt(ac + 1, b + 1);
                    Complex x = y.multiply(d);

                    if (sum == null) {
                        sum = x;
                    } else {
                        sum = sum.plus(x);
                    }
                }
                matrix1.setData(a, b, sum);
            }
        }
        return matrix1;
    }

    public ComplexMatrix multiply(ComplexMatrix matrix) {
        ComplexMatrix matrix1 = new ComplexMatrix(getRowSize(), matrix.getColumnSize());

        for (int a = 0; a < getRowSize(); a++) {
            for (int b = 0; b < matrix.getColumnSize(); b++) {
                Complex sum = null;
                for (int ac = 0; ac < getColumnSize(); ac++) {
                    Complex y = getData(a, ac);
                    Complex d = matrix.getAt(ac + 1, b + 1);
                    Complex x = y.multiply(d);

                    if (sum == null) {
                        sum = x;
                    } else {
                        sum = sum.plus(x);
                    }
                }
                matrix1.setData(a, b, sum);
            }
        }
        return matrix1;
    }

    public ComplexMatrix divide(Complex complex) {
        ComplexMatrix matrix = new ComplexMatrix(getRowSize(), getColumnSize());
        for (int i = 0; i < getRowSize(); i++) {
            for (int j = 0; j < getColumnSize(); j++) {
                matrix.setData(i, j, getData(i, j).dividedBy(complex));
            }
        }
        return matrix;
    }

    public ComplexMatrix divide(ComplexMatrix complex) {
        ComplexMatrix matrix = new ComplexMatrix(getRowSize(), getColumnSize());
        for (int i = 0; i < getRowSize(); i++) {
            for (int j = 0; j < getColumnSize(); j++) {
                matrix.setData(i, j, getData(i, j).dividedBy(complex.getData(i, j)));
            }
        }
        return matrix;
    }

    public ComplexMatrix divide(Matrix complex) {
        ComplexMatrix matrix = new ComplexMatrix(getRowSize(), getColumnSize());
        for (int i = 0; i < getRowSize(); i++) {
            for (int j = 0; j < getColumnSize(); j++) {
                matrix.setData(i, j, getData(i, j).dividedBy(complex.getAt(i + 1, j + 1)));
            }
        }
        return matrix;
    }

    public ComplexMatrix divide(double value) {
        ComplexMatrix matrix = new ComplexMatrix(getRowSize(), getColumnSize());
        for (int i = 0; i < getRowSize(); i++) {
            for (int j = 0; j < getColumnSize(); j++) {
                matrix.setData(i, j, getData(i, j).dividedBy(value));
            }
        }
        return matrix;
    }

    public ComplexMatrix diag() {
        ComplexMatrix matrix = null;
        int rows = getRowSize();
        int cols = getColumnSize();
        if (cols == 1) {
            matrix = new ComplexMatrix(rows, rows);
            for (int i = 0; i < getRowSize(); i++) {
                for (int j = 0; j < getRowSize(); j++) {
                    if (i == j) {
                        matrix.setData(i, j, getData(i, 0));
                    }
                }
            }
        }
        return matrix;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Complex[] z : data) {
            for (Complex d : z) {
                sb.append(" ").append(d);
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public ComplexMatrix conjugate() {
        ComplexMatrix matrix = new ComplexMatrix(getRowSize(), getColumnSize());
        for (int i = 0; i < getRowSize(); i++) {
            for (int j = 0; j < getColumnSize(); j++) {
                matrix.setData(i, j, getData(i, j).conj());
            }
        }
        return matrix;
    }

    public Matrix abs() {
        Matrix matrix = new Matrix(getRowSize(), getColumnSize());
        for (int i = 0; i < getRowSize(); i++) {
            for (int j = 0; j < getColumnSize(); j++) {
                matrix.setAt(i + 1, j + 1, getData(i, j).abs());
            }
        }
        return matrix;
    }
}
