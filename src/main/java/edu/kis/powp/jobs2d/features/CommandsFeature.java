package edu.kis.powp.jobs2d.features;

import edu.kis.powp.appbase.Application;
import edu.kis.powp.jobs2d.command.manager.DriverCommandManager;
import edu.kis.powp.jobs2d.command.manager.LoggerCommandChangeObserver;
import edu.kis.powp.jobs2d.plugin.FeaturePlugin;

public class CommandsFeature implements FeaturePlugin {

    private static DriverCommandManager commandManager;

    public static void setupCommandManager() {
        commandManager = new DriverCommandManager();

        LoggerCommandChangeObserver loggerObserver = new LoggerCommandChangeObserver();
        commandManager.getChangePublisher().addSubscriber(loggerObserver);
    }

    @Override
    public void setup(Application application) {
        setupCommandManager();
    }

    /**
     * Get manager of application driver command.
     * 
     * @return plotterCommandManager.
     */
    public static DriverCommandManager getDriverCommandManager() {
        return commandManager;
    }
}
