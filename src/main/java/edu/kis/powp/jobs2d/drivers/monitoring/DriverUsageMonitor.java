package edu.kis.powp.jobs2d.drivers.monitoring;

import java.util.ArrayList;
import java.util.List;

public class DriverUsageMonitor {
    private int headDistance = 0;
    private int opDistance = 0;
    private int currentX = 0;
    private int currentY = 0;

    private boolean movementAllowed = true;

    public void setMovementAllowed(boolean allowed) {
        this.movementAllowed = allowed;
    }

    public boolean isMovementAllowed() {
        return movementAllowed;
    }

    public void resetMovementAllowed() {
        this.movementAllowed = true;
    }
    private final List<DriverUsageObserver> observers = new ArrayList<>();

    public void addObserver(DriverUsageObserver observer) {
        observers.add(observer);
    }

    private void notifyObservers() {
        for (DriverUsageObserver observer : observers) {
            observer.onUsageUpdated(this);
        }
    }

    public void recordHeadMove(int x, int y) {
        headDistance += calculateDistance(currentX, currentY, x, y);
        updatePosition(x, y);
        notifyObservers();
    }

    public void recordOperationMove(int x, int y) {
        opDistance += calculateDistance(currentX, currentY, x, y);
        headDistance += calculateDistance(currentX, currentY, x, y);
        updatePosition(x, y);
        notifyObservers();
    }

    private void updatePosition(int x, int y) {
        currentX = x;
        currentY = y;
    }

    private int calculateDistance(int x1, int y1, int x2, int y2) {
        return (int) Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    public int getHeadDistance() {
        return headDistance;
    }

    public int getOperationDistance() {
        return opDistance;
    }

    public void resetHeadDistance() {
        headDistance = 0;
    }

    public void resetOpDistance() {
        opDistance = 0;
    }

    public int[] getPosition(){
        return new int[]{currentX, currentY};
    }

}
