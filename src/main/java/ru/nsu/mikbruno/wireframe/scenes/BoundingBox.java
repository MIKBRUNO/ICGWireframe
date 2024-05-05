package ru.nsu.mikbruno.wireframe.scenes;

import ru.nsu.mikbruno.wireframe.chains.Chain;
import ru.nsu.mikbruno.wireframe.homogenous.Point3D;

public class BoundingBox {
    private double minx, miny, minz;
    private double maxx, maxy, maxz;
    private final double width, height, depth;
    private final double centerX, centerY, centerZ;

    public BoundingBox(Scene scene) {
        Point3D first = scene.getChains().get(0).getPoints().get(0);
        minx = maxx = first.getX();
        miny = maxy = first.getY();
        minz = maxz = first.getZ();
        for (Chain<Point3D> chain : scene.getChains()) {
            for (Point3D point3D : chain.getPoints()) {
                if (point3D.getX() < minx) minx = point3D.getX();
                if (point3D.getX() > maxx) maxx = point3D.getX();
                if (point3D.getY() < miny) miny = point3D.getY();
                if (point3D.getY() > maxy) maxy = point3D.getY();
                if (point3D.getZ() < minz) minz = point3D.getZ();
                if (point3D.getZ() > maxz) maxz = point3D.getZ();
            }
        }
        width = maxx - minx; height = maxy - miny; depth = maxz - minz;
        centerX = minx + width / 2; centerY = miny + height / 2; centerZ = minz + depth / 2;
    }

    public double getWidth() { return width; }
    public double getHeight() { return height; }
    public double getDepth() { return depth; }
    public double getMinX() { return minx; }
    public double getMinY() { return miny; }
    public double getMinZ() { return minz; }
    public double getMaxX() { return maxx; }
    public double getMaxY() { return maxy; }
    public double getMaxZ() { return maxz; }
    public double getCenterX() { return centerX; }
    public double getCenterY() { return centerY; }
    public double getCenterZ() { return centerZ; }
}
