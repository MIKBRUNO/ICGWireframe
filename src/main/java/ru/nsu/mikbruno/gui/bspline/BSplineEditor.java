package ru.nsu.mikbruno.gui.bspline;

import ru.nsu.mikbruno.interaction.observer.IntObservable;
import ru.nsu.mikbruno.interaction.observer.Observables;
import ru.nsu.mikbruno.wireframe.ArrayListChain;
import ru.nsu.mikbruno.wireframe.PointUV;
import ru.nsu.mikbruno.wireframe.PointUVImpl;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class BSplineEditor extends JDialog {
    private static final String NAME = "B-Spline Editor";
    private static final Dimension MINIMUM_SIZE = new Dimension(640, 480);
    private static final Dimension PREFERRED_SIZE = new Dimension(720, 480);
    private static final boolean RESIZABLE = true;

    public BSplineEditor(Observables applicationObservables) {
        setTitle(NAME);
        setPreferredSize(PREFERRED_SIZE);
        setMinimumSize(MINIMUM_SIZE);
        setResizable(RESIZABLE);
        setLocationByPlatform(true);
        setLayout(new BorderLayout());


        ArrayListChain<PointUV> chain = applicationObservables.getValue("chain");
        PointUVObservable currentPoint = new PointUVObservable(null);
        JPanel settings = new JPanel(new GridLayout(3, 4));

        JSpinner pointsSpinner = new JSpinner(new SpinnerNumberModel(
                chain.getPoints().size(), 4, 10, 1
        ));
        chain.addObserver(o -> pointsSpinner.setValue(chain.getPoints().size()));
        pointsSpinner.addChangeListener(e -> {
            int newSize = (int) pointsSpinner.getValue();
            int size = chain.getPoints().size();
            if (newSize > size) {
                for (int i = size; i < newSize; ++i) {
                    chain.addPoint(new PointUVImpl(0, 0));
                }
            }
            else {
                for (int i = newSize; i < size; ++i) {
                    chain.removePoint();
                }
            }
        });

        IntObservable splineSeg = applicationObservables.getValue("spline_segments");
        JSpinner splineSegSpinner = new JSpinner(new SpinnerNumberModel(
                splineSeg.getValue(), 1, 100, 1
        ));
        splineSeg.addObserver(splineSegSpinner::setValue);
        splineSegSpinner.addChangeListener(e -> splineSeg.setValue((Integer) splineSegSpinner.getValue()));

        IntObservable gens = applicationObservables.getValue("generatricies");
        JSpinner gensSpinner = new JSpinner(new SpinnerNumberModel(
                gens.getValue(), 1, 100, 1
        ));
        gens.addObserver(gensSpinner::setValue);
        gensSpinner.addChangeListener(e -> gens.setValue((Integer) gensSpinner.getValue()));

        IntObservable circleSeg = applicationObservables.getValue("circle_segments");
        JSpinner circleSegSpinner = new JSpinner(new SpinnerNumberModel(
                circleSeg.getValue(), 1, 100, 1
        ));
        circleSeg.addObserver(circleSegSpinner::setValue);
        circleSegSpinner.addChangeListener(e -> circleSeg.setValue((Integer) circleSegSpinner.getValue()));

        JSpinner currentPointUSpinner = new JSpinner(new SpinnerNumberModel(
                currentPoint.getU(), -100., 100., .5
        ));
        JSpinner currentPointVSpinner = new JSpinner(new SpinnerNumberModel(
                currentPoint.getV(), -100., 100., .5
        ));
        currentPoint.addObserver(o -> {
            currentPointUSpinner.setValue(currentPoint.getU());
            currentPointVSpinner.setValue(currentPoint.getV());
        });
        currentPointUSpinner.addChangeListener(e -> currentPoint.setUV(
                (Double) currentPointUSpinner.getValue(),
                currentPoint.getV()
        ));
        currentPointVSpinner.addChangeListener(e -> currentPoint.setUV(
                currentPoint.getU(),
                (Double) currentPointVSpinner.getValue()
        ));

        settings.add(new Label("N")); settings.add(splineSegSpinner);
        settings.add(new Label("K")); settings.add(pointsSpinner);
        settings.add(new Label("M1")); settings.add(circleSegSpinner);
        settings.add(new Label("M")); settings.add(gensSpinner);
        settings.add(new Label("Current U")); settings.add(currentPointUSpinner);
        settings.add(new Label("Current V")); settings.add(currentPointVSpinner);
        add(settings, BorderLayout.SOUTH);


        Observables observables = new Observables(Map.of(
                "chain",
                chain,
                "current point",
                currentPoint,
                "splineSegments",
                splineSeg
        ));
        BSplineView viewPanel = new BSplineView(observables);
        add(viewPanel, BorderLayout.CENTER);
    }
}
