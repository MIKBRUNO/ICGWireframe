package ru.nsu.mikbruno.wireframe.homogenous;

public class OperatorImpl extends Operator {
    private final double[][] mat;

    public OperatorImpl(double[][] mat) {
        this.mat = mat;
    }

    @Override
    protected double[][] matrix() {
        return mat;
    }
}
