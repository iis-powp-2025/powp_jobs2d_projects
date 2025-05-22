package edu.kis.powp.jobs2d.events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.command.ImmutableComplexCommand;
import edu.kis.powp.jobs2d.command.manager.DriverCommandManager;
import edu.kis.powp.jobs2d.features.CommandsFeature;

public class SelectLoadNotSoSecretCommandOptionListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        ImmutableComplexCommand complexCommand = edu.kis.powp.jobs2d.events.StarCommandFactory.createStarCommand();

        DriverCommandManager manager = CommandsFeature.getDriverCommandManager();
        List<DriverCommand> copiedCommands = new ArrayList<>();
        complexCommand.iterator().forEachRemaining(copiedCommands::add);
        manager.setCurrentCommand(copiedCommands, "StarCommand");
    }
}