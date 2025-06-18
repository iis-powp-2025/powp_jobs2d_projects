package edu.kis.powp.jobs2d.drivers.monitoring;

import java.util.*;

public class DriverEventManager {

    public enum DriverEventType {
        HEAD_DISTANCE_EXCEEDED,
        OP_DISTANCE_EXCEEDED
    }

    private static DriverEventManager instance;

    private final Map<DriverEventType, List<Runnable>> listeners = new EnumMap<>(DriverEventType.class);

    private DriverEventManager() {
        for (DriverEventType type : DriverEventType.values()) {
            listeners.put(type, new ArrayList<>());
        }
    }

    public static DriverEventManager getInstance() {
        if (instance == null) {
            instance = new DriverEventManager();
        }
        return instance;
    }

    public void registerListener(DriverEventType type, Runnable listener) {
        listeners.get(type).add(listener);
    }

    public void triggerEvent(DriverEventType type) {
        for (Runnable listener : listeners.get(type)) {
            listener.run();
        }
    }
}
