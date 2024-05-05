package ru.nsu.mikbruno.gui.wireframe;

import ru.nsu.mikbruno.interaction.InteractionGroup;
import ru.nsu.mikbruno.interaction.observer.DoubleObservable;
import ru.nsu.mikbruno.interaction.observer.IntObservable;
import ru.nsu.mikbruno.interaction.observer.Observables;
import ru.nsu.mikbruno.interaction.observer.ValueObservable;
import ru.nsu.mikbruno.wireframe.SceneProduction;
import ru.nsu.mikbruno.wireframe.SceneTransformation;
import ru.nsu.mikbruno.wireframe.chains.ArrayListChainObservable;
import ru.nsu.mikbruno.wireframe.chains.PointUV;
import ru.nsu.mikbruno.wireframe.chains.PointUVImpl;
import ru.nsu.mikbruno.wireframe.homogenous.Operator;
import ru.nsu.mikbruno.wireframe.homogenous.ScaleOperator;
import ru.nsu.mikbruno.wireframe.scenes.ArrayListScene;
import ru.nsu.mikbruno.wireframe.scenes.Scene;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class MainWindow extends JFrame {
    private static final String NAME = "ICGWireframe";
    private static final Dimension MINIMUM_SIZE = new Dimension(640, 480);
    private static final Dimension PREFERRED_SIZE = new Dimension(720, 480);
    private static final boolean RESIZABLE = true;

    public MainWindow() throws IOException {
        super(NAME);
        setPreferredSize(PREFERRED_SIZE);
        setMinimumSize(MINIMUM_SIZE);
        setResizable(RESIZABLE);
        setLocationByPlatform(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        Observables observables = new Observables(Map.of(
                "chain",
                new ArrayListChainObservable<PointUV>(List.of(
                        new PointUVImpl(-1, 0),
                        new PointUVImpl(-1, 1),
                        new PointUVImpl(1, 1),
                        new PointUVImpl(1, 0)
                )),
                "spline_segments",
                new IntObservable(4),
                "generatricies",
                new IntObservable(10),
                "circle_segments",
                new IntObservable(5),
                "scene",
                new ValueObservable<Scene>(new ArrayListScene()),
                "pitch",
                new DoubleObservable(0),
                "yaw",
                new DoubleObservable(0),
                "operator",
                new ValueObservable<Operator>(new ScaleOperator(1, 1, 1)),
                "zoom",
                new DoubleObservable(1)
        ));
        new SceneProduction(observables);
        new SceneTransformation(observables);


        List<InteractionGroup> groups = List.of(
                new InteractionGroup("File"),
                new InteractionGroup("Editor")
                        .addInteraction(new EditorInteraction(observables))
                        .addInteraction(new ResetInteraction(observables)),
                new InteractionGroup("Help")
        );

        setJMenuBar(new MainMenuBar(groups));
        add(new MainToolBar(groups), BorderLayout.NORTH);

        SceneView view = new SceneView(observables);
        add(view, BorderLayout.CENTER);

        pack();
        setVisible(true);
    }
}
