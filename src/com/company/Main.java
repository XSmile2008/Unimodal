package com.company;

public class Main {

    public static void main(String[] args) {
        //optimization1d();
        //optimizationNd1();
        //optimizationNd2();
        //rozenbrok();
        global1d();
    }

    private static void optimization1d() {
        System.out.println("http://goo.gl/OWuKiS");
        System.out.println("3*x^4 + 5*x^3 - 10*x^2 + 6*x");
        System.out.println("true min is in -2.13863332262411 and = -44.7195867483370");

        Function f = x -> 3 * x[0] * x[0] * x[0] * x[0] + 5 * x[0] * x[0] * x[0] - 10 * x[0] * x[0] + 6 * x[0];
        double a = -4;
        double b = 2;
        double e = 1.0E-08;
        double h = (a+b)/2.;
        Optimization1d.localization(f, a, h, e);
        Optimization1d.dichotomy(f, a, b, e, e / 3.);
        Optimization1d.goldenCut(f, a, b, e);
        Optimization1d.goldenCutM(f, a, b, e);
        Optimization1d.fibonacciM(f, a, b, e);
        Optimization1d.parabolas(f, a, h, e);
    }

    private static void optimizationNd1() {
        System.out.println("http://goo.gl/xqiHfB" + " - function minimization");
        System.out.println("http://goo.gl/MfP98X" + " - f'");
        System.out.println("http://goo.gl/8epoIA" + " - f''");
        System.out.println("10*x^2 + 4*x*y + y^2 - 2*x + y");
        System.out.println("true min is in (1/3; -7/6) and = -11/12");

        Function f = x -> 10 * x[0] * x[0] + 4 * x[0] * x[1] + x[1] * x[1] - 2 * x[0] + x[1];
        Function[] derivatives = {
                x -> 20*x[0] + 4*x[1] - 2,
                x -> 4*x[0] + 2*x[1] + 1
        };
        Function[][] derivatives2 = {
                {x -> 20, x -> 4},
                {x -> 4, x -> 2}
        };

        /*Function f = x -> (x[0]-5)*(x[0]-5) + 4*(x[1]-4)*(x[1]-4);
        Function[] derivatives = {
                x -> 2*(x[0]-5),
                x -> 8*(x[1]-4)
        };*/
        double[] x0 = {0, 0};
        double e = 1.0E-08;
        OptimizatonNd.gradientDescent(f, derivatives, x0, e);
        OptimizatonNd.Gauss_Seidel(f, x0, e);
        //OptimizatonNd.classicNewton(f, derivatives, derivatives2, x0, e);
        //OptimizatonNd.generalizedNewton(f, derivatives, derivatives2, x0, e);
    }

    private static void optimizationNd2() {
        System.out.println("http://goo.gl/OCcHnL" + " - function minimization");
        System.out.println("http://goo.gl/cccDLr" + " - f'");
        System.out.println("http://goo.gl/JQsq1w" + " - f''");
        System.out.println("6 * sqrt(x^2 + 4*y^2) + 5*x^2");

        Function f = x -> 6 * Math.sqrt(x[0]*x[0] + 4*x[1]*x[1]) + 5*x[0]*x[0];
        Function[] derivatives = {
                x -> 6*x[0] / Math.sqrt(x[0]*x[0] + 4*x[1]*x[1]) + 10*x[0],
                x -> 24*x[1] / Math.sqrt(x[0]*x[0] + 4*x[1]*x[1])
        };
        Function[][] derivatives2 = {
                {x -> 24*x[1]*x[1] / Math.pow(x[0]*x[0] + 4*x[1]*x[1], 3./2.) + 10, x -> 24*x[0]*x[1] / Math.pow(x[0]*x[0] + 4*x[1]*x[1], 3./2.)},
                {x -> - 24*x[0]*x[1] / Math.pow(x[0]*x[0] + 4*x[1]*x[1], 3./2.),    x -> 24*x[0]*x[0] / Math.pow(x[0]*x[0] + 4*x[1]*x[1], 3./2.)}
        };

        double[] x0 = {2, 2};
        double e = 1.0E-08;
        //OptimizatonNd.gradientDescent(f, derivatives, x0, e);
        //OptimizatonNd.Gauss_Seidel(f, x0, e);
        OptimizatonNd.classicNewton(f, derivatives, derivatives2, x0, e);
        OptimizatonNd.generalizedNewton(f, derivatives, derivatives2, x0, e);
    }

    private static void rozenbrok() {
        System.out.println("http://goo.gl/BJV8uv" + " - function minimization");
        System.out.println("http://goo.gl/ihnjAs" + " - f'");
        System.out.println("(1 - x)^2 + 100 * (y - x^2)^2");
        Function f = x -> Math.pow(1 - x[0], 2) + 100 * Math.pow(x[1] - x[0] * x[0], 2);
        Function[] derivatives = {
                x -> 400*Math.pow(x[0], 3) - 400*x[0]*x[1] + 2*x[0] - 2,
                x -> 200*(x[1] * x[0] * x[0])
        };
        double[] x0 = {0, 0};
        double e = 1.0E-08;
        OptimizatonNd.gradientDescent(f, derivatives, x0, e);
        OptimizatonNd.Gauss_Seidel(f, x0, e);
    }

    private static void global1d() {
        System.out.println("https://goo.gl/bGAAks" + " - function minimization");
        System.out.println("https://goo.gl/eYwOsI" + " - f'");
        System.out.println("https://goo.gl/K1js6h" + " - sup(f')");//L
        System.out.println("cos(4*x)/x^2");

        double a = 2;
        double b = 6;
        double e = 1.0E-04;

        Function f = x -> Math.cos(4 * x[0]) / (x[0] * x[0]);
        Function fs = x -> -(4 * x[0] * Math.sin(4 * x[0]) + 2 * Math.cos(4 * x[0]))/Math.pow(x[0], 3);

        System.out.println("\nbruteForce(" + f + ", " + fs + ", " + a + ", " + b + ", " + e);
        double[] xfx = Global1d.bruteForce(f, fs, a, b, e);
        System.out.println("x = " + xfx[0] + " f(x) = " + xfx[1]);

        System.out.println("\nbruteForceM(" + f + ", " + fs + ", " + a + ", " + b + ", " + e);
        xfx = Global1d.bruteForce(f, fs, a, b, e);
        System.out.println("x = " + xfx[0] + " f(x) = " + xfx[1]);
    }

}
