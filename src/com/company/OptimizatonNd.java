package com.company;

import java.util.Arrays;

/**
 * Created by Starikov on 20.10.15.
 */
public class OptimizatonNd {

    static int k;
    static int fk;

    private static double[] ungradient(double[] x, Function[] fs) {//-gradient
        double[] ug = new double[fs.length];
        for (int i = 0; i < fs.length; i++) ug[i] = - fs[i].calc(x);
        return ug;
    }

    private static double norm(double[] vector) {
        double sum  = 0;
        for (double d : vector) sum += d*d;
        return Math.sqrt(sum);
    }

    public static double[][] gradientDescent(Function f, Function[] fs, double[] x0, double e) {
        System.err.println("\ngradientDescent(" + f + ", " + fs + ", " + Arrays.toString(x0) + ", " + e + ")");
        k = 0;
        double[] x = Arrays.copyOf(x0, x0.length);
        double nx = e;//norm(x^(k+1) - x^k)
        while (true) {
            k++;

            double[] ug = ungradient(x, fs);//step0
            if (norm(ug) < e || nx < e) {
                double[][] r = new double[2][]; r[0] = x; r[1] = new double[] {f.calc(x)};
                return r;
            }

            double[] h = new double[x.length];
            for (int i = 0; i < h.length; i++) {
                final int fi = i;
                Function func = hi -> {
                    double[] args = new double[x.length];
                    for (int j = 0; j < x.length; j++)
                        if (fi == j) args[j] = x[j] - hi[0] * ug[j];
                        else args[j] = x[j] - h[j] * ug[j];
                    return f.calc(args);
                };
                //h[i] = Optimization1d.parabolas(func, x[0], 1, e)[0];//TODO: check x[0] for hi
                h[i] = Optimization1d.parabolas(func, 0, 1, e)[0];
            }

            /*Function nyan0 = h0 -> f.calc(x[0] - h0[0] * ug[0], x[1] - h[1] * ug[1]);
            h[0] = Optimization1d.parabolas(nyan0, x[0], 1, e)[0];

            Function nyan1 = h1 -> f.calc(x[0] - h[0] * ug[0], x[1] - h1[0] * ug[1]);
            h[1] = Optimization1d.parabolas(nyan1, x[1], 1, e)[0];*/

            double[] delta = new double[x.length];
            for (int i = 0; i < x.length; i++) delta[i] = x[i] - (x[i] - h[i] * ug[i]);
            nx = norm(delta);

            for (int i = 0; i < x.length; i++) x[i] -= h[i] * ug[i];//TODO: can be moved into h minimization loop
            System.out.println("\nk = " + k + " -----------------------------------");
            System.out.println("f(x^k = )" + f.calc(x));
            System.out.println("norm(x^(k+1) - x^k) = " + nx);
        }
    }

    public static void Gauss_Seidel(Function f, double[] x0, double e) {
        System.err.println("\nGauss_Seidel(" + f + ", " + Arrays.toString(x0) + ", " + e + ")");
        k = 0;
        double[] x = Arrays.copyOf(x0, x0.length);
        double fx = f.calc(x);
        int[] sign = new int[x.length];
        for (int i = 0; i < x.length; i++) sign[i] = 1;
        double nx = e;//norm(x^(k+1) - x^k)
        double[] delta = new double[x.length];
        while (nx >= e) {//external step x^(k_)
            k++;
            for (int j = 0; j < x.length; j++) {//internal step for all x^(kj)
                final int fj = j;
                double[] args = new double[x.length];

                for (int l = 0; l < x.length; l++)//find sign (e or -e)
                    if (fj == l) args[l] = x[l] + sign[l] * e;
                    else args[l] = x[l];
                if (f.calc(args) > fx) sign[fj] *= -1;//if wrong way reverse

                Function func = hj -> {
                    for (int l = 0; l < x.length; l++)
                        if (fj == l) args[l] = x[l] + hj[0] * sign[l] * e;
                        else args[l] = x[l];
                    return f.calc(args);
                };
                double h = Optimization1d.localization(func, x[0], 1, e)[0][0];
                double temp = x[j];
                x[j] = x[j] + h * sign[j] * e;//calc x^(kj + 1)
                fx = f.calc(x);
                delta[j] = x[j] - temp;//calc x^(kj + 1) - x^(kj), that we will use in norm(delta)
            }
            nx = norm(delta);
            System.out.println("\nk = " + k + " -----------------------------------");
            System.out.println("f(x^k = )" + f.calc(x));
            System.out.println("norm(x^(k+1) - x^k) = " + nx);
        }
    }
}
