package ru.nsu.mikbruno.interaction.observer;

import java.util.Map;

public class Observables {
    private final Map<String, ? extends Observable> observables;

    public Observables(Map<String, ? extends Observable> observables) {
        this.observables = observables;
    }

    @SuppressWarnings("unchecked")
    public <T extends Observable> T getValue(String key) {
        return (T) observables.get(key);
    }
}
