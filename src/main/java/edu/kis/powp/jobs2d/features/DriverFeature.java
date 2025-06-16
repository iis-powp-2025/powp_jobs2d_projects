package edu.kis.powp.jobs2d.features;

import edu.kis.powp.appbase.Application;
import edu.kis.powp.jobs2d.drivers.DriverManager;
import edu.kis.powp.jobs2d.drivers.SelectDriverMenuOptionListener;
import edu.kis.powp.jobs2d.drivers.VisitableJob2dDriver;
import edu.kis.powp.jobs2d.drivers.monitoring.DriverLoggingMonitor;
import edu.kis.powp.jobs2d.drivers.monitoring.DriverMonitoringObserver;
import edu.kis.powp.jobs2d.drivers.observers.DriverLabelChangeObserver;
import edu.kis.powp.jobs2d.plugin.FeaturePlugin;

public class DriverFeature implements FeaturePlugin {

    private static DriverManager driverManager = new DriverManager();
    private static Application app;

    public static DriverManager getDriverManager() {
        return driverManager;
    }

    /**
     * Setup jobs2d drivers Plugin and add to application.
     * 
     * @param application Application context.
     */
    public static void setupDriverPlugin(Application application) {
        app = application;
        app.addComponentMenu(DriverFeature.class, "Drivers");

        DriverLabelChangeObserver driverLabelChangeObserver = new DriverLabelChangeObserver(application);
        driverManager.getChangePublisher().addSubscriber(driverLabelChangeObserver);

        DriverLoggingMonitor loggingMonitor = new DriverLoggingMonitor();
        DriverMonitoringObserver monitoringObserver = new DriverMonitoringObserver(driverManager,loggingMonitor);
        driverManager.getChangePublisher().addSubscriber(monitoringObserver);
    }

    @Override
    public void setup(Application application) {
        setupDriverPlugin(application);
    }

    /**
     * Add driver to context, create button in driver menu.
     * 
     * @param name   Button name.
     * @param driver Job2dDriver object.
     */
    public static void addDriver(String name, VisitableJob2dDriver driver) {
        SelectDriverMenuOptionListener listener = new SelectDriverMenuOptionListener(driver, driverManager);
        app.addComponentMenuElement(DriverFeature.class, name, listener);
    }
}
