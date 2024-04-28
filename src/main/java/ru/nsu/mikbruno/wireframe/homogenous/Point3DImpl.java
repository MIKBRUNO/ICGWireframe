package ru.nsu.mikbruno.wireframe.homogenous;

import ru.nsu.mikbruno.wireframe.chains.PointUV;

public class Point3DImpl implements Point3D {
    private double x, y, z;

    public Point3DImpl(double x, double y, double z) {
        set(x, y, z);
    }

    public Point3DImpl(double x, double y, double z, double w) {
        set(x, y, z, w);
    }

    public Point3DImpl(double[][] xyzw) {
        set(xyzw);
    }

    // yes this way
    public Point3DImpl(PointUV pointUV) {
        set(pointUV.getV(), pointUV.getU(), 0);
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public double getZ() {
        return z;
    }

    @Override
    public double[][] get() {
        return new double[][] {{x, y, z, 1}};
    }

    @Override
    public void set(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public void set(double x, double y, double z, double w) {
        this.x = x/w;
        this.y = y/w;
        this.z = z/w;
    }

    @Override
    public void set(double[][] vec) {
        double w = vec[0][3];
        this.x = vec[0][0] / w;
        this.y = vec[0][1] / w;
        this.z = vec[0][2] / w;
    }
}
