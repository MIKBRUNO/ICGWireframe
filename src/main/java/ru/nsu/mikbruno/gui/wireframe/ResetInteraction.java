package ru.nsu.mikbruno.gui.wireframe;

import org.jetbrains.annotations.NotNull;
import ru.nsu.mikbruno.gui.bspline.BSplineEditor;
import ru.nsu.mikbruno.interaction.Interaction;
import ru.nsu.mikbruno.interaction.observer.DoubleObservable;
import ru.nsu.mikbruno.interaction.observer.Observables;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ResetInteraction implements Interaction {
    private final DoubleObservable pitch;
    private final DoubleObservable yaw;
    private final Icon icon;

    public ResetInteraction(Observables observables) throws IOException {
        pitch = observables.getValue("pitch");
        yaw = observables.getValue("yaw");
        icon = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/reset.png"))
                .getScaledInstance(16,16, Image.SCALE_SMOOTH));
    }

    @Override
    public void apply(JComponent action) {
        yaw.setValue(0);
        pitch.setValue(0);
    }

    @Override
    public Icon actionImage() {
        return icon;
    }

    @Override
    public @NotNull String name() {
        return "Reset";
    }

    @Override
    public @NotNull String description() {
        return "Reset rotation angles";
    }
}
