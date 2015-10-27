package com.company;

import com.company.Matrix.IncompatibleSizesExeption;
import com.company.Matrix.Matrix;
import com.company.Matrix.Vector;

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

    public static double[][] gradientDescent(Function f, Function[] fs, final double[] x0, double e) {
        System.out.println("\ngradientDescent(" + f + ", " + fs + ", " + Arrays.toString(x0) + ", " + e + ")");
        k = 0;
        double[] x = Arrays.copyOf(x0, x0.length);
        double nx = e;//norm(x^(k+1) - x^k)
        while (true) {
            k++;

            double[] ug = ungradient(x, fs);//step0
            if (Vector.norm(ug) < e || nx < e) {
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
                h[i] = Optimization1d.parabolas(func, 0, 1, e)[0];//TODO: check x[0] for hi
            }

            double[] delta = new double[x.length];
            for (int i = 0; i < x.length; i++) delta[i] = x[i] - (x[i] - h[i] * ug[i]);

            /*double[] delta = new double[x.length];
            for (int i = 0; i < x.length; i++) {
                final int fi = i;
                Function func = hi -> {
                    double[] args = Arrays.copyOf(x, x.length);
                    args[fi] = x[fi] - hi[0] * ug[fi];
                    return f.calc(args);
                };
                double h = Optimization1d.parabolas(func, 0, 1, e)[0];//TODO: check x[0] for hi∂
                double temp = x[i];
                x[i] -= h * ug[i];//calc x^(kj + 1)
                delta[i] = x[i] - temp;//calc x^(kj + 1) - x^(kj), that we will use in norm(delta)
            }*/

            nx = Vector.norm(delta);

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
                double h = Optimization1d.parabolas(func, 0, 1, e)[0];
                double temp = x[j];
                x[j] = x[j] + h * sign[j] * e;//calc x^(kj + 1)
                fx = f.calc(x);
                delta[j] = x[j] - temp;//calc x^(kj + 1) - x^(kj), that we will use in norm(delta)
            }
            nx = Vector.norm(delta);
            System.out.println("\nk = " + k + " -----------------------------------");
            System.out.println("f(x^k = )" + f.calc(x));
            System.out.println("norm(x^(k+1) - x^k) = " + nx);
        }
    }

    public static void classicNewton(Function f, Function[] fs, Function[][] fss, double[] x0, double e) {
        System.out.println("\nclassicNewton(" + f + ", " + fs + ", " + fss + ", " + Arrays.toString(x0) + ", " + e + ")");
        k = 0;
        double[] x = Arrays.copyOf(x0, x0.length);
        double[] xk1;
        double nx = e;
        while (nx >= e) try {
            k++;
            double[] fsd = new double[x.length];
            for (int i = 0; i < x.length; i++) fsd[i] = fs[i].calc(x);

            double [][] inverted = new double[x.length][x.length];
            for (int i = 0; i < x.length; i++)
                for (int j = 0; j < x.length; j++)
                    inverted[i][j] = fss[i][j].calc(x);
            Matrix.invert(inverted);

            xk1 = Vector.subtract(x, Matrix.multiply(inverted, fsd));
            nx = Vector.norm(Vector.subtract(xk1, x));
            x = xk1;
            System.out.println("\nk = " + k + " -----------------------------------");
            System.out.println("f(x^k = )" + f.calc(x));
            System.out.println("norm(x^(k+1) - x^k) = " + nx);
        } catch (IncompatibleSizesExeption incompatibleSizesExeption) {
            incompatibleSizesExeption.printStackTrace();
        }
    }

    public static void generalizedNewton(Function f, Function[] fs, Function[][] fss, double[] x0, double e) {
        System.out.println("\ngeneralizedNewton(" + f + ", " + fs + ", " + fss + ", " + Arrays.toString(x0) + ", " + e + ")");
        k = 0;
        double[] x = Arrays.copyOf(x0, x0.length);
        double[] xk1;
        double nx = e;
        while (nx >= e) try {
            k++;
            double[] fsd = new double[x.length];
            for (int i = 0; i < x.length; i++) fsd[i] = fs[i].calc(x);

            double [][] inverted = new double[x.length][x.length];
            for (int i = 0; i < x.length; i++)
                for (int j = 0; j < x.length; j++)
                    inverted[i][j] = fss[i][j].calc(x);
            Matrix.invert(inverted);
            double[] p = Matrix.multiply(inverted, fsd);

            final double[] finx = x;
            Function func = h -> {
                try {
                    return f.calc(Vector.subtract(finx, Vector.multiply(p, h[0])));
                } catch (IncompatibleSizesExeption incompatibleSizesExeption) {
                    incompatibleSizesExeption.printStackTrace();
                    return 0;
                }
            };
            double h = Optimization1d.parabolas(func, 1, 1, e)[0];

            xk1 = Vector.subtract(x, Vector.multiply(p, h));
            nx = Vector.norm(Vector.subtract(xk1, x));
            x = xk1;
            System.out.println("\nk = " + k + " -----------------------------------");
            System.out.println("f(x^k = )" + f.calc(x));
            System.out.println("norm(x^(k+1) - x^k) = " + nx);
        } catch (IncompatibleSizesExeption incompatibleSizesExeption) {
            incompatibleSizesExeption.printStackTrace();
        }
    }
}
