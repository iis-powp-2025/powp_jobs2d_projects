package edu.kis.powp.jobs2d.drivers.monitoring;

import edu.kis.powp.jobs2d.drivers.DriverVisitor;
import edu.kis.powp.jobs2d.drivers.VisitableJob2dDriver;

public class DriverMonitorDecorator implements VisitableJob2dDriver {
    private final VisitableJob2dDriver driver;
    private final DriverUsageMonitor monitor;
    private final DriverMonitor outputMonitor;

    public DriverMonitorDecorator(VisitableJob2dDriver driver, DriverUsageMonitor monitor,
            DriverMonitor outputMonitor) {
        this.driver = driver;
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

    @Override
    public void accept(DriverVisitor visitor) {
        visitor.visit(this);
    }
}
