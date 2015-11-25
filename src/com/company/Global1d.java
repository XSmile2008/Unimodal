package com.company;

import javafx.util.Pair;

import java.util.LinkedList;
import java.util.ListIterator;

/**
 * Created by vladstarikov on 16.11.15.
 */
public class Global1d {

    public static int k, fk;

    public static double[] bruteForce(Function f, double L, double a, double b, double e) {
        k = 2;
        double h = 2 * e / L;
        double x = a + h / 2;
        double fx = f.calc(x);
        double min_x = x;
        double min_fx = fx;
        while ((x += h) < b) {
            k++;
            fx = f.calc(x);
            if (fx < min_fx) {
                min_x = x;
                min_fx = fx;
            }
        }

        fx = f.calc(b);
        if (fx < min_fx) {
            min_x = b;
            min_fx = fx;
        }
        fk = k;
        return new double[] {min_x, min_fx};
    }

    public static double[] bruteForceM(Function f, double L, double a, double b, double e) {
        k = 2;
        double h = 2 * e / L;
        double x = a + h / 2;
        double fx = f.calc(x);
        double min_x = x;
        double min_fx = fx;
        while ((x += h + (fx - min_fx)/L) < b) {
            k++;
            fx = f.calc(x);
            if (fx < min_fx) {
                min_x = x;
                min_fx = fx;
            }
        }

        fx = f.calc(b);
        if (fx < min_fx) {
            min_x = b;
            min_fx = fx;
        }
        fk = k;
        return new double[] {min_x, min_fx};
    }

    public static double[] Piyavskogo(Function f, Function minorant, Function collision, double a, double b, double e) {
        SortedLinkedList<Double> x = new SortedLinkedList<>(Double::compare);
        x.add(a);
        x.add((a + b) / 2);
        x.add(b);

        LinkedList<Pair<Double, Double>> z = new SortedLinkedList<>((o1, o2) -> Double.compare(o1.getValue(), o2.getValue()));//Key - z, Value - f(z);
        double tz = collision.calc(x.get(0), x.get(1));
        z.add(new Pair<>(tz, minorant.calc(tz, x.get(0))));
        tz = collision.calc(x.get(1), x.get(2));
        z.add(new Pair<>(tz, minorant.calc(tz, x.get(1))));

        k = 3; fk = 7;
        double newX, neighbor = 0;
        do {
            k++;
            newX = z.pop().getKey();
            ListIterator<Double> i = x.addI(newX);

            if (i.hasPrevious()) {
                neighbor = i.previous();
                tz = collision.calc(neighbor, newX);
                z.add(new Pair<>(tz, minorant.calc(tz, newX)));
                i.next();//return to newX
                i.next();
                fk += 3;
            }

            if (i.hasNext()) {
                neighbor = i.next();
                tz = collision.calc(newX, neighbor);
                z.add(new Pair<>(tz, minorant.calc(tz, newX)));
                fk += 3;
            }
        } while (Math.abs(neighbor - newX) > e);
        //System.out.println("x = " + x);
        //System.out.println("z = " + z);
        return new double[] {newX, f.calc(newX)};//fk++
    }
}
