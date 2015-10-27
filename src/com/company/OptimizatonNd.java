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

    public static double[][] gradientDescent(Function f, Function[] fs, final double[] x0, double e) {
        System.out.println("\ngradientDescent(" + f + ", " + fs + ", " + Arrays.toString(x0) + ", " + e + ")");
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
                    double[] args = Arrays.copyOf(x, x.length);
                    args[fi] = x[fi] - hi[0] * ug[fi];
                    return f.calc(args);
                };
                h[i] = Optimization1d.parabolas(func, 0, 1, e)[0];//TODO: check x[0] for hi∂
            }

            double[] delta = new double[x.length];
            for (int i = 0; i < x.length; i++) delta[i] = x[i] - (x[i] - h[i] * ug[i]);
            nx = norm(delta);

            for (int i = 0; i < x.length; i++) x[i] -= h[i] * ug[i];//TODO: can be moved into h minimization loop
            System.out.println("\nk = " + k + " -----------------------------------");
            System.out.println("ug = " + Arrays.toString(ug));
            System.out.println("h = " + Arrays.toString(h));
            System.out.println("f(x^(k + 1) = )" + f.calc(x));
            System.out.println("x^(k + 1) = " + Arrays.toString(x));
            System.out.println("norm(x^(k+1) - x^k) = " + nx);
        }
    }

    public static void Gauss_Seidel(Function f, double[] x0, double e) {
        System.out.println("\nGauss_Seidel(" + f + ", " + Arrays.toString(x0) + ", " + e + ")");
        k = 0;
        double[] x = Arrays.copyOf(x0, x0.length);
        double fx = f.calc(x);
        int[] sign = new int[x.length];
        for (int i = 0; i < x.length; i++) sign[i] = 1;
        double nx = e;//norm(x^(k + 1) - x^k)
        double[] delta = new double[x.length];
        while (nx >= e) {//external step x^(k_)
            k++;
            for (int j = 0; j < x.length; j++) {//internal step for all x^(kj)
                final int fj = j;

                double[] args = Arrays.copyOf(x, x.length);//check sign[j]
                args[j] = x[j] + sign[j] * e;
                if (f.calc(args) > fx) sign[fj] *= -1;//if wrong way reverse

                Function func = hj -> {
                    double[] doubles = Arrays.copyOf(x, x.length);
                    doubles[fj] = x[fj] + hj[0] * sign[fj] * e;
                    return f.calc(doubles);
                };
                double h = Optimization1d.parabolas(func, x[0], 1, e)[0];
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

    public static void classicNewton(Function f, Function[] fs, Function[][] fss, double[] x0, double e) {
        double[] x = Arrays.copyOf(x0, x0.length);
    }
}
