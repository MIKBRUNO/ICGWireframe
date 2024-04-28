package ru.nsu.mikbruno.util;

import java.util.ArrayList;

public class ArrayListPair<T> implements Pair<T> {
    protected final ArrayList<T> pair = new ArrayList<>(2);

    public ArrayListPair(T first, T second) {
        pair.add(first);
        pair.add(second);
    }

    public void setFirst(T e) {
        pair.set(0, e);
    }

    public void setSecond(T e) {
        pair.set(1, e);
    }

    @Override
    public T first() {
        return pair.get(0);
    }

    @Override
    public T second() {
        return pair.get(1);
    }
}
