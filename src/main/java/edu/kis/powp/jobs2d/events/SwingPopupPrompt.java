package edu.kis.powp.jobs2d.events;

import javax.swing.*;

public class SwingPopupPrompt implements UserPrompt {

    @Override
    public boolean confirm(String message, String title) {
        int option = JOptionPane.showOptionDialog(
                null,
                message,
                title,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE,
                null,
                new String[]{"Refill", "No"},
                "Refill"
        );
        return option == JOptionPane.YES_OPTION;
    }
}