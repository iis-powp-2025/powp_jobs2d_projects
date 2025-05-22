package edu.kis.powp.jobs2d.drivers;

import java.util.logging.Logger;

import edu.kis.powp.jobs2d.drivers.visitors.DriverVisitor;

public class InformativeLoggerDriver implements VisitableJob2dDriver {

    Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    @Override
    public void setPosition(int x, int y) {
        logger.info("Setting position to (" + x + ", " + y + ")");
    }

    @Override
    public void operateTo(int x, int y) {
        logger.info("Operating to position (" + x + ", " + y + ")");
    }

    @Override
    public void accept(DriverVisitor visitor) {
        visitor.visit(this);
    }
}
