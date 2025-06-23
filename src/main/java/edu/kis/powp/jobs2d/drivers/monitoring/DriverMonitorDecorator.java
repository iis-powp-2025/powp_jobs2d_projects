package edu.kis.powp.jobs2d.drivers.monitoring;

import edu.kis.powp.jobs2d.drivers.AbstractDecorator;
import edu.kis.powp.jobs2d.drivers.VisitableJob2dDriver;
import edu.kis.powp.jobs2d.drivers.monitoring.DriverEventManager.DriverEventType;
import edu.kis.powp.observer.Publisher;


public class DriverMonitorDecorator extends AbstractDecorator {
    private final DriverUsageMonitor monitor;
    private final Publisher movePublisher = new Publisher();
    private static boolean monitorEnabled = true;
    
    private final DriverMonitor outputMonitor;
    private final DriverParameters driverParameters;
    private final DriverEventManager eventManager;


    public DriverMonitorDecorator(
            VisitableJob2dDriver driver,
            DriverUsageMonitor monitor,
            DriverMonitor outputMonitor,
            DriverParameters driverParameters,
            DriverEventManager eventManager
    ) {
        super(driver);
        this.monitor = monitor;
        this.outputMonitor = outputMonitor;
        this.driverParameters = driverParameters;
        this.eventManager = eventManager;
    }

    private void triggerEvent(DriverEventType eventType) {
        eventManager.triggerEvent(eventType);
  
    public DriverMonitorDecorator(VisitableJob2dDriver driver, DriverUsageMonitor monitor) {
        super(driver);
        this.monitor = monitor;
    }
    public static boolean isMonitorEnabled() {
        return monitorEnabled;
    }
      
    private void triggerEvent(DriverEventType eventType) {
        eventManager.triggerEvent(eventType);

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

        if(monitorEnabled) movePublisher.notifyObservers();
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

        if(monitorEnabled) movePublisher.notifyObservers();
    }
    public Publisher getMovePublisher(){
        return movePublisher;
    }
    public static void setMonitorEnabled(boolean enabled) {
        monitorEnabled = enabled;
    }

  
}
