package edu.kis.powp.jobs2d.command.strategy;

import edu.kis.powp.jobs2d.command.entries.CommandEntry;

import java.util.ArrayList;
import java.util.List;

public class JsonParsingStrategy implements ParsingStrategy {

    @Override
    public List<CommandEntry> parseCommands(String json) {
        List<CommandEntry> commandList = new ArrayList<>();
        String cleaned = json.replace("[", "")
                .replace("]", "")
                .trim();
        String[] entries = cleaned.split("},\\s*\\{");

        for (String entry : entries) {
            entry = entry.replace("{", "").replace("}", "").trim();
            String[] fields = entry.split(",");
            CommandEntry cmd = createCommandEntry(fields);
            commandList.add(cmd);
        }
        return commandList;
    }

    @Override
    public String getFormatName() {
        return "JSON";
    }

    @Override
    public boolean canParse(String input) {
        String trimmed = input.trim();
        return trimmed.startsWith("[") && trimmed.endsWith("]") &&
                (trimmed.contains("{") && trimmed.contains("}"));
    }

    private CommandEntry createCommandEntry(String[] fields) {
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
