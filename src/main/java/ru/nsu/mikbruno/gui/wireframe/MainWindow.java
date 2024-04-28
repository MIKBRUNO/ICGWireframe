package ru.nsu.mikbruno.gui.wireframe;

import ru.nsu.mikbruno.interaction.InteractionGroup;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MainWindow extends JFrame {
    private static final String NAME = "ICGWireframe";
    private static final Dimension MINIMUM_SIZE = new Dimension(640, 480);
    private static final Dimension PREFERRED_SIZE = new Dimension(720, 480);
    private static final boolean RESIZABLE = true;

    public MainWindow() {
        super(NAME);
        setPreferredSize(PREFERRED_SIZE);
        setMinimumSize(MINIMUM_SIZE);
        setResizable(RESIZABLE);
        setLocationByPlatform(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);



        List<InteractionGroup> groups = List.of(
                new InteractionGroup("File"),
                new InteractionGroup("Editor"),
                new InteractionGroup("Help")
        );

        setJMenuBar(new MainMenuBar(groups));
        add(new MainToolBar(groups), BorderLayout.NORTH);
        pack();
        setVisible(true);
    }
}
