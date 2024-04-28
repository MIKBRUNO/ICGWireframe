package ru.nsu.mikbruno.interaction.observer;

import java.util.ArrayList;
import java.util.List;

public class ObservableImpl implements Observable{
    private final List<Observer> observers = new ArrayList<>(1);

    @Override
    public void addObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void removeObservers() {
        observers.clear();
    }

    @Override
    public void notifyObservers(Object o) {
        for (Observer ob : observers) {
            ob.update(o);
        }
    }
}
