package ru.nsu.mikbruno.wireframe;

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
                .map(p -> (Chain<Point3D>) new ArrayListChain<Point3D>(
                        List.of(new Point3DImpl(p))
                ))
                .toList();
        for (int i = 0; i < generatrixCount; ++i) {
            scene.addChain(spline3d);
            RotateOperator operator = new RotateOperator((2 * Math.PI * i)/generatrixCount, Axis.Y);
            spline3d = operator.apply(spline3d);
            for (int j = 0; j < circleSegments; ++j) {
                for (int c = 0; c < circles.size(); ++c) {
                    circles.get(c).addPoint(operator.apply(segmentPoints3d.get(c)));
                }
                operator = new RotateOperator(
                        (2 * Math.PI * (i * generatrixCount + j)) / (generatrixCount * circleSegments),
                        Axis.Y
                );
            }
        }
        for (Chain<Point3D> circle : circles) {
            scene.addChain(circle);
        }

        return scene;
    }

    private SceneProducer() {}
}
