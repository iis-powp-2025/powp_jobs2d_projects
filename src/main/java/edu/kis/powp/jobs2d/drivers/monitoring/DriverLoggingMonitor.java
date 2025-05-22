package edu.kis.powp.jobs2d.drivers.monitoring;

import java.util.logging.Logger;

public class DriverLoggingMonitor implements DriverMonitor {
    Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    @Override
    public void update(int x, int y, int headDistance, int opDistance) {
        logger.info("Position = (" + x + ", " + y + ")\n   Head distance = " + headDistance + "\n   Op distance = " + opDistance);
    }
}
