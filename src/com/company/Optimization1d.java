package com.company;

/**
 * Created by Starikov on 20.10.15.
 */
public class Optimization1d {

    static private int k;
    static private int fk;

    static int fibonacci(int n) {
        int f_2 = 1;
        int f_1 = 1;
        int f = 1;
        for (int i = 2; i < n; i++) {
            f = f_1 + f_2;
            f_2 = f_1;
            f_1 = f;
        }
        return f;
    }

    static double[][] localization(Function f, double x0, double h, double e) {
        double[] x = new double[3];
        double[] fx = new double[3];
        double X;
        double fX;
        x[0] = x0;
        fx[0] = f.calc(x[0]); fk++;

        do {
            X = x[0] + h;//step0
            fX = f.calc(X); fk++;
            if (fX <= fx[0]) {//step1
                x[1] = X;
                fx[1] = fX;
            } else {
                X = x[0] - h;//step2
                fX = f.calc(X); fk++;
                if (fX <= fx[0]) {//step3
                    h = -h;
                    x[1] = X;
                    fx[1] = fX;
                } else if (Math.abs(h) < e / 2.) {
                    System.out.println("x = " + X + "\nf(x) = " + fX);//TODO
                    double[][] r = new double[2][]; r[0] = x; r[1] = fx;
                    return r;
                } else h /= 2;//step4
            }
        } while (fX > fx[0]);

        x[2] = x[1] + h;//step5
        fx[2] = f.calc(x[2]); fk++;
        while (fx[2] <= fx[1]) {
            x[0] = x[1];
            fx[0] = fx[1];
            x[1] = x[2];
            fx[1] = fx[2];
            x[2] = x[1] + h;//step5
            fx[2] = f.calc(x[2]); fk++;
        }

        if (h < 0) {//swap
            double temp = x[0];
            x[0] = x[2];
            x[2] = temp;

            temp = fx[0];
            fx[0] = fx[2];
            fx[2] = temp;
        }

        /*System.out.println("x0 = " + x[0] + " f(x0) = " + fx[0]);
        System.out.println("x1 = " + x[1] + " f(x1) = " + fx[1]);
        System.out.println("x2 = " + x[2] + " f(x2) = " + fx[2]);*/

        double[][] r = new double[2][]; r[0] = x; r[1] = fx;
        return r;
    }

    static double[] dihotomiya(Function f, double a, double b, double e, double d) { // d = e/3
        System.out.println("\ndihotomiya(" + a + ", " + b + ", " + e + ", " + d + ")");
        k = 0;
        double xs, fs;
        double x1, f1;
        double x2, f2;

        do {
            k++;

            x1 = (a + b - d) / 2.;//step1
            x2 = (a + b + d) / 2.;
            f1 = f.calc(x1);
            f2 = f.calc(x2);

            if (f1 <= f2) {
                b = x2;
                xs = a;
                fs = f1;
            } else {
                a = x1;
                xs = b;
                fs = f2;
            }

        } while (b - a >= e);
        fk = k * 2;
        System.out.println("finish");
        System.out.println("a = " + a + " b = " + b);
        System.out.println("x = " + xs + " f(x) = " + fs);
        System.out.println("k = " + k + " fk = " + fk);
        return  new double[] {xs, fs};
    }

    static double[] goldenCut(Function f, double a, double b, double e) {
        System.out.println("\ngoldenCut(" + a + ", " + b + ", " + e + ")");//step4
        double u = a + (3 - Math.sqrt(5))/2 * (b - a);//step1
        double v = a + b - u;
        double fu = f.calc(u);
        double fv = f.calc(v);
        double xs = 0;
        double fs = 0;

        k = 0;
        do {
            k++;
            if (fu <= fv) {//step 2
                b = v;
                xs = u;
                fs = fu;
                v = u;
                fv = fu;
                u = a + b - v;
                fu = f.calc(u);
            } else {
                a = u;
                xs = v;
                fs = fv;
                u = v;
                fu = fv;
                v = a + b - u;
                fv = f.calc(v);
            }
        } while (b - a >= e);//step3
        fk = k + 2;
        System.out.println("xs = " + xs + " f(xs) = " + fs);
        System.out.println("k = " + k + " fk = " + fk);
        return  new double[] {xs, fs};
    }

    static double[] goldenCutM(Function f, double a, double b, double e) {
        System.out.println("\ngoldenCutM(" + a + ", " + b + ", " + e + ")");//step5
        k = 0;
        fk = 0;
        double u = 0, v = 0, fu = 0, fv = 0, xs = 0, fs = 0;
        do {
            if (u >= v) {//step4
                u = a + (3 - Math.sqrt(5)) / 2 * (b - a);//step1
                v = a + b - u;
                fu = f.calc(u);
                fv = f.calc(v);
                fk += 2;
            }
            if (fu <= fv) {//step 2
                b = v;
                xs = u;
                fs = fu;
                v = u;
                fv = fu;
                u = a + b - v;
                fu = f.calc(u);
            } else {
                a = u;
                xs = v;
                fs = fv;
                u = v;
                fu = fv;
                v = a + b - u;
                fv = f.calc(v);
            }
            k++;
            fk++;
        } while (b - a >= e);
        System.out.println("xs = " + xs + " f(xs) = " + fs);
        System.out.println("k = " + k + " fk = " + fk);
        return  new double[] {xs, fs};
    }

    static double[] fibonacciM(Function f, double a, double b, double e) {
        System.out.println("\nfibonacciM(" + a + ", " + b + ", " + e + ")");//step5
        int n = 0;
        do n++; while ((b - a)/ fibonacci(n + 2) >= e);

        double u = 0, v = 0;
        double fu = 0, fv = 0;
        double xs = 0, fs = 0;

        fk = 0;
        for (k = 1; k <= n; k++) {
            if (u >= v) {
                u = a + ((double) fibonacci(n - k + 1))/(double)(fibonacci(n - k + 3)) * (b - a);//step1
                v = a + b - u;
                fu = f.calc(u);
                fv = f.calc(v);
                fk += 2;
            }
            if (fu <= fv) {//step 2
                b = v;
                xs = u;
                fs = fu;
                v = u;
                fv = fu;
                u = a + b - v;
                if (u < v) {
                    fu = f.calc(u);
                    fk++;
                }
            } else {
                a = u;
                xs = v;
                fs = fv;
                u = v;
                fu = fv;
                v = a + b - u;
                if (u < v) {
                    fv = f.calc(v);
                    fk++;
                }
            }
        }

        System.out.println("xs = " + xs);
        System.out.println("f(xs) = " + fs);
        System.out.println("k = " + k);
        System.out.println("fk = " + fk);
        return  new double[] {xs, fs};
    }

    static double[] parabolas(Function f, double x0, double h, double e) {
    //static void parabolas(double x0, double a, double b, double e) {
        //System.out.println("\nparabolas(" + x0 + ", " + h + ", " + e + ")");
        k = 0;
        fk = 0;

        double[][] xfx = localization(f, x0, h, e);
        double[] x = xfx[0];
        double[] fx = xfx[1];
        double x3, fx3;

        while (true) {
            double xs = x[1] + (1 / 2.) * (((x[2] - x[1]) * (x[2] - x[1]) * (fx[0] - fx[1]) - (x[1] - x[0]) * (x[1] - x[0]) * (fx[2] - fx[1]))
                    / ((x[2] - x[1]) * (fx[0] - fx[1]) + (x[1] - x[0]) * (fx[2] - fx[1])));
            double fxs = f.calc(xs);
            k++; fk++;
            if (Math.abs(xs - x[1]) < e) {
                //System.out.println("x^* = " + xs + " f(x^*) = " + fxs);
                //System.out.println("k = " + k + " fk = " + fk);
                return  new double[] {xs, fxs};
            } else if (xs > x[1]) {
                //x[3] = x[2];
                x3 = x[2];
                x[2] = xs;

                //fx[3] = fx[2];
                fx3 = fx[2];
                fx[2] = fxs;
            } else {
                //x[3] = x[2];
                x3 = x[2];
                x[2] = x[1];
                x[1] = xs;

                //fx[3] = fx[2];
                fx3 = fx[2];
                fx[2] = fx[1];
                fx[1] = fxs;
            }

            if (fx[1]>fx[2]) {
                x[0] = x[1];
                x[1] = x[2];
                x[2] = x3;
                fx[0] = fx[1];
                fx[1] = fx[2];
                fx[2] = fx3;
            }

        }
    }

}
