package ru.nsu.mikbruno.wireframe.homogenous;

public interface Point3D {
    double getX();

    double getY();

    double getZ();

    double[][] get();

    void set(double x, double y, double z);

    void set(double x, double y, double z, double w);

    void set(double[][] vec);
}
