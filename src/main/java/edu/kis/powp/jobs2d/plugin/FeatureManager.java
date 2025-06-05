package edu.kis.powp.jobs2d.plugin;

import edu.kis.powp.appbase.Application;
import java.util.ArrayList;
import java.util.List;

public class FeatureManager {
    private static final List<FeaturePlugin> plugins = new ArrayList<>();

    public static void registerFeature(FeaturePlugin plugin) {
        plugins.add(plugin);
    }

    public static void initializeAll(Application app) {
        for (FeaturePlugin plugin : plugins) {
            plugin.setup(app);
        }
    }
}
