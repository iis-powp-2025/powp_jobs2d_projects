package edu.kis.powp.jobs2d.events;

import edu.kis.powp.jobs2d.drivers.monitoring.DriverEventManager;
import edu.kis.powp.jobs2d.drivers.monitoring.DriverUsageMonitor;

public class EventPopupHandler {

    private final DriverUsageMonitor usageMonitor;
    private final UserPrompt prompt;

    public EventPopupHandler(DriverUsageMonitor usageMonitor, UserPrompt prompt) {
        this.usageMonitor = usageMonitor;
        this.prompt = prompt;
    }

    public void registerAll(DriverEventManager manager) {
        manager.registerListener(DriverEventManager.DriverEventType.OP_DISTANCE_EXCEEDED,
                new RefillPromptAction("Maximum operational distance exceeded.",
                        "Driver Event – Operational",
                        usageMonitor::resetOpDistance,
                        prompt));

        manager.registerListener(DriverEventManager.DriverEventType.HEAD_DISTANCE_EXCEEDED,
                new RefillPromptAction("Maximum head shift distance exceeded.",
                        "Driver Event – Head",
                        usageMonitor::resetHeadDistance,
                        prompt));
    }
}

