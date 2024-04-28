package ru.nsu.mikbruno.wireframe;

import ru.nsu.mikbruno.wireframe.chains.Chain;
import ru.nsu.mikbruno.wireframe.homogenous.Point3D;

import java.util.List;

public interface Scene {
    List<Chain<Point3D>> getChains();

    default void addChain(Chain<Point3D> chain) {
        getChains().add(chain);
    }

    default void addScene(Scene scene) {
        getChains().addAll(scene.getChains());
    }
}
