package com.company;

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

    private static double norm(double[] vector) {
        double sum  = 0;
        for (double d : vector) sum += d*d;
        return Math.sqrt(sum);
    }

    public static void gradientDescent(Function f, Function[] fs, double[] x0, double e) {
        k = 0;
        double[] x = x0;//TODO: remove
        double fx = f.calc(x0);
        double nx = e;//norm(x^(k+1) - x^k)

        while (true) {
            k++;

            double[] ug = ungradient(x, fs);//step0
            if (norm(ug) < e || nx < e) {
                return;
            }

            double[] h = new double[x.length];
            for (int i = 0; i < h.length; i++) {
                Function[] func_j = new Function[x.length];
                for (int j = 0; j < func_j.length; j++) {
                    final int fj = j;
                    if (i == j) func_j[j] = hi -> x[fj] - hi[0] * ug[fj];
                    else func_j[j] = hi -> x[fj] - h[fj] * ug[fj];
                }
                Function func_i = hi -> {
                    double[] doubles = new double[x.length];
                    for (int j = 0; j < x.length; j++) doubles[j] = func_j[j].calc(hi);
                    return f.calc(doubles);
                };
                h[i] = Optimization1d.parabolas(func_i, x[0], 1, e)[0];
            }

            /*Function nyan0 = h0 -> f.calc(x[0] - h0[0] * ug[0], x[1] - h[1] * ug[1]);
            h[0] = Optimization1d.parabolas(nyan0, x[0], 1, e)[0];

            Function nyan1 = h1 -> f.calc(x[0] - h[0] * ug[0], x[1] - h1[0] * ug[1]);
            h[1] = Optimization1d.parabolas(nyan1, x[1], 1, e)[0];*/

            double[] delta = new double[x.length];
            for (int i = 0; i < x.length; i++) delta[i] = x[i] - (x[i] - h[i] * ug[i]);
            nx = norm(delta);

            for (int i = 0; i < x.length; i++) x[i] -= h[i] * ug[i];
            System.out.println("\nk = " + k + " -----------------------------------");
            System.out.println("f(x^k = )" + f.calc(x));
            System.out.println("norm(x^(k+1) - x^k) = " + nx);
        }
    }
}
