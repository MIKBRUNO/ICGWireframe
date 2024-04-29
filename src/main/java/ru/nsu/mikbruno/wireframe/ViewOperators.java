package ru.nsu.mikbruno.wireframe;

import ru.nsu.mikbruno.interaction.observer.*;
import ru.nsu.mikbruno.wireframe.homogenous.*;
import ru.nsu.mikbruno.wireframe.scenes.SceneNormalizer;

public class ViewOperators {
    private final DoubleObservable pitch, yaw;
    private final ValueObservable<SceneNormalizer.BoundingBox> bbox;
    private final DoubleObservable plane_z, plane_w, plane_h;
    private Operator rotateOperator;
    private Operator normOperator;
    private Operator frameOperator;
    private Operator resultOperator;

    public ViewOperators(Observables observables) {
        pitch = observables.getValue("pitch");
        yaw = observables.getValue("yaw");
        bbox = observables.getValue("bbox");
        plane_z = observables.getValue("plane_z");
        plane_h = observables.getValue("plane_h");
        plane_w = observables.getValue("plane_w");
        Observable view = observables.getValue("view");

        Observer pitchYawObserver = o -> {
            if (pitch.getValue() > Math.PI / 2) {
                pitch.setValue(Math.PI / 2);
            }
            else if (pitch.getValue() < -Math.PI / 2) {
                pitch.setValue(-Math.PI / 2);
            }   yaw.setValue(0);
            rotateOperator = new RotateOperator(yaw.getValue(), Axis.Y)
                    .apply(new RotateOperator(pitch.getValue(), Axis.X));
        };
        pitchYawObserver.update(null);

        Observer bbpxObserver = o -> {
            SceneNormalizer.BoundingBox box = bbox.getValue();
            double scaleFactor = Math.max(box.getWidth(), Math.max(box.getHeight(), box.getDepth()));
            normOperator = new MoveOperator(-box.getCenterX(), -box.getCenterY(), -box.getCenterZ())
                    .apply(new ScaleOperator(1/scaleFactor, 1/scaleFactor, 1/scaleFactor));
        };
        bbpxObserver.update(null);

        Observer frameObserver = o -> {

        };

        Observer commonObserver = o -> {
            resultOperator = finalizeOperator();
            view.notifyObservers(resultOperator);
        };

        pitch.addObserver(o -> {pitchYawObserver.update(o); commonObserver.update(o);});
        yaw.addObserver(o -> {pitchYawObserver.update(o); commonObserver.update(o);});
        bbox.addObserver(o -> {bbpxObserver.update(o); commonObserver.update(o);});
    }

    private Operator finalizeOperator() {
        return normOperator.apply(rotateOperator);
    }

    public Operator getFinalOperator() {
        return resultOperator;
    }
}
