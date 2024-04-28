package ru.nsu.mikbruno.interaction;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.image.BufferedImage;

public interface InteractionUI {
    Icon actionImage();

    @NotNull
    String name();

    @NotNull
    String description();
}
