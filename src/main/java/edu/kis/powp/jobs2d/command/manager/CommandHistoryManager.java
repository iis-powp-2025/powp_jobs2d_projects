package edu.kis.powp.jobs2d.command.manager;

import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.observer.Subscriber;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CommandHistoryManager implements Subscriber {
    private final DriverCommandManager driverCommandManager;

    public CommandHistoryManager(DriverCommandManager driverCommandManager) {
        this.driverCommandManager = driverCommandManager;
    }

    @Override
    public void update() {
        DriverCommand command = driverCommandManager.getCurrentCommand();
        if (command != null) {
            store(command);
        }
    }

    public static class HistoryEntry {
        private final DriverCommand command;
        private final LocalDateTime timestamp;

        public HistoryEntry(DriverCommand command, LocalDateTime timestamp) {
            this.command = command;
            this.timestamp = timestamp;
        }

        public DriverCommand getCommand() {
            return command;
        }

        @Override
        public String toString() {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return timestamp.format(formatter) + " - " + command.toString();
        }
    }

    private final List<HistoryEntry> history = new ArrayList<>();

    public synchronized void store(DriverCommand command) {
        history.add(new HistoryEntry(command, LocalDateTime.now()));
    }

    public synchronized List<HistoryEntry> getHistory() {
        return history;
    }
}
