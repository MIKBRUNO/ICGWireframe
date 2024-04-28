package ru.nsu.mikbruno.wireframe;

import org.jetbrains.annotations.UnmodifiableView;
import ru.nsu.mikbruno.interaction.observer.Observable;
import ru.nsu.mikbruno.interaction.observer.ObservableImpl;
import ru.nsu.mikbruno.interaction.observer.Observer;
import ru.nsu.mikbruno.util.ArrayListPair;
import ru.nsu.mikbruno.util.Pair;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class ArrayListChain<T> implements Chain<T>, Observable {
    private final ArrayList<T> chain;

    private boolean closure = false;

    public ArrayListChain() {
        chain = new ArrayList<>();
    }

    public ArrayListChain(Collection<T> chain) {
        this.chain = new ArrayList<>(chain);
    }

    @Override
    public Iterator<T> getPointsIterator() {
        return chain.iterator();
    }

    @Override
    @UnmodifiableView
    public List<T> getPoints() {
        return chain;
    }

    @Override
    public void setPoints(List<T> points) {
        this.chain.clear();
        this.chain.addAll(points);
        observable.notifyObservers(null);
    }

    @Override
    public Iterator<Pair<T>> getEdgesIterator() {
        Iterator<T> it = getPointsIterator();
        ArrayList<T> pairList = new ArrayList<>(2);
        for (int i = 0; i < 2; ++i) {
            if (it.hasNext()) {
                pairList.add(it.next());
            }
        }
        final ArrayListPair<T> pair = (pairList.size() == 2) ?
                new ArrayListPair<>(pairList.get(0), pairList.get(1)) :
                null;

        return new Iterator<>() {
            private final ArrayListPair<T> current = pair;
            private final Iterator<T> iterator = getPointsIterator();

            @Override
            public boolean hasNext() {
                return current != null && iterator.hasNext();
            }

            @Override
            public Pair<T> next() {
                if (!hasNext())
                    return null;
                pair.setFirst(pair.second());
                pair.setSecond(iterator.next());
                return pair;
            }
        };
    }

    @Override
    public void addPoint(T p) {
        chain.add(p);
        observable.notifyObservers(null);
    }

    @Override
    public T removePoint() {
        T res = chain.remove(chain.size() - 1);
        observable.notifyObservers(null);
        return res;
    }

    @Override
    public boolean isClosed() {
        return closure;
    }

    @Override
    public void setClosure(boolean closure) {
        this.closure = closure;
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
