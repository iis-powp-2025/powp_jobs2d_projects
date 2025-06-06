package edu.kis.powp.jobs2d.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ComplexCommandEditor {
    private final List<DriverCommand> commands;

    public ComplexCommandEditor(ICompoundCommand compoundCommand) {
        this.commands = new ArrayList<>();
        for (DriverCommand cmd : compoundCommand) {
            this.commands.add(cmd.copy());
        }
    }

    public ComplexCommandEditor() {
        this.commands = new ArrayList<>();
    }


    public void addCommand(DriverCommand command) {
        commands.add(command.copy());
    }


    public void removeCommand(int index) {
        validateIndex(index);
        commands.remove(index);
    }


    public void moveCommand(int fromIndex, int toIndex) {
        validateIndex(fromIndex);
        if (toIndex < 0 || toIndex >= commands.size()) {
            throw new IndexOutOfBoundsException("Invalid target index: " + toIndex);
        }
        DriverCommand command = commands.remove(fromIndex);
        commands.add(toIndex, command);
    }


    public List<DriverCommand> getCommands() {
        return Collections.unmodifiableList(commands);
    }


    private void validateIndex(int index) {
        if (index < 0 || index >= commands.size()) {
            throw new IndexOutOfBoundsException("Invalid command index: " + index);
        }
    }

    public void replaceCommand(int index, DriverCommand newCommand) {
        commands.set(index, newCommand);
    }

}

