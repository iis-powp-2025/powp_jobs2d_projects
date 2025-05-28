package edu.kis.powp.jobs2d.drivers.observers;

import edu.kis.powp.jobs2d.canva.ClippingJobs2dDriverDecorator;
import edu.kis.powp.jobs2d.drivers.VisitableJob2dDriver;
import edu.kis.powp.jobs2d.features.DriverFeature;
import edu.kis.powp.observer.Subscriber;

public class WorkspaceDriverChangeObserver implements Subscriber {
    private final ClippingJobs2dDriverDecorator clipper;

    public WorkspaceDriverChangeObserver(ClippingJobs2dDriverDecorator clipper) {
        this.clipper = clipper;
    }

    @Override
    public void update() {
        VisitableJob2dDriver currentDriver = DriverFeature.getDriverManager().getCurrentDriver();
        if (currentDriver == clipper)
            return;

        clipper.setDriver(currentDriver);
        DriverFeature.getDriverManager().setCurrentDriver(clipper);
    }
}
