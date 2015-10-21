package com.company;

public class Main {

    public static void main(String[] args) {
        optimization1d();
    }

    private static void optimization1d() {
        System.out.println("3*x^4 + 5*x^3 - 10*x^2 + 6*x");
        System.out.println("true min is in -2.13863332262411 and = -44.7195867483370");
        double a = -4;
        double b = 2;
        double e = 0.0001;
        double h = (a+b)/2.;
        Optimization1d.localization(a, h, e);
        Optimization1d.dihotomiya(a, b, e, e / 3.);
        Optimization1d.goldenCut(a, b, e);
        Optimization1d.goldenCutM(a, b, e);
        Optimization1d.fibonacciM(a, b, e);
        Optimization1d.parabols(a, h, e);
    }

}
