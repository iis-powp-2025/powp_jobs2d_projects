package edu.kis.powp.jobs2d.command.mapper;

import com.fasterxml.jackson.core.type.TypeReference;
import edu.kis.powp.jobs2d.command.entries.CommandEntry;
import edu.kis.powp.jobs2d.command.entries.JsonCommandEntry;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class JsonCommandEntryMapper implements CommandEntryMapper {

    public List<CommandEntry> mapToCommandEntries(String filename) {
        try{
            File file = new File(filename);
            List<JsonCommandEntry> jsonCommands = mapper.readValue(file, new TypeReference<List<JsonCommandEntry>>() {});

            return new ArrayList<>(jsonCommands);
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
