package ru.nsu.mikbruno.gui.bspline;

import ru.nsu.mikbruno.interaction.observer.Observable;
import ru.nsu.mikbruno.interaction.observer.ObservableImpl;
import ru.nsu.mikbruno.interaction.observer.Observer;
import ru.nsu.mikbruno.wireframe.chains.PointUV;

public class PointUVObservable implements Observable, PointUV {
    private PointUV point = null;

    public PointUVObservable(PointUV point) {
        this.point = point;
    }

    public PointUV getPoint() {
        return point;
    }

    public void setPoint(PointUV point) {
        this.point = point;
        observable.notifyObservers(point);
    }

    private ObservableImpl observable = new ObservableImpl();

    @Override
    public void addObserver(Observer o) {
        observable.addObserver(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observable.removeObserver(o);
    }

    @Override
    public void removeObservers() {
        observable.removeObservers();
    }

    @Override
    public void notifyObservers(Object o) {
        observable.notifyObservers(o);
    }

    @Override
    public double getU() {
        if (point == null) return 0;
        return point.getU();
    }

    @Override
    public double getV() {
        if (point == null) return 0;
        return point.getV();
    }

    @Override
    public void setUV(double u, double v) {
        if (point == null) return;
        point.setUV(u, v);
        observable.notifyObservers(null);
    }
}
