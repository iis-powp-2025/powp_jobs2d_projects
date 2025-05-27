package edu.kis.powp.jobs2d.events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.kis.powp.jobs2d.command.*;
import edu.kis.powp.jobs2d.command.manager.DriverCommandManager;
import edu.kis.powp.jobs2d.features.CommandsFeature;

public class SelectLoadEditedCommandOptionListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        ComplexCommandEditor editor = new ComplexCommandEditor();
        editor.addCommand(new SetPositionCommand(-20, -50));
        editor.addCommand(new OperateToCommand(-20, -50));
        editor.addCommand(new SetPositionCommand(-20, -40));
        editor.addCommand(new OperateToCommand(-20, 50));
        editor.addCommand(new SetPositionCommand(0, -50));
        editor.addCommand(new OperateToCommand(0, -50));
        editor.addCommand(new SetPositionCommand(0, -40));
        editor.addCommand(new OperateToCommand(0, 50));
        editor.addCommand(new SetPositionCommand(70, -50));
        editor.addCommand(new OperateToCommand(20, -50));
        editor.addCommand(new OperateToCommand(20, 0));
        editor.addCommand(new OperateToCommand(70, 0));
        editor.addCommand(new OperateToCommand(70, 50));
        editor.addCommand(new OperateToCommand(20, 50));

        editor.addCommand(new OperateToCommand(100, 100));
        editor.insertCommand(5, new SetPositionCommand(10, 10));
        editor.updateCommand(2, new SetPositionCommand(-30, -30));
        editor.removeCommand(3);
        editor.moveCommand(6, 10);

        DriverCommandManager manager = CommandsFeature.getDriverCommandManager();
        manager.setCurrentCommand(editor.getCommands(), "EditedTopSecretCommand");
    }
}
