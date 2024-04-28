package ru.nsu.mikbruno.wireframe;

public final class MatrixUtils {
    public static double[][] matrixMultiplication(double[][] A, double[][] B){
        int A_rows = A.length;
        int A_cols = A[0].length;
        int B_cols = B[0].length;
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

    public static double[][] transpose(double[][] M) {
        int n = M.length;
        int m = M[0].length;
        double[][] M_t = new double[m][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                M_t[j][i] = M[i][j];
            }
        }

        return M_t;
    }

    private MatrixUtils() {}
}
