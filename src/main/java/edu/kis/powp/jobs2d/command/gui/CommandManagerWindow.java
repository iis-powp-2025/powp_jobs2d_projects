package edu.kis.powp.jobs2d.command.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.*;

import edu.kis.legacy.drawer.panel.DrawPanelController;
import edu.kis.legacy.drawer.shape.LineFactory;
import edu.kis.powp.appbase.gui.WindowComponent;
import edu.kis.powp.jobs2d.canva.shapes.CanvaShape;
import edu.kis.powp.jobs2d.command.manager.CommandParsingStrategiesManager;
import edu.kis.powp.jobs2d.command.CommandParser;
import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.command.ICompoundCommand;
import edu.kis.powp.jobs2d.command.entries.CommandEntry;
import edu.kis.powp.jobs2d.command.manager.CommandHistoryManager;
import edu.kis.powp.jobs2d.command.manager.ICommandManager;
import edu.kis.powp.jobs2d.command.strategy.ParsingStrategy;
import edu.kis.powp.jobs2d.drivers.VisitableJob2dDriver;
import edu.kis.powp.jobs2d.drivers.adapter.LineDriverAdapter;
import edu.kis.powp.jobs2d.features.WorkspaceFeature;
import edu.kis.powp.jobs2d.transformations.PointTransformation;
import edu.kis.powp.jobs2d.transformations.ScaleTransformation;
import edu.kis.powp.jobs2d.transformations.TransformationDriverDecorator;
import edu.kis.powp.observer.Subscriber;

public class CommandManagerWindow extends JFrame implements WindowComponent {

    private final ICommandManager commandManager;
    private VisitableJob2dDriver previewDriver;

    private final VisitableJob2dDriver workspaceDriver;

    private final JTextArea currentCommandField;

    private final JTextArea observerListField;

    private final JTextArea commandsInputTextField;
    private final List<Subscriber> deletedSubscriberList = new ArrayList<>();

    private final DrawPanelController drawPanelController;

    private final CommandHistoryManager commandHistoryManager;
    private final JList<CommandHistoryManager.HistoryEntry> commandHistoryList;
    private final DefaultListModel<CommandHistoryManager.HistoryEntry> historyListModel;
    private boolean isCanvasDisplayed = false;

    private final CommandParsingStrategiesManager parsingContext;

    private final JComboBox<ParsingStrategy> strategyComboBox;
    private final JLabel currentStrategyLabel;

    /**
     * 
     */
    private static final long serialVersionUID = 9204679248304669948L;

    public CommandManagerWindow(ICommandManager commandManager, CommandHistoryManager commandHistoryManager, CommandParsingStrategiesManager parsingContext) {
        this.setTitle("Command Manager");
        this.setSize(600, 400);
        Container content = this.getContentPane();
        content.setLayout(new GridBagLayout());

        this.parsingContext = parsingContext;
        this.commandManager = commandManager;
        this.commandHistoryManager = commandHistoryManager;

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridBagLayout());

        GridBagConstraints leftConstraints = new GridBagConstraints();
        leftConstraints.fill = GridBagConstraints.BOTH;
        leftConstraints.weightx = 1;
        leftConstraints.gridx = 0;

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

        JLabel historyLabel = new JLabel("Commands History:");
        leftConstraints.gridy = 2;
        leftConstraints.weighty = 0;
        leftPanel.add(historyLabel, leftConstraints);

        historyListModel = new DefaultListModel<>();
        commandHistoryList = new JList<>(historyListModel);
        commandHistoryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        commandHistoryList.setVisibleRowCount(10);

        JScrollPane scrollPane = new JScrollPane(commandHistoryList);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(200, 300));

        leftConstraints.gridy = 3;
        leftConstraints.weighty = 0.3;
        leftPanel.add(scrollPane, leftConstraints);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());

        GridBagConstraints buttonConstraints = new GridBagConstraints();
        buttonConstraints.fill = GridBagConstraints.HORIZONTAL;
        buttonConstraints.weightx = 1;
        buttonConstraints.gridx = 0;

        JButton btnRunCommand = new JButton("Run command");
        btnRunCommand.addActionListener((ActionEvent e) -> this.runCommand());
        buttonConstraints.gridy = 0;
        buttonPanel.add(btnRunCommand, buttonConstraints);

        JButton btnClearCommand = new JButton("Clear command");
        btnClearCommand.addActionListener((ActionEvent e) -> this.clearCommand());
        buttonConstraints.gridy = 1;
        buttonPanel.add(btnClearCommand, buttonConstraints);

        JButton btnClearOrResetObservers = new JButton("Delete observers");
        btnClearOrResetObservers.addActionListener((ActionEvent e) -> {
            this.deleteObservers();
            this.toggleButtons(btnClearOrResetObservers);
        });
        buttonConstraints.gridy = 2;
        buttonPanel.add(btnClearOrResetObservers, buttonConstraints);

        JButton btnClearPanel = new JButton("Clear Panel");
        btnClearPanel.addActionListener((ActionEvent e) -> this.clearPanel());
        buttonConstraints.gridy = 3;
        buttonPanel.add(btnClearPanel, buttonConstraints);

        JButton btnRefreshHistory = new JButton("Refresh Commands History");
        btnRefreshHistory.addActionListener((ActionEvent e) -> this.updateCommandHistoryField());
        buttonConstraints.gridy = 3;
        buttonPanel.add(btnRefreshHistory, buttonConstraints);

        JButton btnRestoreCommand = new JButton("Restore Selected Command");
        btnRestoreCommand.addActionListener((ActionEvent e) -> this.restoreSelectedCommand());
        buttonConstraints.gridy = 4;
        buttonPanel.add(btnRestoreCommand, buttonConstraints);

        JButton btnDisplayCanva = new JButton("Display Workspace Canva (off)");
        btnDisplayCanva.addActionListener((ActionEvent e) -> this.changeCanvasVisibility());
        buttonConstraints.gridy = 5;
        buttonPanel.add(btnDisplayCanva, buttonConstraints);

        JButton btnEditCommand = new JButton("Edit Current Command");
        btnEditCommand.addActionListener((ActionEvent e) -> this.editCurrentCommand());
        buttonConstraints.gridy = 6;
        buttonPanel.add(btnEditCommand, buttonConstraints);

        JLabel strategyLabel = new JLabel("Parsing Strategy:");
        buttonConstraints.gridy = 7;
        buttonPanel.add(strategyLabel, buttonConstraints);

        strategyComboBox = new JComboBox<>();
        strategyComboBox.addItem(null); // Auto-detect option
        for (ParsingStrategy strategy : parsingContext.getAvailableStrategies()) {
            strategyComboBox.addItem(strategy);
        }
        strategyComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                                                          int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value == null) {
                    setText("Auto-detect");
                } else {
                    setText(((ParsingStrategy) value).getFormatName());
                }
                return this;
            }
        });
        strategyComboBox.addActionListener(e -> {
            ParsingStrategy selected = (ParsingStrategy) strategyComboBox.getSelectedItem();
            parsingContext.setStrategy(selected);
            updateCurrentStrategyLabel();
        });
        buttonConstraints.gridy = 8;
        buttonPanel.add(strategyComboBox, buttonConstraints);

        currentStrategyLabel = new JLabel("Current: Auto-detect");
        buttonConstraints.gridy = 9;
        buttonPanel.add(currentStrategyLabel, buttonConstraints);

        JLabel commandsInputTextLabel = new JLabel("Commands Input:");
        buttonConstraints.gridy = 10;
        buttonPanel.add(commandsInputTextLabel, buttonConstraints);

        commandsInputTextField = new JTextArea("");
        buttonConstraints.gridy = 11;
        buttonPanel.add(commandsInputTextField, buttonConstraints);

        JButton loadCommandsFromInputText = new JButton("Load Commands From Input");
        loadCommandsFromInputText.addActionListener((ActionEvent e) -> this.loadCommandsFromInput());
        buttonConstraints.gridy = 12;
        buttonPanel.add(loadCommandsFromInputText, buttonConstraints);

        leftConstraints.gridy = 4;
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

        PointTransformation transformation = new ScaleTransformation(.5, .5);
        previewDriver = new LineDriverAdapter(drawPanelController, LineFactory.getBasicLine(), "preview");
        previewDriver = new TransformationDriverDecorator(previewDriver, transformation);

        workspaceDriver = new LineDriverAdapter(drawPanelController, LineFactory.getDottedLine(), "workspacePreview");

        c.fill = GridBagConstraints.BOTH;
        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 0.8;
        content.add(drawPanel, c);
    }

    private void runCommand() {
        if (commandManager.getCurrentCommand() == null)
            return;
        commandManager.runCommand();
    }

    private void toggleButtons(JButton button) {
        clearButtonListeners(button);
        if (Objects.equals(button.getText(), "Delete observers")) {
            button.setText("Reset observers");
            button.addActionListener((ActionEvent e) -> {
                this.resetObservers();
                this.toggleButtons(button);
            });
        } else {
            button.setText("Delete observers");
            button.addActionListener((ActionEvent e) -> {
                this.deleteObservers();
                this.toggleButtons(button);
            });
        }
    }

    private void resetObservers() {
        for (Subscriber subscriber : deletedSubscriberList) {
            commandManager.getChangePublisher().addSubscriber(subscriber);
        }
        updateObserverListField();
    }

    private void changeCanvasVisibility() {
        isCanvasDisplayed = !isCanvasDisplayed;
        if (isCanvasDisplayed) {
            displayCanvas();
        }
    }

    private void displayCanvas() {
        CanvaShape currentCanvaShape = WorkspaceFeature.getWorkspaceManager().getCurrentCanvaShape();
        if (currentCanvaShape == null)
            return;
        currentCanvaShape.draw(workspaceDriver);
    }

    private void clearCommand() {
        commandManager.clearCurrentCommand();
        updateCurrentCommandField();
    }

    public void updateCurrentCommandField() {
        currentCommandField.setText(commandManager.getCurrentCommandString());
    }

    public void deleteObservers() {
        deletedSubscriberList.clear();
        deletedSubscriberList.addAll(commandManager.getChangePublisher().getSubscribers());
        commandManager.getChangePublisher().clearObservers();
        this.updateObserverListField();
    }

    private void clearPanel() {
        if (drawPanelController != null) {
            drawPanelController.clearPanel();
        }
    }

    public void previewCommand() {
        DriverCommand currentCommand = commandManager.getCurrentCommand();

        if (currentCommand != null) {
            clearPanel();
            if (isCanvasDisplayed) {
                displayCanvas();
            }
            currentCommand.execute(previewDriver);
        }
    }

    private void clearButtonListeners(JButton button) {
        for (ActionListener al : button.getActionListeners()) {
            button.removeActionListener(al);
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

    public void updateCommandHistoryField() {
        List<CommandHistoryManager.HistoryEntry> history = commandHistoryManager.getHistory();
        historyListModel.clear();
        for (CommandHistoryManager.HistoryEntry entry : history) {
            historyListModel.addElement(entry);
        }
    }

    private void restoreSelectedCommand() {
        CommandHistoryManager.HistoryEntry selectedEntry = commandHistoryList.getSelectedValue();
        if (selectedEntry != null) {
            commandManager.setCurrentCommand(selectedEntry.getCommand());
            updateCurrentCommandField();
        }
    }

    @Override
    public void HideIfVisibleAndShowIfHidden() {
        updateObserverListField();
        this.setVisible(!this.isVisible());
    }

    private void editCurrentCommand() {
        DriverCommand current = commandManager.getCurrentCommand();

        if (!(current instanceof ICompoundCommand)) {
            JOptionPane.showMessageDialog(this, "Current command is not a complex (compound) command.",
                    "Edit Not Allowed", JOptionPane.WARNING_MESSAGE);
            return;
        }

        ComplexCommandEditorUI editorUI = new ComplexCommandEditorUI(commandManager);
        editorUI.setLocationRelativeTo(this);
        editorUI.setVisible(true);
    }

    private void loadCommandsFromInput() {
        String commandsText = commandsInputTextField.getText();

        try {
            List<CommandEntry> commands = parsingContext.parseCommands(commandsText);
            List<DriverCommand> driverCommands = CommandParser.parseEntryListToDriverCommand(commands);

            commandManager.setCurrentCommand(
                    driverCommands,
                    commandsText
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateCurrentStrategyLabel() {
        currentStrategyLabel.setText("Current: " + parsingContext.getCurrentStrategyName());
    }

    public void addParsingStrategy(ParsingStrategy strategy) {
        parsingContext.registerStrategy(strategy);
        strategyComboBox.addItem(strategy);
    }

}
