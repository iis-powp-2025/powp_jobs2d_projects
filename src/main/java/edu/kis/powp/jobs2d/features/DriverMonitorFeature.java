package edu.kis.powp.jobs2d.features;

import edu.kis.powp.appbase.Application;
import edu.kis.powp.jobs2d.drivers.monitoring.DriverMonitorDecorator;
import edu.kis.powp.jobs2d.plugin.FeaturePlugin;

public class DriverMonitorFeature implements FeaturePlugin {
    private static Application app;

    public static void setupDriverMonitorPlugin(Application application) {




        app = application;
        app.addComponentMenu(DriverMonitorFeature.class, "Driver Monitor");
        app.addComponentMenuElementWithCheckBox(DriverMonitorFeature.class, "Driver Monitor", (e) -> toggleDriverMonitor(), true);    
    }

    public static void toggleDriverMonitor() {
        boolean currentState = DriverMonitorDecorator.isMonitorEnabled();
        setMonitorState(!currentState);
        app.updateInfo(String.format("Driver Monitor is now %s", currentState ? "disabled" : "enabled"));
    }
    @Override
    public void setup(Application application) {
        setupDriverMonitorPlugin(application);
    }


    private static void setMonitorState(boolean state) {
        DriverMonitorDecorator.setMonitorEnabled(state);
    }
}
