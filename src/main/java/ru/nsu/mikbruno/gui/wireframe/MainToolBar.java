package ru.nsu.mikbruno.gui.wireframe;

import ru.nsu.mikbruno.interaction.Interaction;
import ru.nsu.mikbruno.interaction.InteractionGroup;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MainToolBar extends JToolBar {
    public MainToolBar(List<InteractionGroup> groups) {
        super();
        for (InteractionGroup group : groups) {
            for (Interaction interaction : group.getInteractions()) {
                JButton button = new JButton();
                if (interaction.actionImage() != null) {
                    button.setIcon(new ImageIcon(interaction.actionImage()
                            .getScaledInstance(-1, 16, Image.SCALE_FAST)
                    ));
                }
                else {
                    button.setText(interaction.name());
                }
                button.setToolTipText(interaction.description());
                button.addActionListener(e -> interaction.apply(this));
                this.add(button);
            }
            this.add(new Separator());
        }
    }
}
