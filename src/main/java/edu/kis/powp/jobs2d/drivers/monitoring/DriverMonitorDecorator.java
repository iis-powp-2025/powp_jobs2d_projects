package edu.kis.powp.jobs2d.drivers.monitoring;

import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.observer.Publisher;

public class DriverMonitorDecorator implements Job2dDriver {
    private final Job2dDriver driver;
    private final DriverUsageMonitor monitor;
    private final Publisher movePublisher = new Publisher();
    private static boolean monitorEnabled = true;
    public DriverMonitorDecorator(Job2dDriver driver, DriverUsageMonitor monitor) {
        this.driver = driver;
        this.monitor = monitor;
    }


    @Override
    public void setPosition(int x, int y) {
        monitor.recordHeadMove(x, y);
        driver.setPosition(x, y);
        //outputMonitor.update(x,y, monitor.getHeadDistance(), monitor.getOperationDistance());

        if(monitorEnabled) movePublisher.notifyObservers();
    }

    @Override
    public void operateTo(int x, int y) {
        monitor.recordOperationMove(x, y);
        driver.operateTo(x, y);
        //outputMonitor.update(x,y, monitor.getHeadDistance(), monitor.getOperationDistance());

         if(monitorEnabled) movePublisher.notifyObservers();
    }

    public Publisher getMovePublisher(){
        return movePublisher;
    }
    public static void setMonitorEnabled(boolean enabled) {
        monitorEnabled = enabled;
    }
}
