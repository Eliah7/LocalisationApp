package com.example.application.dca.interfaces;

import com.example.application.dca.math.ComplexMatrix;
import com.example.application.dca.math.Matrix;

public abstract class PGOAnalyzer {
    public Matrix busData;
    public Matrix lineData;
    protected int centralBus;
    protected ComplexMatrix IB;
    protected ComplexMatrix VB;
    protected Double baseMva;
    protected Double baseKva;

    abstract public double calculate();
}
