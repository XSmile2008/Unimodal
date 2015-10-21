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
    private static double fsx0(double x[]) {
        return 18 + 4*x[1];//TODO: verify
    }
    private static double fsx1(double x[]) {
        return 4*x[0] + 2 + 1;//TODO: verify
    }
    private static double[] ungradient(double x[]) {//-gradient
        return new double[]{-fsx0(x), -fsx1(x)};
    }

    private static double norm(double g[]) {
        double sum  = 0;
        for (double d : g) sum += d*d;
        return Math.sqrt(sum);
    }

    public static void gradientDescent(double[] x0, double e) {
        k = 0;
        double fx0 = f(x0);
        double[] ug = ungradient(x0);
        if (norm(ug) < e) {
            return;
        }



    }
}
