package com.company;

public class Main {

    static private int k;
    static private int fk;

    public static void main(String[] args) {
        System.out.println("true min is in -2.13863332262411 and = -44.7195867483370");
        localization(3, .1, .01);
        dihotomiya(-3, 1, 0.1, 0.1/3.);
        goldenCut(-3, 1, 0.01);
        goldenCutM(-3, 1, 0.01);
        fibonacciM(-3, 1, 0.01);
        parabols(-10, 1, .01);
    }

    static double f(double x) {
        return 3*x*x*x*x + 5*x*x*x - 10*x*x + 6*x;
    }

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

    static double[][] localization(double x0, double h, double e) {
        double[] x = new double[3];
        double[] fx = new double[3];
        double X;
        double fX;
        x[0] = x0;
        fx[0] = f(x[0]); fk++;

        do {
            X = x[0] + h;//step0
            fX = f(X); fk++;
            if (fX <= fx[0]) {//step1
                x[1] = X;
                fx[1] = fX;
            } else {
                X = x[0] - h;//step2
                fX = f(X); fk++;
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
        fx[2] = f(x[2]); fk++;
        while (fx[2] <= fx[1]) {
            x[0] = x[1];
            fx[0] = fx[1];
            x[1] = x[2];
            fx[1] = fx[2];
            x[2] = x[1] + h;//step5
            fx[2] = f(x[2]); fk++;
        }

        if (h < 0) {//swap
            double temp = x[0];
            x[0] = x[2];
            x[2] = temp;

            temp = fx[0];
            fx[0] = fx[2];
            fx[2] = temp;
        }

        System.out.println("x0 = " + x[0] + " f(x0) = " + fx[0]);
        System.out.println("x1 = " + x[1] + " f(x1) = " + fx[1]);
        System.out.println("x2 = " + x[2] + " f(x2) = " + fx[2]);

        double[][] r = new double[2][]; r[0] = x; r[1] = fx;
        return r;
    }

    static void dihotomiya(double a, double b, double e, double d) {
        System.out.println("\ndihotomiya(" + a + ", " + b + ", " + e + ", " + d + ")");
        k = 0;
        double xs;
        double fs;
        double x1;
        double x2;
        double f1;
        double f2;

        do {
            k++;

            x1 = (a + b - d) / 2.;//step1
            x2 = (a + b + d) / 2.;
            f1 = f(x1);
            f2 = f(x2);

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
        double fk = k * 2;
        System.out.println("finish");
        System.out.println("a = " + a + " b = " + b);
        System.out.println("x = " + xs + " f(x) = " + fs);
        System.out.println("k = " + k + " fk = " + fk);
    }

    static void goldenCut(double a, double b, double e) {
        System.out.println("\ngoldenCut(" + a + ", " + b + ", " + e + ")");//step4
        double u = a + (3 - Math.sqrt(5))/2 * (b - a);//step1
        double v = a + b - u;
        double fu = f(u);
        double fv = f(v);
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
                fu = f(u);
            } else {
                a = u;
                xs = v;
                fs = fv;
                u = v;
                fu = fv;
                v = a + b - u;
                fv = f(v);
            }
        } while (b - a >= e);//step3
        fk = k + 2;
        System.out.println("xs = " + xs + " f(xs) = " + fs);
        System.out.println("k = " + k + " fk = " + fk);
    }

    static void goldenCutM(double a, double b, double e) {
        System.out.println("\ngoldenCutM(" + a + ", " + b + ", " + e + ")");//step5
        k = 0;
        fk = 0;
        double u = 0, v = 0, fu = 0, fv = 0, xs = 0, fs = 0;
        do {
            if (u >= v) {//step4
                u = a + (3 - Math.sqrt(5)) / 2 * (b - a);//step1
                v = a + b - u;
                fu = f(u);
                fv = f(v);
                fk += 2;
            }
            if (fu <= fv) {//step 2
                b = v;
                xs = u;
                fs = fu;
                v = u;
                fv = fu;
                u = a + b - v;
                fu = f(u);
            } else {
                a = u;
                xs = v;
                fs = fv;
                u = v;
                fu = fv;
                v = a + b - u;
                fv = f(v);
            }
            k++;
            fk++;
        } while (b - a >= e);
        System.out.println("xs = " + xs + " f(xs) = " + fs);
        System.out.println("k = " + k + " fk = " + fk);
    }

    static void fibonacciM(double a, double b, double e) {
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
                fu = f(u);
                fv = f(v);
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
                    fu = f(u);
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
                    fv = f(v);
                    fk++;
                }
            }
        }

        System.out.println("xs = " + xs);
        System.out.println("f(xs) = " + fs);
        System.out.println("k = " + k);
        System.out.println("fk = " + fk);
    }

    static void parabols(double x0, double h, double e) {
        System.out.println("\nparabols(" + x0 + ", " + h + ", " + e + ")");
        k = 0;
        fk = 0;

        double[][] xfx = localization(x0, h, e);
        double[] x = xfx[0];
        double[] fx = xfx[1];

        while (true) {
            double xs = x[1] + (1 / 2.) * (((x[2] - x[1]) * (x[2] - x[1]) * (fx[0] - fx[1]) - (x[1] - x[0]) * (x[1] - x[0]) * (fx[2] - fx[1]))
                    / ((x[2] - x[1]) * (fx[0] - fx[1]) + (x[1] - x[0]) * (fx[2] - fx[1])));
            double fxs = f(xs);
            k++; fk++;
            //System.out.println("k = " + k + " xs = " + xs + " f(xs) = " + fxs);
            if (Math.abs(xs - x[1]) < e) {
                System.out.println("x^* = " + xs + " f(x^*) = " + fxs);
                System.out.println("k = " + k + " fk = " + fk);
                return;
            } else if (xs > x[1]) {
                //x[3] = x[2];
                x[2] = xs;

                //fx[3] = fx[2];
                fx[2] = fxs;
            } else {
                //x[3] = x[2];
                x[2] = x[1];
                x[1] = xs;

                //fx[3] = fx[2];
                fx[2] = fx[1];
                fx[1] = fxs;
            }
        }
    }
}
