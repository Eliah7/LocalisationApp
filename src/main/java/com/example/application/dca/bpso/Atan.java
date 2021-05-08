package com.example.application.dca.bpso;

/**
 * © CoreIT All rights reserved.
 * <p>
 * <p>
 * Date: 5/1/2020
 * Time: 3:50 PM
 *
 * @author Ramadhan Juma <ramaj93@yahoo.com>
 * @since 0.0.1
 */
public class Atan {
    public static double function(double velocity) {
        double result = 0.0;
        result = (2 / Math.PI) * (Math.atan((Math.PI * velocity) / 2));
        return result;
    }
}
