package edu.kis.powp.jobs2d.command.manager;

import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.observer.Publisher;

import java.util.List;

public interface ICommandManager extends Cloneable {
    void setCurrentCommand(DriverCommand commandList);

    void setCurrentCommand(List<DriverCommand> commandList, String name);

    DriverCommand getCurrentCommand();

    void clearCurrentCommand();

    String getCurrentCommandString();

    Publisher getChangePublisher();

    void runCommand();

    Object clone() throws CloneNotSupportedException;
}
