package ru.nsu.mikbruno.interaction.observer;

public class ValueObservable<T> extends ObservableImpl implements Observable {
    private T value;

    public ValueObservable(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
        this.notifyObservers(value);
    }
}
