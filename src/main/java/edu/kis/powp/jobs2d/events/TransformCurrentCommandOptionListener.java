package edu.kis.powp.jobs2d.events;

import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.command.manager.DriverCommandManager;
import edu.kis.powp.jobs2d.command.visitor.DriverCommandTransformVisitor;
import edu.kis.powp.jobs2d.features.CommandsFeature;
import edu.kis.powp.jobs2d.transformations.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class TransformCurrentCommandOptionListener implements ActionListener {
    private final List<PointTransformation> transformations;

    public TransformCurrentCommandOptionListener(List<PointTransformation> transformations) {
        this.transformations = transformations;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        DriverCommand currentCommand = CommandsFeature.getDriverCommandManager().getCurrentCommand();
        if (currentCommand == null) {
            System.out.println("No command loaded.");
            return;
        }

        DriverCommandTransformVisitor visitor = new DriverCommandTransformVisitor();
        transformations.forEach(visitor::addTransformation);
        currentCommand.accept(visitor);
        CommandsFeature.getDriverCommandManager().setCurrentCommand(visitor.getTransformedCommands(), "Transformed Command");
        System.out.println("Command transformed and updated.");
    }
}
