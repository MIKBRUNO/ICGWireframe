package ru.nsu.mikbruno.wireframe.scenes;

import ru.nsu.mikbruno.wireframe.BSplineProducer;
import ru.nsu.mikbruno.wireframe.chains.ArrayListChain;
import ru.nsu.mikbruno.wireframe.chains.Chain;
import ru.nsu.mikbruno.wireframe.chains.PointUV;
import ru.nsu.mikbruno.wireframe.homogenous.Axis;
import ru.nsu.mikbruno.wireframe.homogenous.Point3D;
import ru.nsu.mikbruno.wireframe.homogenous.Point3DImpl;
import ru.nsu.mikbruno.wireframe.homogenous.RotateOperator;

import java.util.ArrayList;
import java.util.List;

public final class SceneProducer {
    public static Scene produce(Chain<PointUV> splineRef, int splineSegments, int generatrixCount, int circleSegments) {
        List<PointUV> segmentPoints = new ArrayList<>();
        Chain<PointUV> spline = BSplineProducer.produceWithCallBack(splineRef, splineSegments, segmentPoints::add);
        Chain<Point3D> spline3d = new ArrayListChain<>(spline
                        .getPoints().stream()
                        .map(p -> (Point3D) new Point3DImpl(p))
                        .toList()
        );
        List<Point3D> segmentPoints3d = segmentPoints
                .stream()
                .map(p -> (Point3D) new Point3DImpl(p))
                .toList();
        Scene scene = new ArrayListScene();
        List<Chain<Point3D>> circles = segmentPoints.stream()
                .map(p -> (Chain<Point3D>) new ArrayListChain<Point3D>())
                .toList();
        for (int i = 0; i < generatrixCount; ++i) {
            double angle = 2 * Math.PI * i / (double) generatrixCount;
            RotateOperator operator = new RotateOperator(angle, Axis.Y);
            scene.addChain(operator.apply(spline3d));
            for (int j = 0; j < circleSegments; ++j) {
                angle = 2 * Math.PI * (i * circleSegments + j) / (double) (generatrixCount * circleSegments);
                operator = new RotateOperator(angle, Axis.Y);
                for (int c = 0; c < circles.size(); ++c) {
                    circles.get(c).addPoint(operator.apply(segmentPoints3d.get(c)));
                }
            }
        }
        for (Chain<Point3D> circle : circles) {
            circle.setClosure(true);
            scene.addChain(circle);
        }

        return scene;
    }

    private SceneProducer() {}
}
