package edu.kis.powp.jobs2d;

import java.util.logging.Logger;

import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.command.SimpleCountingVisitor;
import edu.kis.powp.jobs2d.features.CommandsFeature;

public class CountCommandsTest {

    private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public static void execute() {
        DriverCommand command = CommandsFeature.getDriverCommandManager().getCurrentCommand();
        if (command != null) {
            SimpleCountingVisitor visitor = new SimpleCountingVisitor();
            int count = command.accept(visitor);
            logger.info("Total commands (flattened): " + count);
        } else {
            logger.warning("No command loaded to count.");
        }
    }
}
