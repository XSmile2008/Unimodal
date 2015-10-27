package com.company.Matrix;

import java.util.Arrays;

/**
 * Created by vladstarikov on 27.10.15.
 */
public class Matrix {

    public static void invert(double A[][]) throws IncompatibleSizesExeption {//not my
        int n = A.length;
        int row[] = new int[n];
        int col[] = new int[n];
        double temp[] = new double[n];
        int hold, I_pivot, J_pivot;
        double pivot, abs_pivot;

        if (A[0].length != n) throw new IncompatibleSizesExeption();
        // установиим row и column как вектор изменений.
        for (int k = 0; k < n; k++) {
            row[k] = k;
            col[k] = k;
        }
        // начало главного цикла
        for (int k = 0; k < n; k++) {
            // найдем наибольший элемент для основы
            pivot = A[row[k]][col[k]];
            I_pivot = k;
            J_pivot = k;
            for (int i = k; i < n; i++) {
                for (int j = k; j < n; j++) {
                    abs_pivot = Math.abs(pivot);
                    if (Math.abs(A[row[i]][col[j]]) > abs_pivot) {
                        I_pivot = i;
                        J_pivot = j;
                        pivot = A[row[i]][col[j]];
                    }
                }
            }
            if (Math.abs(pivot) < 1.0E-10) {
                System.out.println("Matrix is singular !");
                return;
            }
            //перестановка к-ой строки и к-ого столбца с стобцом и строкой, содержащий основной элемент(pivot основу)
            hold = row[k];
            row[k] = row[I_pivot];
            row[I_pivot] = hold;
            hold = col[k];
            col[k] = col[J_pivot];
            col[J_pivot] = hold;
            // k-ую строку с учетом перестановок делим на основной элемент
            A[row[k]][col[k]] = 1.0 / pivot;
            for (int j = 0; j < n; j++) {
                if (j != k) {
                    A[row[k]][col[j]] = A[row[k]][col[j]] * A[row[k]][col[k]];
                }
            }
            // внутренний цикл
            for (int i = 0; i < n; i++) {
                if (k != i) {
                    for (int j = 0; j < n; j++) {
                        if (k != j) {
                            A[row[i]][col[j]] = A[row[i]][col[j]] - A[row[i]][col[k]] *
                                    A[row[k]][col[j]];
                        }
                    }
                    A[row[i]][col[k]] = -A[row[i]][col[k]] * A[row[k]][col[k]];
                }
            }
        }
        // конец главного цикла

        // переставляем назад rows
        for (int j = 0; j < n; j++) {
            for (int i = 0; i < n; i++) {
                temp[col[i]] = A[row[i]][j];
            }
            for (int i = 0; i < n; i++) {
                A[i][j] = temp[i];
            }
        }
        // переставляем назад columns
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                temp[row[j]] = A[i][col[j]];
            }
            for (int j = 0; j < n; j++) {
                A[i][j] = temp[j];
            }
        }
    }

    public static double determinant(double A[][]) throws IncompatibleSizesExeption {
        int n = A.length;
        double D = 1.0;                 // определитель
        double B[][] = new double[n][n];  // рабочая матрица
        int row[] = new int[n];
        int hold, I_pivot;
        double pivot;
        double abs_pivot;

        if (A[0].length != n) throw new IncompatibleSizesExeption();
        // создаем рабочую матрицу
        for (int i = 0; i < n; i++)
            B[i] = Arrays.copyOf(A[i], n);
        // заполняем вектор перестановок
        for (int k = 0; k < n; k++) {
            row[k] = k;
        }
        // начало основного цикла
        for (int k = 0; k < n - 1; k++) {
            // находим наиболший элемент для основы
            pivot = B[row[k]][k];
            abs_pivot = Math.abs(pivot);
            I_pivot = k;
            for (int i = k; i < n; i++) {
                if (Math.abs(B[row[i]][k]) > abs_pivot) {
                    I_pivot = i;
                    pivot = B[row[i]][k];
                    abs_pivot = Math.abs(pivot);
                }
            }
            // если нашлась такая основа, то меняем знак определителя и меняем местами столбцы
            if (I_pivot != k) {
                hold = row[k];
                row[k] = row[I_pivot];
                row[I_pivot] = hold;
                D = -D;
            }
            // проверка на ноль
            if (abs_pivot < 1.0E-10) {
                return 0.0;
            } else {
                D = D * pivot;
                // делим на основу
                for (int j = k + 1; j < n; j++) {
                    B[row[k]][j] = B[row[k]][j] / B[row[k]][k];
                }
                //  внутренний цикл
                for (int i = 0; i < n; i++) {
                    if (i != k) {
                        for (int j = k + 1; j < n; j++) {
                            B[row[i]][j] = B[row[i]][j] - B[row[i]][k] * B[row[k]][j];
                        }
                    }
                }
            }
            // конец внутреннего цикла
        }
        // конец главного цикла
        return D * B[row[n - 1]][n - 1];
    }

    public static double[][] add(double A[][], double B[][]) throws IncompatibleSizesExeption {
        if (B.length != A.length || B[0].length != A[0].length) throw new IncompatibleSizesExeption();
        double[][] C = new double[A.length][A[0].length];
        for (int i = 0; i < A.length; i++)
            for (int j = 0; j < A[0].length; j++)
                C[i][j] = A[i][j] + B[i][j];
        return C;
    }

    public static double[][] subtract(double A[][], double B[][]) throws IncompatibleSizesExeption {
        if (B.length != A.length || B[0].length != A[0].length) throw new IncompatibleSizesExeption();
        double[][] C = new double[A.length][A[0].length];
        for (int i = 0; i < A.length; i++)
            for (int j = 0; j < A[0].length; j++)
                C[i][j] = A[i][j] - B[i][j];
        return C;
    }

    public static double[] multiply(double A[][], double B[]) {
        double[] C = new double[A.length];
        for (int i = 0; i < A.length; i++)
            for (int j = 0; j < A.length; j++)
                C[i] +=  A[i][j] * B[j];
        return C;
    }

    public static double[][] multiply(final double A[][], final double B[][]) throws IncompatibleSizesExeption{
        if (B.length != A[0].length) throw new IncompatibleSizesExeption();
        double C[][] = new double[A.length][B[0].length];
        for (int i = 0; i < A.length; i++)
            for (int j = 0; j < B.length; j++)
                for (int k = 0; k < A[0].length; k++)
                    C[i][j] += A[i][k] * B[k][j];
        return C;
    }

    public static boolean equals(double A[][], double B[][]) throws IncompatibleSizesExeption {
        if (B.length != A.length || B[0].length != A[0].length) throw new IncompatibleSizesExeption();
        for (int i = 0; i < A.length; i++)
            for (int j = 0; j < A[0].length; j++)
                if (A[i][j] != B[i][j]) return false;
        return true;
    }

    public static String toString(double A[][]) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[0].length; j++)
                if (j != A[0].length - 1) stringBuilder.append(A[i][j] + " ");
                else stringBuilder.append(A[i][j]);
            if (i != A.length - 1) stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    /*public static void main(String[] args) throws IncompatibleSizesExeption {
        double[][] A = new double[][]{{1, 2}, {3, 4}};
        double[][] B = new double[][]{{1, 2}, {3, 4}};
        Matrix.invert(B);
        System.out.println(Matrix.toString(Matrix.multiply(A, B)));
    }*/
}
