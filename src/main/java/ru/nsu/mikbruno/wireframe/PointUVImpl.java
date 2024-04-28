package ru.nsu.mikbruno.wireframe;

import java.io.Serializable;

public class PointUVImpl implements PointUV {
    private double u, v;

    public PointUVImpl(double u, double v) {
        setUV(u, v);
    }

    public double getU() {
        return u;
    }

    public double getV() {
        return v;
    }

    public void setUV(double u, double v) {
        this.u = u;
        this.v = v;
    }
}
