package ru.nsu.mikbruno.gui.wireframe;

import ru.nsu.mikbruno.interaction.InteractionGroup;
import ru.nsu.mikbruno.interaction.observer.IntObservable;
import ru.nsu.mikbruno.interaction.observer.Observables;
import ru.nsu.mikbruno.wireframe.chains.ArrayListChainObservable;
import ru.nsu.mikbruno.wireframe.chains.PointUV;
import ru.nsu.mikbruno.wireframe.chains.PointUVImpl;

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
                        new PointUVImpl(0, 0),
                        new PointUVImpl(1, 1),
                        new PointUVImpl(1, 2),
                        new PointUVImpl(0, 2)
                )),
                "spline",
                new ArrayListChainObservable<PointUV>(),
                "spline_segments",
                new IntObservable(1),
                "generatricies",
                new IntObservable(2),
                "circle_segments",
                new IntObservable(1)
        ));

        List<InteractionGroup> groups = List.of(
                new InteractionGroup("File"),
                new InteractionGroup("Editor")
                        .addInteraction(new EditorInteraction(observables)),
                new InteractionGroup("Help")
        );

        setJMenuBar(new MainMenuBar(groups));
        add(new MainToolBar(groups), BorderLayout.NORTH);
        pack();
        setVisible(true);
    }
}
