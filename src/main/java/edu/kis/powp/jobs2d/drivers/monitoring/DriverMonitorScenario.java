package edu.kis.powp.jobs2d.drivers.monitoring;


import java.util.ArrayList;
import java.util.List;

public class DriverMonitorScenario {
  private final DriverUsageMonitor monitor;

  private float maxDistance = 0;
  private float maxPaintCapacity = 0;

  public DriverMonitorScenario(DriverUsageMonitor monitor, float maxDistance, float maxPaintCapacity) {
    this.monitor = monitor;
    this.maxDistance = maxDistance;
    this.maxPaintCapacity = maxPaintCapacity;
  }

  public List<EventType> detectEvents() {
    List<EventType> events = new ArrayList<>();

    float headDistance = monitor.getHeadDistance();
    float operationDistance = monitor.getOperationDistance();

    if (maxDistance > 0 && operationDistance > maxDistance) {
      events.add(EventType.SERVICE_REQUIRED);
    }

    if (maxPaintCapacity > 0 && headDistance > maxPaintCapacity) {
      events.add(EventType.REFILL_REQUIRED);
    }

    return events;
  }

  public String getSummaryText() {
    StringBuilder sb = new StringBuilder();
    sb.append("----- DRIVER USAGE SUMMARY -----\n");

    float headDistance = monitor.getHeadDistance();
    float operationDistance = monitor.getOperationDistance();

    sb.append("Total head distance: ").append(headDistance).append("\n");
    sb.append("Total operation distance: ").append(operationDistance).append("\n");

    sb.append("\n--- Thresholds ---\n");
    sb.append("Max allowed distance: ").append(maxDistance).append("\n");
    sb.append("Max paint capacity: ").append(maxPaintCapacity).append("\n");

    List<EventType> detectedEvents = detectEvents();

    sb.append("\n--- Status ---\n");
    if (!detectedEvents.isEmpty()) {
      sb.append("Detected event types:\n");
      for (EventType event : detectedEvents) {
        sb.append("- ").append(event.name()).append("\n");
      }
    } else {
      sb.append("No events detected.\n");
    }

    sb.append("\n--- Warnings ---\n");
    if (!detectedEvents.isEmpty()) {
      for (EventType event : detectedEvents) {
        switch (event) {
          case SERVICE_REQUIRED:
            sb.append("⚠ Operation distance exceeds the max allowed limit!\n");
            break;
          case REFILL_REQUIRED:
            sb.append("⚠ Head usage exceeds paint capacity!\n");
            break;
          default:
            sb.append("⚠ Warning: ").append(event.name()).append("\n");
            break;
        }
      }
    } else {
      sb.append("No warnings.\n");
    }

    sb.append("--------------------------------");
    return sb.toString();
  }
}
