package ru.nsu.mikbruno.gui.wireframe;

import org.jetbrains.annotations.NotNull;
import ru.nsu.mikbruno.gui.bspline.BSplineEditor;
import ru.nsu.mikbruno.interaction.Interaction;
import ru.nsu.mikbruno.interaction.observer.Observables;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class EditorInteraction implements Interaction {
    private final Observables observables;
    private final Icon icon;

    public EditorInteraction(Observables observables) throws IOException {
        this.observables = observables;
        icon = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/gear.png"))
                .getScaledInstance(16,16, Image.SCALE_SMOOTH));
    }

    @Override
    public void apply(JComponent action) {
        new BSplineEditor(observables);
    }

    @Override
    public Icon actionImage() {
        return icon;
    }

    @Override
    public @NotNull String name() {
        return "Settings";
    }

    @Override
    public @NotNull String description() {
        return "BSpline editor window and rotation figure settings";
    }
}
