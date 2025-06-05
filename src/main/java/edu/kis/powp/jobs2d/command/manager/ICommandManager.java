package edu.kis.powp.jobs2d.command.manager;

import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.observer.Publisher;

import java.util.List;

public interface ICommandManager {
    void setCurrentCommand(DriverCommand commandList);

    void runCommand();

    void setCurrentCommand(List<DriverCommand> commandList, String name);

    DriverCommand getCurrentCommand();

    void clearCurrentCommand();

    String getCurrentCommandString();

    Publisher getChangePublisher();
}
