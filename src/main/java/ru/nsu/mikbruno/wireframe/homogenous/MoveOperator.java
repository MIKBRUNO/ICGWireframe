package ru.nsu.mikbruno.wireframe.homogenous;

public class MoveOperator extends Operator {
    private final double[][] mat;

    public MoveOperator(double ox, double oy, double oz) {
        mat = new double[][] {
                {1, 0, 0, ox},
                {0, 1, 0, oy},
                {0, 0, 1, oz},
                {0, 0, 0, 1},
        };
    }

    @Override
    protected double[][] matrix() {
        return mat;
    }
}
