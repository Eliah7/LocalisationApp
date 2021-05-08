package com.example.application.data.state;

import com.example.application.dca.Main;
import com.example.application.dca.core.Dlf;
import com.example.application.dca.math.Matrix;

public class NetworkState {
    private Matrix loadData;
    private Matrix lineData;

    public NetworkState(Matrix loadData, Matrix lineData) {
        this.loadData = loadData;
        this.lineData = lineData;
    }

    public static void main(String args[]){
        Dlf pf = new Dlf(new Matrix(Main.loadData), new Matrix(Main.lineData), 1);
        double pl = pf.calculate();
        System.out.println("power loss:" + pl);
    }


}
