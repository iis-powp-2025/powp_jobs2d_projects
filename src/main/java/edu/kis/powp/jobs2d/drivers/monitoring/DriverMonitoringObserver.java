package edu.kis.powp.jobs2d.drivers.monitoring;

import edu.kis.powp.appbase.Application;
import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.drivers.DriverManager;
import edu.kis.powp.jobs2d.drivers.VisitableJob2dDriver;
import edu.kis.powp.jobs2d.features.DriverFeature;
import edu.kis.powp.observer.Subscriber;

public class DriverMonitoringObserver implements Subscriber {
    private final DriverManager driverManager;
    private DriverMonitor driverMonitor;

    public DriverMonitoringObserver(DriverManager driverManager,DriverMonitor driverMonitor) {
        this.driverManager = driverManager;
        this.driverMonitor = driverMonitor;
    }

    @Override
    public void update() {
        VisitableJob2dDriver driver = DriverFeature.getDriverManager().getCurrentDriver();

        if (!(driver instanceof DriverMonitorDecorator)) {
            DriverUsageMonitor usageMonitor = new DriverUsageMonitor();

            DriverMonitorDecorator monitoredDriver = new DriverMonitorDecorator(driver, usageMonitor);
            driverManager.setCurrentDriver(monitoredDriver);

            monitoredDriver.getMovePublisher().addSubscriber(() -> {
                int[] position = usageMonitor.getPosition();
                int x = position[0];
                int y = position[1];
                driverMonitor.update(x,y,usageMonitor.getHeadDistance(), usageMonitor.getOperationDistance());
            });
        }
    }

    public String toString(){
        return "Driver monitoring test";
    }
}
