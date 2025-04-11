package edu.kis.powp.jobs2d.command;

import edu.kis.powp.jobs2d.Job2dDriver;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Implementation of ICompoundCommand for running multiple commands.
 */
public class ComplexCommand implements ICompoundCommand {

    private final List<DriverCommand> commandList = new ArrayList<>();

    public void addCommand(DriverCommand command) {
        this.commandList.add(command);
    }

    @Override
    public void execute(Job2dDriver driver){
        for (DriverCommand command : commandList) {
            command.execute(driver);
        }
    }

    @Override
    public Iterator<DriverCommand> iterator() {
        return commandList.iterator();
    }

    @Override
    public int accept(DriverCommandVisitor visitor) {
        return visitor.visit(this);
    }
}