package com.company;

import com.company.matrix.IncompatibleSizesException;
import com.company.matrix.Matrix;
import com.company.matrix.Vector;

import java.util.Arrays;

/**
 * Created by Starikov on 20.10.15.
 */
public class OptimizatonNd {

    static int k, fk;

    private static double[] gradient(double[] x, Function[] fs) {//-gradient
        double[] g = new double[fs.length];
        for (int i = 0; i < fs.length; i++) g[i] = fs[i].calc(x);
        return g;
    }

    public static double[][] gradientDescent(Function f, Function[] fs, final double[] x0, double e) {
        System.out.println("\ngradientDescent(" + f + ", " + fs + ", " + Arrays.toString(x0) + ", " + e + ")");
        k = 0; fk = 0;
        double[] x = Arrays.copyOf(x0, x0.length);
        double nx = e;//norm(x^(k+1) - x^k)
        double h = 1;
        while (true) try {
            k++;

            double[] g = gradient(x, fs);//step0
            if (Vector.norm(g) < e || nx < e) {
                double[][] r = new double[2][];//exit
                r[0] = x;
                r[1] = new double[]{f.calc(x)};
                return r;
            }

            final double[] finx = x;
            Function func = hh -> {
                try {
                    return f.calc(Vector.substract(finx, Vector.multiply(g, hh[0])));
                } catch (IncompatibleSizesException exception) {
                    exception.printStackTrace();
                    return 0;
                }
            };
            h = Optimization1d.parabolas(func, 0, h, e)[0];//TODO: check h
            fk += Optimization1d.fk;

            double[] xk1 = Vector.substract(x, Vector.multiply(g, h));
            nx = Vector.norm(Vector.substract(xk1, x));
            x = xk1;

            System.out.println("\nk = " + k + " -----------------------------------");
            System.out.println("fk = " + fk);
            System.out.println("g = " + Arrays.toString(g));
            System.out.println("h = " + h);
            System.out.println("f(x^(k + 1) = )" + f.calc(x));
            System.out.println("x^(k + 1) = " + Arrays.toString(x));
            System.out.println("norm(x^(k+1) - x^k) = " + nx);
        } catch (IncompatibleSizesException exception) {
            exception.printStackTrace();
        }
    }

    public static double[][] Gauss_Seidel(Function f, double[] x0, double e) {
        System.out.println("\nGauss_Seidel(" + f + ", " + Arrays.toString(x0) + ", " + e + ")");
        k = 0; fk = 0;
        double[] x = Arrays.copyOf(x0, x0.length);
        double fx = f.calc(x);
        int[] sign = new int[x.length];
        for (int i = 0; i < x.length; i++) sign[i] = 1;
        double nx = e;//norm(x^(k + 1) - x^k)
        double[] delta = new double[x.length];
        while (nx >= e) {//external step x^(k_)
            k++;
            double[] h = new double[x.length]; for (int i = 0; i < x.length; i++) h[i] = 1;
            for (int j = 0; j < x.length; j++) {//internal step for all x^(kj)
                final int fj = j;

                double[] args = Arrays.copyOf(x, x.length);//check sign[j]
                args[j] = x[j] + sign[j] * e;
                if (f.calc(args) > fx) sign[fj] *= -1;//if wrong way reverse//TODO

                Function func = hj -> {
                    double[] doubles = Arrays.copyOf(x, x.length);
                    doubles[fj] = x[fj] + hj[0] * sign[fj];
                    return f.calc(doubles);
                };
                h[j] = Optimization1d.parabolas(func, 0, h[j], e)[0];//TODO: check h
                fk += Optimization1d.fk + 1;
                double temp = x[j];
                x[j] = x[j] + h[j] * sign[j];//calc x^(kj + 1)
                fx = f.calc(x);
                delta[j] = x[j] - temp;//calc x^(kj + 1) - x^(kj), that we will use in norm(delta)
            }
            nx = Vector.norm(delta);
            System.out.println("\nk = " + k + " -----------------------------------");
            System.out.println("fk = " + fk);
            System.out.println("f(x^k = )" + f.calc(x));
            System.out.println("x = " + Arrays.toString(x));
            System.out.println("norm(x^(k+1) - x^k) = " + nx);
        }
        double[][] r = new double[2][];//exit
        r[0] = x;
        r[1] = new double[]{f.calc(x)};
        return r;
    }

    public static double[][] classicNewton(Function f, Function[] fs, Function[][] fss, double[] x0, double e) {
        System.out.println("\nclassicNewton(" + f + ", " + fs + ", " + fss + ", " + Arrays.toString(x0) + ", " + e + ")");
        k = 0;
        double[] x = Arrays.copyOf(x0, x0.length);
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

            double[] xk1 = Vector.substract(x, Matrix.multiply(inverted, fsd));
            nx = Vector.norm(Vector.substract(xk1, x));
            x = xk1;
            System.out.println("\nk = " + k + " -----------------------------------");
            System.out.println("f(x^k = )" + f.calc(x));
            System.out.println("x = " + Arrays.toString(x));
            System.out.println("norm(x^(k+1) - x^k) = " + nx);
        } catch (IncompatibleSizesException incompatibleSizesException) {
            incompatibleSizesException.printStackTrace();
        }
        double[][] r = new double[2][];//exit
        r[0] = x;
        r[1] = new double[]{f.calc(x)};
        return r;
    }

    public static double[][] generalizedNewton(Function f, Function[] fs, Function[][] fss, double[] x0, double e) {
        System.out.println("\ngeneralizedNewton(" + f + ", " + fs + ", " + fss + ", " + Arrays.toString(x0) + ", " + e + ")");
        k = 0; fk = 0;
        double[] x = Arrays.copyOf(x0, x0.length);
        try {
            double [][] inverted = new double[x.length][x.length];
            for (int i = 0; i < x.length; i++)
                for (int j = 0; j < x.length; j++)
                    inverted[i][j] = fss[i][j].calc(x);
            Matrix.invert(inverted);

            double h = 1;
            double nx = e;
            while (nx >= e) {
                k++;
                double[] fsd = new double[x.length];
                for (int i = 0; i < x.length; i++) fsd[i] = fs[i].calc(x);
                double[] p = Matrix.multiply(inverted, fsd);

                final double[] finx = x;
                Function func = hh -> {
                    try {
                        return f.calc(Vector.substract(finx, Vector.multiply(p, hh[0])));
                    } catch (IncompatibleSizesException incompatibleSizesExeption) {
                        incompatibleSizesExeption.printStackTrace();
                        return 0;
                    }
                };
                h = Optimization1d.parabolas(func, 0, h, e)[0];//TODO
                fk += Optimization1d.fk;

                double[] xk1 = Vector.substract(x, Vector.multiply(p, h));
                nx = Vector.norm(Vector.substract(xk1, x));
                x = xk1;
                System.out.println("\nk = " + k + " -----------------------------------");
                System.out.println("fk = " + fk);
                System.out.println("f(x^k = )" + f.calc(x));
                System.out.println("x = " + Arrays.toString(x));
                System.out.println("norm(x^(k+1) - x^k) = " + nx);
            }
        } catch (IncompatibleSizesException incompatibleSizesException) {
            incompatibleSizesException.printStackTrace();
        }
        double[][] r = new double[2][];//exit
        r[0] = x;
        r[1] = new double[]{f.calc(x)};
        return r;
    }
}
