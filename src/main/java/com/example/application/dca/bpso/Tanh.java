package com.example.application.dca.bpso;

/**
 * © CoreIT All rights reserved.
 * <p>
 * <p>
 * Date: 5/1/2020
 * Time: 3:45 PM
 *
 * @author Ramadhan Juma <ramaj93@yahoo.com>
 * @since 0.0.1
 */
public class Tanh {
    public static double function(double velocity) {
        double result = 0.0;
        result = 1 / (1 + Math.exp(-velocity));
        return result;
    }
}
