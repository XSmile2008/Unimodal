package com.company;

public class Main {

    public static void main(String[] args) {
        System.out.println("true min is in -2.13863332262411 and = -44.7195867483370");
        double a = -4;
        double b = 2;
        double e = 0.0001;
        double h = (a+b)/2.;
        Optimization1d.localization(3, h, e);
        Optimization1d.dihotomiya(a, b, e, e / 3.);
        Optimization1d.goldenCut(a, b, e);
        Optimization1d.goldenCutM(a, b, e);
        Optimization1d.fibonacciM(a, b, e);
        Optimization1d.parabols(a, h, e);
    }

}
