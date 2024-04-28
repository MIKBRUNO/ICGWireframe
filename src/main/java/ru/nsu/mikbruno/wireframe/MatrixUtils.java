package ru.nsu.mikbruno.wireframe;

public final class MatrixUtils {
    public static double[][] matrixMultiplication(
            double[][] A,
            double[][] B,
            int A_rows, int A_cols, int B_cols
    ){
        double[][] ans = new double[A_rows][B_cols];
        for(int i = 0; i < A_rows; i++){
            for(int j = 0; j < B_cols; j++){
                for(int k = 0; k < A_cols; k++){
                    ans[i][j] += A[i][k]*B[k][j];
                }
            }
        }

        return ans;
    }
}
