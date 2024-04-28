package ru.nsu.mikbruno.wireframe.chains;

import org.jetbrains.annotations.UnmodifiableView;
import ru.nsu.mikbruno.interaction.observer.Observable;
import ru.nsu.mikbruno.interaction.observer.ObservableImpl;
import ru.nsu.mikbruno.interaction.observer.Observer;
import ru.nsu.mikbruno.util.Pair;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class ArrayListChainObservable<T> implements Chain<T>, Observable {
    private final ArrayListChain<T> chain;

    public ArrayListChainObservable() {
        chain = new ArrayListChain<>();
    }

    public ArrayListChainObservable(Collection<T> chain) {
        this.chain = new ArrayListChain<>(chain);
    }

    @Override
    public Iterator<T> getPointsIterator() {
        return chain.getPointsIterator();
    }

    @Override
    @UnmodifiableView
    public List<T> getPoints() {
        return chain.getPoints();
    }

    @Override
    public void setPoints(List<T> points) {
        chain.setPoints(points);
        observable.notifyObservers(null);
    }

    @Override
    public Iterator<Pair<T>> getEdgesIterator() {
        return chain.getEdgesIterator();
    }

    @Override
    public void addPoint(T p) {
        chain.addPoint(p);
        observable.notifyObservers(null);
    }

    @Override
    public T removePoint() {
        T res = chain.removePoint();
        observable.notifyObservers(null);
        return res;
    }

    @Override
    public boolean isClosed() {
        return chain.isClosed();
    }

    @Override
    public void setClosure(boolean closure) {
        chain.setClosure(closure);
    }

    private final ObservableImpl observable = new ObservableImpl();

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
}
