package ru.nsu.mikbruno.interaction.observer;

public class DoubleObservable extends ObservableImpl implements Observable {
    private double value;

    public DoubleObservable(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
        this.notifyObservers(value);
    }
}
