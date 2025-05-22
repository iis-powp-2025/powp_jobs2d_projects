package edu.kis.powp.jobs2d.drivers.observers;

import edu.kis.powp.appbase.Application;
import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.features.DriverFeature;
import edu.kis.powp.observer.Subscriber;


public class DriverLabelChangeObserver implements Subscriber {
    private final Application app;

    public DriverLabelChangeObserver(Application app){
        this.app = app;
    }

    @Override
    public void update() {
        Job2dDriver driver = DriverFeature.getDriverManager().getCurrentDriver();
        app.updateInfo(driver.toString());
    }

    public String toString(){
        return "Drawer Feature Change Observer";
    }
}