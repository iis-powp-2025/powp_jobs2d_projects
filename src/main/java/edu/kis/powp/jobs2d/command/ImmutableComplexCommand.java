package edu.kis.powp.jobs2d.command;

import edu.kis.powp.jobs2d.Job2dDriver;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

public class ImmutableComplexCommand implements ICompoundCommand {

    private final List<DriverCommand> commands;

    public ImmutableComplexCommand(List<DriverCommand> commands) {
        List<DriverCommand> copiedCommands = new ArrayList<>();
        for (DriverCommand command : commands) {
            copiedCommands.add(command.copy());
        }
        this.commands = Collections.unmodifiableList(copiedCommands);
    }


    @Override
    public void execute(Job2dDriver driver) {
        for (DriverCommand command : commands) {
            command.execute(driver);
        }
    }

    @Override
    public Iterator<DriverCommand> iterator() {
        List<DriverCommand> copiedList = new ArrayList<>();
        for (DriverCommand command : commands) {
            copiedList.add(command.copy());
        }
        return copiedList.iterator();
    }


    @Override
    public DriverCommand copy() {
        List<DriverCommand> copiedCommands = new ArrayList<>();
        for (DriverCommand command : commands) {
            copiedCommands.add(command.copy());
        }
        return new ImmutableComplexCommand(copiedCommands);
    }
}
