package com.company;

/**
 * Created by vladstarikov on 16.11.15.
 */
public class Global1d {

    static int k, fk;

    public static double[] bruteForce(Function f, Function fs, double a, double b, double e) {
        k = 1;
        double L = Global1d.sup(fs, a, b, e);
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
        System.out.println(k);
        return new double[] {min_x, min_fx};
    }

    public static double[] bruteForceM(Function f, Function fs, double a, double b, double e) {
        k = 1;
        double L = Global1d.sup(fs, a, b, e);
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
        System.out.println(k);
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

    public static double[] Piyavskogo(Function f, Function fs, double a, double b, double e) {
        double L = Global1d.sup(fs, a, b, e);

        Function minorant = x -> {
            double fsy = fs.calc(x[1]);
            return f.calc(x[1]) + 1/(2*L) * fsy*fsy - L/2 * (x[0] - x[1] - fsy/L);
        };

        return null;
    }

    private static double minorant(Function f, Function fs, double L, double x, double y) {
        double fsy = fs.calc(y);
        return f.calc(y) + 1/(2*L) * fsy*fsy - L/2 * (x - y - fsy/L);
    }
}
