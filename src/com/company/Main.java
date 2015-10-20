package com.company;

public class Main {

    public static void main(String[] args) {
        System.out.println("true min is in -2.13863332262411 and = -44.7195867483370");
        Optimization1d.localization(3, .1, .01);
        Optimization1d.dihotomiya(-3, 1, 0.1, 0.1 / 3.);
        Optimization1d.goldenCut(-3, 1, 0.01);
        Optimization1d.goldenCutM(-3, 1, 0.01);
        Optimization1d.fibonacciM(-3, 1, 0.01);
        Optimization1d.parabols(-10, 1, .01);
    }

}
