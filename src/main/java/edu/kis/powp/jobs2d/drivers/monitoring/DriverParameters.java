package edu.kis.powp.jobs2d.drivers.monitoring;

public class DriverParameters {
    private int maxHeadDistance;
    private int maxOperationDistance;

    public DriverParameters(int maxHeadDistance, int maxOperationDistance) {
        this.maxHeadDistance = maxHeadDistance;
        this.maxOperationDistance = maxOperationDistance;
    }

    public int getMaxHeadDistance() {
        return maxHeadDistance;
    }

    public int getMaxOperationDistance() {
        return maxOperationDistance;
    }
    
}
