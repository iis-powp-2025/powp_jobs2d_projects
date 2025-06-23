package edu.kis.powp.jobs2d.drivers.monitoring;

public class DriverLimitValidator implements DriverUsageObserver{
    private final DriverParameters parameters;
    private final DriverEventManager eventManager;

    public DriverLimitValidator(DriverParameters parameters, DriverEventManager eventManager) {
        this.parameters = parameters;
        this.eventManager = eventManager;
    }

    public boolean validateHeadDistance(DriverUsageMonitor monitor) {
        if (parameters == null || eventManager == null) {
            return true;
        }

        if (monitor.getHeadDistance() > parameters.getMaxHeadDistance()) {
            eventManager.triggerEvent(DriverEventManager.DriverEventType.HEAD_DISTANCE_EXCEEDED);
            monitor.setMovementAllowed(false);
            return false;
        }
        monitor.setMovementAllowed(true);
        return true;
    }

    public boolean validateOperationDistance(DriverUsageMonitor monitor) {
        if (parameters == null || eventManager == null) {
            return true;
        }

        if (monitor.getOperationDistance() > parameters.getMaxOperationDistance()) {
            eventManager.triggerEvent(DriverEventManager.DriverEventType.OP_DISTANCE_EXCEEDED);
            monitor.setMovementAllowed(false);
            return false;
        }
        monitor.setMovementAllowed(true);
        return true;
    }

    @Override
    public void onUsageUpdated(DriverUsageMonitor monitor) {
        validateHeadDistance(monitor);
        validateOperationDistance(monitor);
    }
}
