package edu.kis.powp.jobs2d.command.manager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.command.ICompoundCommand;
import edu.kis.powp.jobs2d.features.DriverFeature;
import edu.kis.powp.observer.Publisher;

/**
 * Driver command Manager.
 */
public class DriverCommandManager {
    private DriverCommand currentCommand = null;

    private final Publisher changePublisher = new Publisher();

    private final List<DriverCommand> commandHistory = new ArrayList<>();

    /**
     * Set current command.
     * 
     * @param commandList Set the command as current.
     */
    public synchronized void setCurrentCommand(DriverCommand commandList) {
        this.currentCommand = commandList;
        changePublisher.notifyObservers();
    }

    public synchronized Job2dDriver getDriver() {
        return DriverFeature.getDriverManager().getCurrentDriver();
    }

    /**
     * Set current command.
     * 
     * @param commandList list of commands representing a compound command.
     * @param name        name of the command.
     */
    public synchronized void setCurrentCommand(List<DriverCommand> commandList, String name) {
        setCurrentCommand(new ICompoundCommand() {

            final List<DriverCommand> driverCommands = commandList;

            @Override
            public void execute(Job2dDriver driver) {
                driverCommands.forEach((c) -> c.execute(driver));
            }

            @Override
            public Iterator<DriverCommand> iterator() {
                return driverCommands.iterator();
            }

            @Override
            public String toString() {
                return name;
            }

            @Override
            public DriverCommand copy() {return this;}
        });

    }

    /**
     * Return current command.
     *
     * @return Current command.
     */
    public synchronized DriverCommand getCurrentCommand() {
        return currentCommand;
    }

    public synchronized void clearCurrentCommand() {
        currentCommand = null;
    }

    public synchronized String getCurrentCommandString() {
        if (getCurrentCommand() == null) {
            return "No command loaded";
        } else
            return getCurrentCommand().toString();
    }

    public Publisher getChangePublisher() {
        return changePublisher;
    }

}
