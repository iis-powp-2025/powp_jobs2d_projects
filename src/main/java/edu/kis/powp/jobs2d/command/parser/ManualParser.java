package edu.kis.powp.jobs2d.command.parser;

import edu.kis.powp.jobs2d.command.entries.CommandEntry;

import java.util.List;

public interface ManualParser {

    List<CommandEntry> parseCommands(String text);

    CommandEntry getCommandEntry(String[] fields);

}
