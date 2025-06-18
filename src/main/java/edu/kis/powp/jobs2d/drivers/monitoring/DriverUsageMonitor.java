package edu.kis.powp.jobs2d.drivers.monitoring;

public class DriverUsageMonitor {
    private int headDistance = 0;
    private int opDistance = 0;
    private int currentX = 0;
    private int currentY = 0;

    public void recordHeadMove(int x, int y) {
        headDistance += calculateDistance(currentX, currentY, x, y);
        updatePosition(x, y);
    }

    public void recordOperationMove(int x, int y) {
        opDistance += calculateDistance(currentX, currentY, x, y);
        headDistance += calculateDistance(currentX, currentY, x, y);
        updatePosition(x, y);
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

    public void setOperationDistnce(int opDistance) {
        opDistance = opDistance;
    }
}
