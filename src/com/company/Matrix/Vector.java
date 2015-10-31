package com.company.matrix;

/**
 * Created by vladstarikov on 27.10.15.
 */
public class Vector {

    public static double[] add(double A[], double B[]) throws IncompatibleSizesException {
        if (B.length != A.length) throw new IncompatibleSizesException();
        double[] C = new double[A.length];
        for (int i = 0; i < A.length; i++)
                C[i] = A[i] + B[i];
        return C;
    }

    public static double[] substract(double A[], double B[]) throws IncompatibleSizesException {
        if (B.length != A.length) throw new IncompatibleSizesException();
        double[] C = new double[A.length];
        for (int i = 0; i < A.length; i++)
                C[i] = A[i] - B[i];
        return C;
    }

    public static boolean equals(double A[], double B[]) throws IncompatibleSizesException {
        if (B.length != A.length) throw new IncompatibleSizesException();
        for (int i = 0; i < A.length; i++)
                if (A[i] != B[i]) return false;
        return true;
    }

    public static double[] multiply(double A[], double B[]) {
        double[] C = new double[A.length];
        for (int i = 0; i < A.length; i++)
                C[i] =  A[i] * B[i];
        return C;
    }

    public static double[] multiply(double A[], double B) {
        double[] C = new double[A.length];
        for (int i = 0; i < A.length; i++)
            C[i] =  A[i] * B;
        return C;
    }

    public static double norm(double[] vector) {
        double sum  = 0;
        for (double d : vector) sum += d*d;
        return Math.sqrt(sum);
    }

}
