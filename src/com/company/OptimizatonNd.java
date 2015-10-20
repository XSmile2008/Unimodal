package com.company;

/**
 * Created by Starikov on 20.10.15.
 */
public class OptimizatonNd {

    static double k;
    static double fk;

    private static double f(double x[]) {
        return 10*x[0]*x[0] + 4*x[0]*x[1] + x[1]*x[1] - 2*x[0] + x[1];
    }

    public static void gradientDescent(double e) {
        double[] x0 = {0, 0};

    }
}
