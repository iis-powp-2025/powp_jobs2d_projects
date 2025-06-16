package edu.kis.powp.jobs2d.command.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;

import edu.kis.legacy.drawer.panel.DrawPanelController;
import edu.kis.legacy.drawer.shape.LineFactory;
import edu.kis.powp.jobs2d.command.ComplexCommandEditor;
import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.command.ICompoundCommand;
import edu.kis.powp.jobs2d.command.ImmutableComplexCommand;
import edu.kis.powp.jobs2d.command.OperateToCommand;
import edu.kis.powp.jobs2d.command.SetPositionCommand;
import edu.kis.powp.jobs2d.command.manager.ICommandManager;
import edu.kis.powp.jobs2d.drivers.VisitableJob2dDriver;
import edu.kis.powp.jobs2d.drivers.adapter.LineDriverAdapter;

public class ComplexCommandEditorUI extends JFrame {

    private ICommandManager manager;
    private ComplexCommandEditor editor;
    private DefaultListModel<DriverCommand> listModel;
    private JList<DriverCommand> commandList;
    private DrawPanelController drawPanelController;
    private VisitableJob2dDriver previewDriver;

    public ComplexCommandEditorUI(ICommandManager manager) {
        this.manager = manager;

        this.editor = (manager.getCurrentCommand() instanceof ICompoundCommand)
                ? new ComplexCommandEditor((ICompoundCommand) this.manager.getCurrentCommand())
                : new ComplexCommandEditor();

        setupUI();
    }

    private void setupUI() {
        setTitle("Complex Command Editor");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        drawPanelController = new DrawPanelController();
        JPanel drawPanel = new JPanel();
        drawPanel.setPreferredSize(new Dimension(600, 300));
        drawPanelController.initialize(drawPanel);

        previewDriver = new LineDriverAdapter(drawPanelController, LineFactory.getBasicLine(), "preview");

        listModel = new DefaultListModel<>();
        updateListModel();

        commandList = new JList<>(listModel);
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setLeftComponent(new JScrollPane(commandList));
        splitPane.setRightComponent(drawPanel);
        splitPane.setResizeWeight(0.3);
        add(splitPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 6));
        buttonPanel.add(createAddButton());
        buttonPanel.add(createEditButton());
        buttonPanel.add(createRemoveButton());
        buttonPanel.add(createMoveUpButton());
        buttonPanel.add(createMoveDownButton());
        buttonPanel.add(createApplyButton());

        add(buttonPanel, BorderLayout.SOUTH);
        previewCommands();
    }

    private JButton createAddButton() {
        return new JButton(new AbstractAction("Add Command") {
            public void actionPerformed(ActionEvent e) {
                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

                JComboBox<String> commandType = new JComboBox<>(new String[] { "SetPosition", "OperateTo" });
                JTextField inputX = new JTextField(5);
                JTextField inputY = new JTextField(5);

                panel.add(new JLabel("Command Type:"));
                panel.add(commandType);
                panel.add(new JLabel("X:"));
                panel.add(inputX);
                panel.add(new JLabel("Y:"));
                panel.add(inputY);

                int result = JOptionPane.showConfirmDialog(null, panel, "Add Command", JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE);

                if (result == JOptionPane.OK_OPTION) {
                    try {
                        int x = Integer.parseInt(inputX.getText());
                        int y = Integer.parseInt(inputY.getText());
                        DriverCommand command = "SetPosition".equals(commandType.getSelectedItem())
                                ? new SetPositionCommand(x, y)
                                : new OperateToCommand(x, y);

                        editor.addCommand(command);
                        updateListModel();
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Invalid input: X and Y must be integers.");
                    }
                }
                previewCommands();
            }
        });
    }

    private JButton createEditButton() {
        return new JButton(new AbstractAction("Edit") {
            public void actionPerformed(ActionEvent e) {
                int index = commandList.getSelectedIndex();
                if (index == -1)
                    return;

                DriverCommand selected = listModel.get(index);
                if (!(selected instanceof SetPositionCommand || selected instanceof OperateToCommand)) {
                    JOptionPane.showMessageDialog(null, "Only SetPosition and OperateTo commands can be edited.");
                    return;
                }

                int currentX = 0, currentY = 0;
                if (selected instanceof SetPositionCommand) {
                    SetPositionCommand spc = (SetPositionCommand) selected;
                    currentX = spc.getX();
                    currentY = spc.getY();
                } else if (selected instanceof OperateToCommand) {
                    OperateToCommand otc = (OperateToCommand) selected;
                    currentX = otc.getX();
                    currentY = otc.getY();
                }

                JTextField inputX = new JTextField(String.valueOf(currentX));
                JTextField inputY = new JTextField(String.valueOf(currentY));
                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                panel.add(new JLabel("X:"));
                panel.add(inputX);
                panel.add(new JLabel("Y:"));
                panel.add(inputY);

                int result = JOptionPane.showConfirmDialog(null, panel, "Edit Command", JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE);

                if (result == JOptionPane.OK_OPTION) {
                    try {
                        int newX = Integer.parseInt(inputX.getText());
                        int newY = Integer.parseInt(inputY.getText());

                        DriverCommand newCommand = (selected instanceof SetPositionCommand)
                                ? new SetPositionCommand(newX, newY)
                                : new OperateToCommand(newX, newY);

                        editor.replaceCommand(index, newCommand);
                        updateListModel();
                        commandList.setSelectedIndex(index);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Invalid input: X and Y must be integers.");
                    }
                }
                previewCommands();
            }
        });
    }

    private JButton createRemoveButton() {
        return new JButton(new AbstractAction("Remove") {
            public void actionPerformed(ActionEvent e) {
                int index = commandList.getSelectedIndex();
                if (index != -1) {
                    editor.removeCommand(index);
                    updateListModel();
                }
                previewCommands();
            }
        });
    }

    private JButton createMoveUpButton() {
        return new JButton(new AbstractAction("Up") {
            public void actionPerformed(ActionEvent e) {
                int index = commandList.getSelectedIndex();
                if (index > 0) {
                    editor.moveCommand(index, index - 1);
                    updateListModel();
                    commandList.setSelectedIndex(index - 1);
                }
                previewCommands();
            }
        });
    }

    private JButton createMoveDownButton() {
        return new JButton(new AbstractAction("Down") {
            public void actionPerformed(ActionEvent e) {
                int index = commandList.getSelectedIndex();
                if (index < listModel.size() - 1) {
                    editor.moveCommand(index, index + 1);
                    updateListModel();
                    commandList.setSelectedIndex(index + 1);
                }
                previewCommands();
            }
        });
    }

    private void updateListModel() {
        listModel.clear();
        for (DriverCommand cmd : editor.getCommands()) {
            listModel.addElement(cmd);
        }
    }

    private JButton createApplyButton() {
        return new JButton(new AbstractAction("Apply") {
            public void actionPerformed(ActionEvent e) {
                manager.setCurrentCommand(editor.getCommands(), "Edited Command");
                JOptionPane.showMessageDialog(null, "Command updated in manager.");
            }
        });
    }

    private void previewCommands() {
        drawPanelController.clearPanel();
        DriverCommand currentCommand = new ImmutableComplexCommand(editor.getCommands());
        if (currentCommand != null) {
            clearPanel();
            currentCommand.execute(previewDriver);
        }
    }

    private void clearPanel() {
        if (drawPanelController != null) {
            drawPanelController.clearPanel();
        }
    }

}
