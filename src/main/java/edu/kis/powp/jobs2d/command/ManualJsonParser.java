package edu.kis.powp.jobs2d.command;

import edu.kis.powp.jobs2d.command.entries.CommandEntry;

import java.util.ArrayList;
import java.util.List;

public class ManualJsonParser {
    public static List<CommandEntry> parseCommands(String json) {
        List<CommandEntry> commandList = new ArrayList<>();

        String cleaned = json.replace("[", "")
                .replace("]", "")
                .trim();

        String[] entries = cleaned.split("},\\s*\\{");

        for (String entry : entries) {
            entry = entry.replace("{", "").replace("}", "").trim();

            String[] fields = entry.split(",");
            CommandEntry cmd = getCommandEntry(fields);

            commandList.add(cmd);
        }

        return commandList;
    }

    private static CommandEntry getCommandEntry(String[] fields) {
        CommandEntry cmd = new CommandEntry();

        for (String field : fields) {
            String[] keyValue = field.split(":");

            String key = keyValue[0].trim().replace("\"", "");
            String value = keyValue[1].trim().replace("\"", "");

            switch (key) {
                case "commandName":
                    cmd.setCommandName(value);
                    break;
                case "x":
                    cmd.setX(Integer.parseInt(value));
                    break;
                case "y":
                    cmd.setY(Integer.parseInt(value));
                    break;
            }
        }
        return cmd;
    }
}
