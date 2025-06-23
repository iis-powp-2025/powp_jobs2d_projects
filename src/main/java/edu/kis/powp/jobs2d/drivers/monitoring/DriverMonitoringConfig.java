package edu.kis.powp.jobs2d.drivers.monitoring;

public class DriverMonitoringConfig {
    private final DriverParameters driverParameters;
    private final DriverEventManager eventManager;
    private final DriverMonitor outputMonitor;
    private boolean monitorEnabled = true;

    private DriverMonitoringConfig(Builder builder) {
        this.driverParameters = builder.driverParameters;
        this.eventManager = builder.eventManager;
        this.outputMonitor = builder.outputMonitor;
        this.monitorEnabled = builder.monitorEnabled;
    }

    public static class Builder {
        private DriverParameters driverParameters;
        private DriverEventManager eventManager;
        private DriverMonitor outputMonitor;
        private boolean monitorEnabled = true;

        public Builder withDriverParameters(DriverParameters driverParameters) {
            this.driverParameters = driverParameters;
            return this;
        }

        public Builder withEventManager(DriverEventManager eventManager) {
            this.eventManager = eventManager;
            return this;
        }

        public Builder withOutputMonitor(DriverMonitor outputMonitor) {
            this.outputMonitor = outputMonitor;
            return this;
        }

        public Builder withMonitorEnabled(boolean enabled) {
            this.monitorEnabled = enabled;
            return this;
        }

        public DriverMonitoringConfig build() {
            return new DriverMonitoringConfig(this);
        }
    }

    // Getters
    public DriverParameters getDriverParameters() { return driverParameters; }
    public DriverEventManager getEventManager() { return eventManager; }
    public DriverMonitor getOutputMonitor() { return outputMonitor; }
    public boolean isMonitorEnabled() { return monitorEnabled; }
}
