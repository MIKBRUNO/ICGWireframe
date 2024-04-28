package ru.nsu.mikbruno.wireframe;

import ru.nsu.mikbruno.wireframe.chains.ArrayListChainObservable;
import ru.nsu.mikbruno.wireframe.chains.Chain;
import ru.nsu.mikbruno.wireframe.chains.PointUV;
import ru.nsu.mikbruno.wireframe.chains.PointUVImpl;

import java.util.Iterator;
import java.util.function.Consumer;

public final class BSplineProducer {
    private static final double[][] M = new double[][] {
            {-1./6, 3./6, -3./6, 1./6},
            {3./6, -6./6, 3./6, 0},
            {-3./6, 0, 3./6, 0},
            {1./6, 4./6, 1./6, 0},
    };
    public static Chain<PointUV> produceWithCallBack(
            Chain<PointUV> chain,
            int splineSegments,
            Consumer<PointUV> onSegment
    ) {
        if (chain.getPoints().size() < 4) {
            throw new IllegalArgumentException("There should be not less then 4 points in chain");
        }
        Chain<PointUV> result = new ArrayListChainObservable<>();
        Iterator<PointUV> it = chain.getPointsIterator();
        PointUV Pm1 = it.next();
        PointUV P0 = it.next();
        PointUV P1 = it.next();
        while (it.hasNext()) {
            PointUV P2 = it.next();
            for (int i = 0; i < splineSegments; ++i) {
                double t = (double) i / splineSegments;
                double [][] TM = MatrixUtils.matrixMultiplication(
                        new double[][] {{t*t*t, t*t, t, 1}}, M
                );
                double ui = MatrixUtils.matrixMultiplication(
                        TM,
                        new double[][] {{Pm1.getU()}, {P0.getU()}, {P1.getU()}, {P2.getU()}}
                )[0][0];
                double vi = MatrixUtils.matrixMultiplication(
                        TM,
                        new double[][] {{Pm1.getV()}, {P0.getV()}, {P1.getV()}, {P2.getV()}}
                )[0][0];
                PointUV point = new PointUVImpl(ui, vi);
                result.addPoint(point);
                onSegment.accept(point);
            }
            if (!it.hasNext()) {
                double [][] TM = MatrixUtils.matrixMultiplication(
                        new double[][] {{1, 1, 1, 1}}, M
                );
                double ui = MatrixUtils.matrixMultiplication(
                        TM,
                        new double[][] {{Pm1.getU()}, {P0.getU()}, {P1.getU()}, {P2.getU()}}
                )[0][0];
                double vi = MatrixUtils.matrixMultiplication(
                        TM,
                        new double[][] {{Pm1.getV()}, {P0.getV()}, {P1.getV()}, {P2.getV()}}
                )[0][0];
                result.addPoint(new PointUVImpl(ui, vi));
            }
            Pm1 = P0;
            P0 = P1;
            P1 = P2;
        }

        return result;
    }

    public static Chain<PointUV> produce(Chain<PointUV> chain, int splineSegments) {
        return produceWithCallBack(chain, splineSegments, (p) -> {});
    }

    private BSplineProducer() {}
}
