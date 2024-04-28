package ru.nsu.mikbruno.interaction.observer;

public class IntObservable extends ObservableImpl implements Observable {
    private int value;

    public IntObservable(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
        this.notifyObservers(value);
    }
}
