package edu.kis.powp.jobs2d.drivers.monitoring;

import edu.kis.powp.jobs2d.drivers.AbstractDecorator;
import edu.kis.powp.jobs2d.drivers.VisitableJob2dDriver;
import edu.kis.powp.jobs2d.drivers.monitoring.DriverEventManager.DriverEventType;

public class DriverMonitorDecorator extends AbstractDecorator {
    private final DriverUsageMonitor monitor;
    private final DriverMonitor outputMonitor;
    private final DriverParameters driverParameters;

    public DriverMonitorDecorator(VisitableJob2dDriver driver, DriverUsageMonitor monitor,
                                  DriverMonitor outputMonitor, DriverParameters driverParameters) {
        super(driver);
        this.monitor = monitor;
        this.outputMonitor = outputMonitor;
        this.driverParameters = driverParameters;
    }

    private void triggerEvent(DriverEventType eventType) {
        DriverEventManager.getInstance().triggerEvent(eventType);
    }

    @Override
    public void setPosition(int x, int y) {

        monitor.recordHeadMove(x, y);

        if (monitor.getHeadDistance() > driverParameters.getMaxHeadDistance()) {
            triggerEvent(DriverEventType.HEAD_DISTANCE_EXCEEDED);
            if (monitor.getHeadDistance() !=0 ){
                return;
            }
        }

        driver.setPosition(x, y);
        outputMonitor.update(x, y, monitor.getHeadDistance(), monitor.getOperationDistance());
    }

    @Override
    public void operateTo(int x, int y) {
        monitor.recordOperationMove(x, y);

        if (monitor.getOperationDistance() > driverParameters.getMaxOperationDistance()) {
            triggerEvent(DriverEventType.OP_DISTANCE_EXCEEDED);
            if (monitor.getOperationDistance() !=0 ){
                return;
            }
        }

        driver.operateTo(x, y);
        outputMonitor.update(x, y, monitor.getHeadDistance(), monitor.getOperationDistance());
    }
}
