package ru.nsu.mikbruno.wireframe.chains;

import org.jetbrains.annotations.UnmodifiableView;
import ru.nsu.mikbruno.util.Pair;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

public interface Chain<PType> {
    Iterator<PType> getPointsIterator();
//    @UnmodifiableView
    List<PType> getPoints();
    void setPoints(List<PType> points);
    Iterator<Pair<PType>> getEdgesIterator();
    void addPoint(PType p);
    PType removePoint();
    default void removePoint(PType p) {
        getPoints().remove(p);
    }
    boolean isClosed();
    void setClosure(boolean closure);
    Chain<PType> copy();
}
