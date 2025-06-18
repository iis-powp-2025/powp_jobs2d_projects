package edu.kis.powp.jobs2d.command;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CommandLoader {


    public static List<DriverCommand> loadCommandsFromJson(String filename) {
        List<DriverCommand> loadedCommands = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(filename);
        try {
            List<JsonCommandEntry> commands = mapper.readValue(file, new TypeReference<List<JsonCommandEntry>>() {});

            for (JsonCommandEntry command : commands) {

                String commandName = command.getCommandName();
                int x = command.getX();
                int y = command.getY();

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
