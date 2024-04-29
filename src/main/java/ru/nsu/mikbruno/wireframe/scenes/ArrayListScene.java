package ru.nsu.mikbruno.wireframe.scenes;

import ru.nsu.mikbruno.wireframe.chains.Chain;
import ru.nsu.mikbruno.wireframe.homogenous.Point3D;

import java.util.ArrayList;
import java.util.List;

public class ArrayListScene implements Scene {
    private final List<Chain<Point3D>> chains;

    public ArrayListScene(List<Chain<Point3D>> chains) {
        this.chains = chains;
    }

    public ArrayListScene() {
        this(new ArrayList<>());
    }

    @Override
    public List<Chain<Point3D>> getChains() {
        return chains;
    }
}
