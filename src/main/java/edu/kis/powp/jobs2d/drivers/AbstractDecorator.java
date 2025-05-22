package edu.kis.powp.jobs2d.drivers;

import edu.kis.powp.jobs2d.drivers.visitors.DriverVisitor;

public abstract class AbstractDecorator implements VisitableJob2dDriver {
    protected final VisitableJob2dDriver driver;

    public AbstractDecorator(VisitableJob2dDriver driver) {
        this.driver = driver;
    }

    public VisitableJob2dDriver getDriver() {
        return driver;
    }

    public void accept(DriverVisitor visitor) {
        visitor.visit(this);
    }
}
