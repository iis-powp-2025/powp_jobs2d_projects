package edu.kis.powp.jobs2d.command;

import edu.kis.powp.jobs2d.command.entries.CommandEntry;

import java.util.ArrayList;
import java.util.List;

public class CommandParser {

    public static List<DriverCommand> parseEntryListToDriverCommand(List<CommandEntry> loadedEntries) {
        List<DriverCommand> loadedCommands = new ArrayList<>();

        try {
            for (CommandEntry commandEntry : loadedEntries) {

                String commandName = commandEntry.getCommandName();
                int x = commandEntry.getX();
                int y = commandEntry.getY();

                DriverCommand newCommand;

                switch(commandName) {
                    case "operateTo":
                        newCommand = new OperateToCommand(x, y);
                        break;
                    case "setPosition":
                        newCommand = new SetPositionCommand(x, y);
                        break;
                    default:
                        newCommand = null;
                }

                if(newCommand == null){
                    continue;
                }

                loadedCommands.add(newCommand);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return loadedCommands;
    }
}
