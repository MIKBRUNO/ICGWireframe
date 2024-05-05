package ru.nsu.mikbruno.wireframe.homogenous;

import ru.nsu.mikbruno.wireframe.scenes.ArrayListScene;
import ru.nsu.mikbruno.wireframe.MatrixUtils;
import ru.nsu.mikbruno.wireframe.scenes.Scene;
import ru.nsu.mikbruno.wireframe.chains.Chain;

public abstract class Operator {
    protected abstract double[][] matrix();

    public Point3D apply(Point3D p) {
        return new Point3DImpl(MatrixUtils.matrixMultiplication(
                p.get(),
                MatrixUtils.transpose(matrix())
        ));
    }

    public Operator apply(Operator op) {
        Operator og = this;
        return new Operator() {
            private final double[][] mat = MatrixUtils.matrixMultiplication(
                    og.matrix(),
                    op.matrix()
            );

            @Override
            protected double[][] matrix() {
                return mat;
            }
        };
    }

    public Chain<Point3D> apply(Chain<Point3D> chain) {
        Chain<Point3D> res = chain.copy();
        res.setPoints(chain.getPoints().stream().map(this::apply).toList());
        return res;
    }

    public Scene apply(Scene scene) {
        return new ArrayListScene(scene.getChains().stream().map(this::apply).toList());
    }
}
