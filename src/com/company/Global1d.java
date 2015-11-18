package com.company;

import javafx.util.Pair;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

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

    public static void Piyavskogo(Function f, Function minorant, Function collision, double a, double b, double e) {
        List<Double> x = new SortedLinkedList<>((o1, o2) -> (int) Math.signum(o1 - o2));
        x.add(a);
        x.add((a + b) / 2);
        x.add(b);

        LinkedList<Pair<Double, Double>> z = new SortedLinkedList<>((o1, o2) -> (int) Math.signum(o1.getValue() - o2.getValue()));
        double tz = collision.calc(x.get(0), x.get(1));
        z.add(new Pair<>(tz, minorant.calc(tz, x.get(0))));
        tz = collision.calc(x.get(1), x.get(2));
        z.add(new Pair<>(tz, minorant.calc(tz, x.get(1))));

        double newX, prev, next = 0;//TODO: remove prev and next
        do {
            newX = z.pop().getKey();
            x.add(newX);
            ListIterator<Double> i = x.listIterator(x.indexOf(newX));

            if (i.hasPrevious()) {
                prev = i.previous();
                tz = collision.calc(prev, newX);
                z.add(new Pair<>(tz, minorant.calc(tz, newX)));
            }

            i.next();
            if (i.hasNext()) {
                i.next();
                next = i.next();
                tz = collision.calc(newX, next);
                z.add(new Pair<>(tz, minorant.calc(tz, newX)));
            }
        } while (next - newX > e);
        System.out.println("x = " + newX + "f(x) = " + f.calc(newX));
        System.out.println(x);
        System.out.println(z);
    }
}
