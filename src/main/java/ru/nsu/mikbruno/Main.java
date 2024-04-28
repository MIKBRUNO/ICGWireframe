package ru.nsu.mikbruno;

import ru.nsu.mikbruno.gui.bspline.BSplineEditor;
import ru.nsu.mikbruno.interaction.observer.IntObservable;
import ru.nsu.mikbruno.interaction.observer.Observables;
import ru.nsu.mikbruno.wireframe.ArrayListChain;
import ru.nsu.mikbruno.wireframe.PointUV;
import ru.nsu.mikbruno.wireframe.PointUVImpl;

import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        Observables observables = new Observables(Map.of(
                "chain",
                new ArrayListChain<PointUV>(List.of(
                        new PointUVImpl(0, 0),
                        new PointUVImpl(1, 1),
                        new PointUVImpl(1, 2),
                        new PointUVImpl(0, 2)
                )),
                "spline_segments",
                new IntObservable(1),
                "generatricies",
                new IntObservable(2),
                "circle_segments",
                new IntObservable(1)
        ));
        BSplineEditor editor = new BSplineEditor(observables);
        editor.setVisible(true);
    }
}