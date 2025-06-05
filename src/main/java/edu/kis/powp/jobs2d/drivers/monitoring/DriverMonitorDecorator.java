package edu.kis.powp.jobs2d.drivers.monitoring;

import edu.kis.powp.jobs2d.drivers.AbstractDecorator;
import edu.kis.powp.jobs2d.drivers.VisitableJob2dDriver;

public class DriverMonitorDecorator extends AbstractDecorator {
    private final DriverUsageMonitor monitor;
    private final DriverMonitor outputMonitor;

    public DriverMonitorDecorator(VisitableJob2dDriver driver, DriverUsageMonitor monitor,
            DriverMonitor outputMonitor) {
        super(driver);
        this.monitor = monitor;
        this.outputMonitor = outputMonitor;
    }

    @Override
    public void setPosition(int x, int y) {
        monitor.recordHeadMove(x, y);
        driver.setPosition(x, y);
        outputMonitor.update(x, y, monitor.getHeadDistance(), monitor.getOperationDistance());
    }

    @Override
    public void operateTo(int x, int y) {
        monitor.recordOperationMove(x, y);
        driver.operateTo(x, y);
        outputMonitor.update(x, y, monitor.getHeadDistance(), monitor.getOperationDistance());
    }
}
