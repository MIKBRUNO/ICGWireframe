package ru.nsu.mikbruno.wireframe.homogenous;

public class RotateOperator extends Operator {
    public RotateOperator(double radians, Axis axis) {
        switch (axis) {
            case X -> mat = rotX(radians);
            case Y -> mat = rotY(radians);
            case Z -> mat = rotZ(radians);
            default -> mat = rotX(0);
        }
    }

    @Override
    protected double[][] matrix() {
        return mat;
    }

    private final double[][] mat;

    private double[][] rotX(double rad) {
        return new double[][] {
                {1, 0, 0, 0},
                {0, Math.cos(rad), -Math.sin(rad), 0},
                {0, Math.sin(rad), Math.cos(rad), 0},
                {0, 0, 0, 1},
        };
    }

    private double[][] rotY(double rad) {
        return new double[][] {
                {Math.cos(rad), 0, Math.sin(rad), 0},
                {0, 1, 0, 0},
                {-Math.sin(rad), 0, Math.cos(rad), 0},
                {0, 0, 0, 1},
        };
    }

    private double[][] rotZ(double rad) {
        return new double[][] {
                {Math.cos(rad), -Math.sin(rad), 0, 0},
                {Math.sin(rad), Math.cos(rad), 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1},
        };
    }
}
