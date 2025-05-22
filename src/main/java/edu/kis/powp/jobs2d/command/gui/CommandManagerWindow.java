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
import javax.swing.JPanel;
import javax.swing.JTextArea;

import edu.kis.legacy.drawer.panel.DrawPanelController;
import edu.kis.legacy.drawer.shape.LineFactory;
import edu.kis.powp.appbase.gui.WindowComponent;
import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.command.manager.DriverCommandManager;

import edu.kis.powp.jobs2d.drivers.VisitableJob2dDriver;
import edu.kis.powp.jobs2d.drivers.adapter.LineDriverAdapter;
import edu.kis.powp.jobs2d.transformations.ScaleTransformationDecorator;
import edu.kis.powp.jobs2d.transformations.TransformationDecorator;

import edu.kis.powp.observer.Subscriber;

public class CommandManagerWindow extends JFrame implements WindowComponent {

    private DriverCommandManager commandManager;
    private VisitableJob2dDriver previewDriver;
    private TransformationDecorator transformationDecorator;


    private final JTextArea currentCommandField;

    private final JTextArea observerListField;

    private final List<Subscriber> subscriberList = new ArrayList<>();

    private DrawPanelController drawPanelController;

    /**
     *
     */
    private static final long serialVersionUID = 9204679248304669948L;

    public CommandManagerWindow(DriverCommandManager commandManager) {
        this.setTitle("Command Manager");
        this.setSize(600, 400);
        Container content = this.getContentPane();
        content.setLayout(new GridBagLayout());

        this.commandManager = commandManager;
<<<<<<< master-old

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridBagLayout());

        GridBagConstraints leftConstraints = new GridBagConstraints();
        leftConstraints.fill = GridBagConstraints.BOTH;
        leftConstraints.weightx = 1;
        leftConstraints.gridx = 0;
=======
        GridBagConstraints c = new GridBagConstraints();
>>>>>>> master

        observerListField = new JTextArea("");
        observerListField.setEditable(false);
        leftConstraints.gridy = 0;
        leftConstraints.weighty = 0.3;
        leftPanel.add(observerListField, leftConstraints);
        updateObserverListField();

        currentCommandField = new JTextArea("");
        currentCommandField.setEditable(false);
        leftConstraints.gridy = 1;
        leftConstraints.weighty = 0.3;
        leftPanel.add(currentCommandField, leftConstraints);
        updateCurrentCommandField();

<<<<<<< master-old
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());

        GridBagConstraints buttonConstraints = new GridBagConstraints();
        buttonConstraints.fill = GridBagConstraints.HORIZONTAL;
        buttonConstraints.weightx = 1;
        buttonConstraints.gridx = 0;
=======
        JButton btnRunCommand = new JButton("Run Command");
        btnRunCommand.addActionListener((ActionEvent e) -> this.runCommand());
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.gridx = 0;
        c.weighty = 1;
        content.add(btnRunCommand, c);
>>>>>>> master

        JButton btnClearCommand = new JButton("Clear command");
        btnClearCommand.addActionListener((ActionEvent e) -> this.clearCommand());
        buttonConstraints.gridy = 0;
        buttonPanel.add(btnClearCommand, buttonConstraints);


        JButton btnClearOrResetObservers = new JButton("Delete observers");
        btnClearOrResetObservers.addActionListener((ActionEvent e) -> {
            this.deleteObservers();
            this.toggleButtons(btnClearOrResetObservers);
        });

        JButton btnClearPanel = new JButton("Clear Panel");
        btnClearPanel.addActionListener((ActionEvent e) -> this.clearPanel());
        buttonConstraints.gridy = 2;
        buttonPanel.add(btnClearPanel, buttonConstraints);

        JButton btnPreviewCommand = new JButton("Preview Command");
        btnPreviewCommand.addActionListener((ActionEvent e) -> this.previewCommand());
        buttonConstraints.gridy = 3;
        buttonPanel.add(btnPreviewCommand, buttonConstraints);

        leftConstraints.gridy = 2;
        leftConstraints.weighty = 0.4;
        leftPanel.add(buttonPanel, leftConstraints);

        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.2;
        c.weighty = 1;

        content.add(leftPanel, c);

        JPanel drawPanel = new JPanel();
        drawPanel.setPreferredSize(new java.awt.Dimension(800, 800));
        drawPanel.setMinimumSize(new java.awt.Dimension(800, 800));

        drawPanelController = new DrawPanelController();
        drawPanelController.initialize(drawPanel);

        previewDriver = new LineDriverAdapter(drawPanelController, LineFactory.getBasicLine(), "preview");
        transformationDecorator = new ScaleTransformationDecorator(previewDriver, 3., 3.);

        c.fill = GridBagConstraints.BOTH;
        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 0.8;
        content.add(drawPanel, c);
        //content.add(btnClearOrResetObservers, c);
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
        DriverCommand currentCommand = commandManager.getCurrentCommand();
        if (currentCommand == null) {
            return;
        }
        currentCommand.execute(DriverFeature.getDriverManager().getCurrentDriver());
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

    private void clearPanel() {
        if (drawPanelController != null) {
            drawPanelController.clearPanel();
        }
    }

    private void previewCommand() {
        DriverCommand currentCommand = commandManager.getCurrentCommand();

        if (currentCommand != null) {
            clearPanel();

            currentCommand.execute(previewDriver);
        }
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
