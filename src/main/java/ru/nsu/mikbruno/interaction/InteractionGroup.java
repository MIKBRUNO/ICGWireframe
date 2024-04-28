package ru.nsu.mikbruno.interaction;

import java.util.ArrayList;
import java.util.List;

public class InteractionGroup {
    private final String name;
    private final List<Interaction> interactions;

    public InteractionGroup(String name) {
        this.name = name;
        interactions = new ArrayList<>(1);
    }

    public InteractionGroup(String name, List<Interaction> interactions) {
        this.name = name;
        this.interactions = interactions;
    }

    public String getName() {
        return name;
    }

    public List<Interaction> getInteractions() {
        return interactions;
    }

    public InteractionGroup addInteraction(Interaction interaction) {
        interactions.add(interaction);

        return this;
    }
}
