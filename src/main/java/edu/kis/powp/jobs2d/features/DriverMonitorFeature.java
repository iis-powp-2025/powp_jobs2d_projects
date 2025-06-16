package edu.kis.powp.jobs2d.features;

import edu.kis.powp.appbase.Application;
import edu.kis.powp.jobs2d.drivers.monitoring.DriverMonitorDecorator;
import edu.kis.powp.jobs2d.plugin.FeaturePlugin;

public class DriverMonitorFeature implements FeaturePlugin {
    private static Application app;
    

    public static void setupDriverMonitorPlugin(Application application) {
        app = application;
        app.addComponentMenu(DriverMonitorFeature.class, "Driver Monitor");
        app.addComponentMenuElement(DriverMonitorFeature.class, "Enable Driver Monitor", (e) -> enableMonitor(true));
        app.addComponentMenuElement(DriverMonitorFeature.class, "Disable Driver Monitor", (e) -> enableMonitor(false));
    }

    @Override
    public void setup(Application application) {
        setupDriverMonitorPlugin(application);
    }


    private static void enableMonitor(boolean enable) {
       DriverMonitorDecorator.setMonitorEnabled(enable);
    }
}
