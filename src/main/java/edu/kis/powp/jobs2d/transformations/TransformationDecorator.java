package edu.kis.powp.jobs2d.transformations;

import edu.kis.powp.jobs2d.drivers.AbstractDecorator;
import edu.kis.powp.jobs2d.drivers.VisitableJob2dDriver;

public abstract class TransformationDecorator extends AbstractDecorator {

    public TransformationDecorator(VisitableJob2dDriver driver) {
        super(driver);
    }

    protected abstract int[] transformation(int x, int y);

    @Override
    public void setPosition(int x, int y) {
        int[] transformed = transformation(x, y);
        driver.setPosition(transformed[0], transformed[1]);
    }

    @Override
    public void operateTo(int x, int y) {
        int[] transformed = transformation(x, y);
        driver.operateTo(transformed[0], transformed[1]);
    }

}