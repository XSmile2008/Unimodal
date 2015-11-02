package com.company;

public class Main {

    public static void main(String[] args) {
        //optimization1d();
        optimizationNd();
    }

    private static void optimization1d() {
        System.out.println("http://goo.gl/OWuKiS");
        System.out.println("3*x^4 + 5*x^3 - 10*x^2 + 6*x");
        System.out.println("true min is in -2.13863332262411 and = -44.7195867483370");

        Function f = x -> 3 * x[0] * x[0] * x[0] * x[0] + 5 * x[0] * x[0] * x[0] - 10 * x[0] * x[0] + 6 * x[0];
        double a = -4;
        double b = 2;
        double e = 0.0001;
        double h = (a+b)/2.;
        Optimization1d.localization(f, a, h, e);
        Optimization1d.diсhotomy(f, a, b, e, e / 3.);
        Optimization1d.goldenCut(f, a, b, e);
        Optimization1d.goldenCutM(f, a, b, e);
        Optimization1d.fibonacciM(f, a, b, e);
        Optimization1d.parabolas(f, a, h, e);
    }

    private static void optimizationNd() {
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
        double e = 0.0001;
        OptimizatonNd.gradientDescent(f, derivatives, x0, e);
        OptimizatonNd.Gauss_Seidel(f, x0, e);
        OptimizatonNd.classicNewton(f, derivatives, derivatives2, x0, e);
        OptimizatonNd.generalizedNewton(f, derivatives, derivatives2, x0, e);
    }

}
