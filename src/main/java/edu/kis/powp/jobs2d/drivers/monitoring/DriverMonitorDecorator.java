package edu.kis.powp.jobs2d.drivers.monitoring;

import edu.kis.powp.jobs2d.drivers.AbstractDecorator;
import edu.kis.powp.jobs2d.drivers.VisitableJob2dDriver;
import edu.kis.powp.observer.Publisher;


public class DriverMonitorDecorator extends AbstractDecorator {
    private final DriverUsageMonitor monitor;
    private final Publisher movePublisher = new Publisher();
    private static boolean monitorEnabled = true;
    public DriverMonitorDecorator(VisitableJob2dDriver driver, DriverUsageMonitor monitor) {
        super(driver);
        this.monitor = monitor;
    }
    public static boolean isMonitorEnabled() {
        return monitorEnabled;
    }

    @Override
    public void setPosition(int x, int y) {
        monitor.recordHeadMove(x, y);
        driver.setPosition(x, y);

        if(monitorEnabled) movePublisher.notifyObservers();
    }

    @Override
    public void operateTo(int x, int y) {
        monitor.recordOperationMove(x, y);
        driver.operateTo(x, y);

        if(monitorEnabled) movePublisher.notifyObservers();
    }
    public Publisher getMovePublisher(){
        return movePublisher;
    }
    public static void setMonitorEnabled(boolean enabled) {
        monitorEnabled = enabled;
    }

  
}
