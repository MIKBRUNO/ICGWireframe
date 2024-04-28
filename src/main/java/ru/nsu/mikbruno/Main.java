package ru.nsu.mikbruno;

import ru.nsu.mikbruno.gui.wireframe.MainWindow;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        new MainWindow();
//        Observables observables = new Observables(Map.of(
//                "chain",
//                new ArrayListChain<PointUV>(List.of(
//                        new PointUVImpl(0, 0),
//                        new PointUVImpl(1, 1),
//                        new PointUVImpl(1, 2),
//                        new PointUVImpl(0, 2)
//                )),
//                "spline_segments",
//                new IntObservable(1),
//                "generatricies",
//                new IntObservable(2),
//                "circle_segments",
//                new IntObservable(1)
//        ));
//        BSplineEditor editor = new BSplineEditor(observables);
//        editor.setVisible(true);
    }
}