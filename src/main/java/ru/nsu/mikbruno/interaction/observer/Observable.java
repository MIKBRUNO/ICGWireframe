package ru.nsu.mikbruno.interaction.observer;

public interface Observable {
    void addObserver(Observer o);
    void removeObserver(Observer o);
    void removeObservers();
    void notifyObservers(Object o);
}
