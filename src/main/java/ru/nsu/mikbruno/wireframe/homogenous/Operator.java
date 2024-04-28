package ru.nsu.mikbruno.wireframe.homogenous;

import ru.nsu.mikbruno.wireframe.ArrayListScene;
import ru.nsu.mikbruno.wireframe.MatrixUtils;
import ru.nsu.mikbruno.wireframe.Scene;
import ru.nsu.mikbruno.wireframe.chains.ArrayListChain;
import ru.nsu.mikbruno.wireframe.chains.Chain;

public abstract class Operator {
    protected abstract double[][] matrix();

    public Point3D apply(Point3D p) {
        return new Point3DImpl(MatrixUtils.matrixMultiplication(p.get(), matrix()));
    }

    public Operator apply(Operator op) {
        return new Operator() {
            private final double[][] mat = MatrixUtils.matrixMultiplication(
                    op.matrix(),
                    MatrixUtils.transpose(matrix())
            );

            @Override
            protected double[][] matrix() {
                return mat;
            }
        };
    }

    public Chain<Point3D> apply(Chain<Point3D> chain) {
        return new ArrayListChain<>(chain.getPoints().stream().map(this::apply).toList());
    }

    public Scene apply(Scene scene) {
        return new ArrayListScene(scene.getChains().stream().map(this::apply).toList());
    }
}
