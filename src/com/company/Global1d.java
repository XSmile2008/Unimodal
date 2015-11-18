package com.company;

import javafx.util.Pair;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by vladstarikov on 16.11.15.
 */
public class Global1d {

    static int k, fk;

    public static double[] bruteForce(Function f, double L, double a, double b, double e) {
        k = 1;
        double h = 2 * e / L;
        double x = a + h /2;
        double fx = f.calc(x);
        double min_x = x;
        double min_fx = fx;
        while (x < b) {
            k++;
            x += h;
            fx = f.calc(x);
            if (fx < min_fx) {
                min_x = x;
                min_fx = fx;
            }
        }
        System.out.println("h = " + h);
        System.out.println("k = " + k);
        return new double[] {min_x, min_fx};
    }

    public static double[] bruteForceM(Function f, double L, double a, double b, double e) {
        k = 1;
        double h = 2 * e / L;
        double x = a + h /2;
        double fx = f.calc(x);
        double min_x = x;
        double min_fx = fx;
        while (x < b) {
            k++;
            x += h + (fx - min_fx)/L;
            fx = f.calc(x);
            if (fx < min_fx) {
                min_x = x;
                min_fx = fx;
            }
        }
        System.out.println("h = " + h);
        System.out.println("k = " + k);
        return new double[] {min_x, min_fx};
    }

    public static double sup(Function f, double a, double b, double e) {
        double h = e/2;
        double max;
        double x = a;
        max = f.calc(x);
        while (x < b) {
            x += h;
            double fx = f.calc(x);
            if (fx > max) max = fx;
        }
        return max;
    }

    public static void Piyavskogo(Function f, Function fs, double L, double a, double b, double e) {
        Function minorant = x -> f.calc(x[1]) - L * Math.abs(x[0] - x[1]);//TODO: move to main
        Function collision = y -> (f.calc(y[0]) - f.calc(y[1]) + L*y[0] + L*y[1])/2*L;//TODO: check

        List<Double> x = new SortedLinkedList<>((o1, o2) -> (int) Math.signum(o1 - o2));
        x.add(a);
        x.add((a + b) / 2);
        x.add(b);

        LinkedList<Pair<Double, Double>> z = new SortedLinkedList<>((o1, o2) -> (int) Math.signum(o1.getValue() - o2.getValue()));
        double tz = collision.calc(x.get(0), x.get(1));
        z.add(new Pair<>(tz, f.calc(tz)));
        tz = collision.calc(x.get(1), x.get(2));
        z.add(new Pair<>(tz, f.calc(tz)));

        Double newX;
        do {
            newX = z.pop().getKey();
            x.add(newX);
            tz = collision.calc(newX, x.get(x.indexOf(newX) - 1));
            z.add(new Pair<>(tz, f.calc(tz)));
            tz = collision.calc(newX, x.get(x.indexOf(newX) + 1));
            z.add(new Pair<>(tz, f.calc(tz)));
            System.out.println("nyan");
        } while (x.get(x.indexOf(newX)) - newX > e);
        System.out.println(x);
        System.out.println(z);
    }
}
