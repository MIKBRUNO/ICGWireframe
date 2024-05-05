package ru.nsu.mikbruno.wireframe;

import ru.nsu.mikbruno.interaction.observer.*;
import ru.nsu.mikbruno.wireframe.homogenous.*;
import ru.nsu.mikbruno.wireframe.scenes.BoundingBox;
import ru.nsu.mikbruno.wireframe.scenes.Scene;

public class SceneTransformation {
    private Operator normOperator = new ScaleOperator(1,1,1);
    private Operator rotOperator = new RotateOperator(0, Axis.X);
    private Operator projOp = projOperator(1);
    private final Operator cameraOp = new MoveOperator(0, 0, 10);

    public SceneTransformation(Observables observables) {
        ValueObservable<Scene> scene = observables.getValue("scene");
        DoubleObservable pitch = observables.getValue("pitch");
        DoubleObservable yaw = observables.getValue("yaw");
        ValueObservable<Operator> op = observables.getValue("operator");
        DoubleObservable zoom = observables.getValue("zoom");

        Runnable apply = () -> {
            op.setValue(getOperator());
        };

        Observer zoomObserver = o -> {
            projOp = projOperator(zoom.getValue());
            apply.run();
        };
        zoomObserver.update(null);
        zoom.addObserver(zoomObserver);

        Observer sceneObserver = o -> {
            BoundingBox box = new BoundingBox(scene.getValue());
            Operator offset = new MoveOperator(
                    -box.getCenterX(), -box.getCenterY(), -box.getCenterZ()
            );
            double scaleFactor = Math.max(box.getWidth(), Math.max(box.getHeight(), box.getDepth()));
            normOperator = new ScaleOperator(1/scaleFactor, 1/scaleFactor, 1/scaleFactor)
                    .apply(offset);
            apply.run();
        };
        sceneObserver.update(null);
        scene.addObserver(sceneObserver);

        Observer rotateObserver = o -> {
            Operator yawOp = new RotateOperator(yaw.getValue(), Axis.Y);
            rotOperator = new RotateOperator(pitch.getValue(), Axis.X).apply(yawOp);
            apply.run();
        };
        pitch.addObserver(rotateObserver);
        yaw.addObserver(rotateObserver);
    }

    public Operator getOperator() {
        return projOp.apply(cameraOp.apply(rotOperator.apply(normOperator)));
    }

    private Operator projOperator(double zf) {
        final double zb = 11;
        return new OperatorImpl(new double[][]{
                {1,0,0,0},
                {0,1,0,0},
                {0,0,zb/(zf*(zb-zf)),-zb/(zb-zf)},
                {0,0,1/zf,0}
        });
    }
}
