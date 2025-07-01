package edu.kis.powp.jobs2d.command.strategy;

import edu.kis.powp.jobs2d.command.entries.CommandEntry;

import java.util.List;

public interface ParsingStrategy {

    List<CommandEntry> parseCommands(String input);

    String getFormatName();

    boolean canParse(String input);

}