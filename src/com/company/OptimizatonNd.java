package com.company;

/**
 * Created by Starikov on 20.10.15.
 */
public class OptimizatonNd {

    static double k;
    static double fk;

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

    public static double[][] gradientDescent(Function f, Function[] fs, double[] x, double e) {
        k = 0;
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
                    double[] doubles = new double[x.length];
                    for (int j = 0; j < x.length; j++)
                        if (fi == j) doubles[j] = x[j] - hi[0] * ug[j];
                        else doubles[j] = x[j] - h[j] * ug[j];
                    return f.calc(doubles);
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

            for (int i = 0; i < x.length; i++) x[i] -= h[i] * ug[i];//TODO: can be moved to h minimization loop
            System.out.println("\nk = " + k + " -----------------------------------");
            System.out.println("f(x^k = )" + f.calc(x));
            System.out.println("norm(x^(k+1) - x^k) = " + nx);
        }
    }

    public static void Gauss_Seidel(Function f, double[] x, double e) {
        k = 0;
        double fx = f.calc(x);
        int[] sign = new int[x.length]; for (int i : sign) i = 1;
        double nx = e;//norm(x^(k+1) - x^k)
        double[] delta = new double[x.length];
        while (nx >= e) {
            for (int j = 0; j < x.length; j++) {
                //TODO: add sign finding
                final int fj = j;
                Function func = hj -> {
                    double[] doubles = new double[x.length];
                    for (int l = 0; l < x.length; l++)
                        if (fj == l) doubles[l] = x[l] + hj[0] * sign[l] * e;
                        else doubles[l] = x[l];
                    return f.calc(doubles);
                };
                double h = Optimization1d.parabolas(func, x[0], 1, e)[0];
                double temp = x[j];
                x[j] = x[j] + h * sign[j] * e;
                fx = f.calc(x);
                delta[j] = x[j] - temp;
            }
            nx = norm(delta);
        }
    }
}
