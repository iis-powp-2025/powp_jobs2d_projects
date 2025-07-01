package edu.kis.powp.jobs2d.command.strategy;

import edu.kis.powp.jobs2d.command.entries.CommandEntry;

import java.util.ArrayList;
import java.util.List;

public class CsvParsingStrategy implements ParsingStrategy {

    @Override
    public List<CommandEntry> parseCommands(String csv) {
        List<CommandEntry> commandList = new ArrayList<>();
        String[] lines = csv.split("\n");

        // Skip header if present
        int startIndex = (lines.length > 0 && lines[0].toLowerCase().contains("command")) ? 1 : 0;

        for (int i = startIndex; i < lines.length; i++) {
            String line = lines[i].trim();
            if (line.isEmpty()) continue;

            String[] parts = line.split(",");
            if (parts.length >= 3) {
                CommandEntry cmd = new CommandEntry();
                cmd.setCommandName(parts[0].trim());
                try {
                    cmd.setX(Integer.parseInt(parts[1].trim()));
                    cmd.setY(Integer.parseInt(parts[2].trim()));
                    commandList.add(cmd);
                } catch (NumberFormatException e) {
                    // Skip invalid lines
                }
            }
        }
        return commandList;
    }

    @Override
    public String getFormatName() {
        return "CSV";
    }

    @Override
    public boolean canParse(String input) {
        return input.trim().contains(",") &&
                input.split("\n").length > 0;
    }
}