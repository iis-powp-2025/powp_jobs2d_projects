package edu.kis.powp.jobs2d.command;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.command.visitor.ComplexCommandDeepCopyVisitor;

/**
 * Implementation of ICompoundCommand for running multiple commands.
 */
public class ComplexCommand implements ICompoundCommand {

    private final List<DriverCommand> commandList;

    private ComplexCommand(List<DriverCommand> commandList) {
        this.commandList = commandList;
    }

    public ComplexCommand() {
        this.commandList = new ArrayList<>();
    }

    public void addCommand(DriverCommand command) {
        this.commandList.add(command);
    }

    public void addCommand(int index, DriverCommand command) {
        this.commandList.add(index, command);
    }

    public void removeCommand(int index) {
        this.commandList.remove(index);
    }

    public void removeLastCommand() {
        this.commandList.remove(this.commandList.size() - 1);
    }

    public void setCommand(int index, DriverCommand command) {
        this.commandList.set(index, command);
    }

    public void clear() {
        this.commandList.clear();
    }

    public boolean isEmpty() {
        return this.commandList.isEmpty();
    }

    public int size() {
        return this.commandList.size();
    }

    @Override
    public void execute(Job2dDriver driver) {
        for (DriverCommand command : commandList) {
            command.execute(driver);
        }
    }

    @Override
    public Iterator<DriverCommand> iterator() {
        return commandList.iterator();
    }

    @Override
    public DriverCommand copy() {
        ComplexCommandDeepCopyVisitor visitor = new ComplexCommandDeepCopyVisitor();
        this.accept(visitor);
        return visitor.getCopy();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;

        ComplexCommand second = (ComplexCommand) obj;

        return commandList.equals(second.commandList);
    }

    public static class Builder {
        private final List<DriverCommand> commands;

        public Builder() {
            this.commands = new ArrayList<>();
        }

        public Builder addCommand(DriverCommand command) {
            commands.add(command);
            return this;
        }

        public Builder addCommands(List<DriverCommand> commands) {
            this.commands.addAll(commands);
            return this;
        }

        public ComplexCommand build() {
            return new ComplexCommand(commands);
        }
    }
}
