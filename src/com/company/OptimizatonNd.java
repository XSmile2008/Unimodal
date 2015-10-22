package com.company;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Starikov on 20.10.15.
 */
public class OptimizatonNd {

    static double k;
    static double fk;

    private static double[] ungradient(double[] x, Function[] fs) {//-gradient
        //return new double[]{- fs[0].calc(x), - fs[1].calc(x)};//TODO: remove
        double[] ug = new double[fs.length];
        for (int i = 0; i < fs.length; i++) ug[i] = - fs[i].calc(x);
        return ug;
    }

    private static double norm(double[] g) {
        double sum  = 0;
        for (double d : g) sum += d*d;
        return Math.sqrt(sum);
    }

    public static void gradientDescent(Function f, Function[] fs, double[] x0, double e) {
        k = 0;
        double fx0 = f.calc(x0);
        double[] ug = ungradient(x0, fs);
        if (norm(ug) < e) {
            return;
        }

        double[] x = x0;
        double[] h = new double[x0.length];
        for (double d : h) d = 0;

        Function nyan0 = h0 -> f.calc(x[0] - h0[0] * ug[0], x[1] - h[1] * ug[1]);
        h[0] = Optimization1d.parabolas(nyan0, x[0], 1, e)[0];

        Function nyan1 = h1 -> f.calc(x[0] - h[0] * ug[0], x[1] - h1[0] * ug[1]);
        h[1] = Optimization1d.parabolas(nyan1, x[1], 1, e)[0];

        for (int i = 0; i < x.length; i++) x[i] -= h[i] * ug[i];
        System.out.println("\nf(x^k = )" + f.calc(x));
    }
}
