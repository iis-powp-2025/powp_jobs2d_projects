package edu.kis.powp.jobs2d.command.gui;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

import edu.kis.powp.appbase.gui.WindowComponent;
import edu.kis.powp.jobs2d.command.manager.DriverCommandManager;
import edu.kis.powp.jobs2d.features.CommandsFeature;
import edu.kis.powp.jobs2d.features.DriverFeature;
import edu.kis.powp.observer.Subscriber;

public class CommandManagerWindow extends JFrame implements WindowComponent {

    private final DriverCommandManager commandManager;

    private final JTextArea currentCommandField;

    private final JTextArea observerListField;

    private List<Subscriber> subscriberList = new ArrayList<>();

    /**
     *
     */
    private static final long serialVersionUID = 9204679248304669948L;

    public CommandManagerWindow(DriverCommandManager commandManager) {
        this.setTitle("Command Manager");
        this.setSize(400, 400);
        Container content = this.getContentPane();
        content.setLayout(new GridBagLayout());

        this.commandManager = commandManager;
        GridBagConstraints c = new GridBagConstraints();

        observerListField = new JTextArea("");
        observerListField.setEditable(false);
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.gridx = 0;
        c.weighty = 1;
        content.add(observerListField, c);
        updateObserverListField();

        currentCommandField = new JTextArea("");
        currentCommandField.setEditable(false);
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.gridx = 0;
        c.weighty = 1;
        content.add(currentCommandField, c);
        updateCurrentCommandField();

        JButton btnRunCommand = new JButton("Run Command");
        btnRunCommand.addActionListener((ActionEvent e) -> this.runCommand());
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.gridx = 0;
        c.weighty = 1;
        content.add(btnRunCommand, c);

        JButton btnClearCommand = new JButton("Clear command");
        btnClearCommand.addActionListener((ActionEvent e) -> this.clearCommand());
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.gridx = 0;
        c.weighty = 1;
        content.add(btnClearCommand, c);

        JButton btnClearOrResetObservers = new JButton("Delete observers");
        btnClearOrResetObservers.addActionListener((ActionEvent e) -> {
            this.deleteObservers();
            this.toggleButtons(btnClearOrResetObservers);
        });
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.gridx = 0;
        c.weighty = 1;
        content.add(btnClearOrResetObservers, c);
    }

    private void toggleButtons(JButton button) {
        if(Objects.equals(button.getText(), "Delete observers")) {
            button.setText("Reset observers");
            for(ActionListener al : button.getActionListeners()){
                button.removeActionListener(al);
            }
            button.addActionListener((ActionEvent e) -> {
                this.resetObservers();
                this.toggleButtons(button);
            });
        }
        else{
            button.setText("Delete observers");
            for(ActionListener al : button.getActionListeners()){
                button.removeActionListener(al);
            }
            button.addActionListener((ActionEvent e) -> {
                this.deleteObservers();
                this.toggleButtons(button);
            });
        }
    }


    private void clearCommand() {
        commandManager.clearCurrentCommand();
        updateCurrentCommandField();
    }

    private void runCommand(){
        commandManager.getCurrentCommand().execute(DriverFeature.getDriverManager().getCurrentDriver());
    }

    private void resetObservers(){
        for(Subscriber subscriber : subscriberList) {
            commandManager.getChangePublisher().addSubscriber(subscriber);
        }
        updateObserverListField();
    }

    public void updateCurrentCommandField() {
        currentCommandField.setText(commandManager.getCurrentCommandString());
    }

    public void deleteObservers() {
        subscriberList.clear();
        subscriberList.addAll(commandManager.getChangePublisher().getSubscribers());
        commandManager.getChangePublisher().clearObservers();
        this.updateObserverListField();
    }

    private void updateObserverListField() {
        StringBuilder observerListString = new StringBuilder();
        List<Subscriber> commandChangeSubscribers = commandManager.getChangePublisher().getSubscribers();
        for (Subscriber observer : commandChangeSubscribers) {
            observerListString.append(observer.toString()).append(System.lineSeparator());
        }
        if (commandChangeSubscribers.isEmpty())
            observerListString = new StringBuilder("No observers loaded");

        observerListField.setText(observerListString.toString());
    }

    @Override
    public void HideIfVisibleAndShowIfHidden() {
        updateObserverListField();
        this.setVisible(!this.isVisible());
    }

}
