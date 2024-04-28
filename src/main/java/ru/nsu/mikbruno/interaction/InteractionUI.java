package ru.nsu.mikbruno.interaction;

import org.jetbrains.annotations.NotNull;

import java.awt.image.BufferedImage;

public interface InteractionUI {
    @NotNull
    BufferedImage actionImage();

    @NotNull
    String name();

    @NotNull
    String description();
}