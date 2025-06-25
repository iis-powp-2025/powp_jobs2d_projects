package edu.kis.powp.jobs2d.command.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.kis.powp.jobs2d.command.entries.CommandEntry;

import java.util.List;

public interface CommandEntryMapper {
    ObjectMapper mapper = new ObjectMapper();

    List<CommandEntry> mapToCommandEntries(String filename);

}
