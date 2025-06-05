package edu.kis.powp.jobs2d;

import java.util.logging.Logger;

import edu.kis.powp.jobs2d.drivers.VisitableJob2dDriver;
import edu.kis.powp.jobs2d.drivers.visitors.SimpleDriverCountingVisitor;
import edu.kis.powp.jobs2d.features.DriverFeature;

public class CountDriversTest {

    private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public static void execute() {
        VisitableJob2dDriver driver = DriverFeature.getDriverManager().getCurrentDriver();
        if (driver != null) {
            SimpleDriverCountingVisitor visitor = new SimpleDriverCountingVisitor();
            driver.accept(visitor);
            int count = visitor.getTotal();
            visitor.reset();
            logger.info("Total driver: " + count);
        } else {
            logger.warning("No driver laoded");
        }
    }

}
