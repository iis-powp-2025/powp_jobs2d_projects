package edu.kis.powp.jobs2d.plugin;

import edu.kis.powp.appbase.Application;

public interface FeaturePlugin {
    void setup(Application application);

    default String[] getDependencies() {
        return new String[0];
    }

    default String getFeatureName() {
        return this.getClass().getSimpleName();
    }
}
