package ru.nsu.mikbruno.gui.wireframe;

import ru.nsu.mikbruno.interaction.Interaction;
import ru.nsu.mikbruno.interaction.InteractionGroup;

import javax.swing.*;
import java.util.List;

public class MainMenuBar extends JMenuBar {
    public MainMenuBar(List<InteractionGroup> groups) {
        super();
        for (InteractionGroup group : groups) {
            JMenu menu = new JMenu(group.getName());
            for (Interaction interaction : group.getInteractions()) {
                JMenuItem item = new JMenuItem(interaction.name());
                item.setToolTipText(interaction.description());
                item.addActionListener(e -> interaction.apply(this));
                menu.add(item);
            }
            this.add(menu);
        }
    }
}
