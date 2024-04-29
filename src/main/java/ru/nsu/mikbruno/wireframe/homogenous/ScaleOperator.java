package ru.nsu.mikbruno.wireframe.homogenous;

public class ScaleOperator extends Operator {
    private final double[][] mat;

    public ScaleOperator(double sx, double sy, double sz) {
        mat = new double[][] {
                {sx, 0, 0, 0},
                {0, sy, 0, 0},
                {0, 0, sz, 0},
                {0, 0, 0, 1},
        };
    }

    @Override
    protected double[][] matrix() {
        return mat;
    }
}
