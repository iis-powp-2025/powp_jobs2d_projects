package edu.kis.powp.jobs2d.transformations;

import edu.kis.powp.jobs2d.drivers.AbstractDecorator;
import edu.kis.powp.jobs2d.drivers.VisitableJob2dDriver;

public class TransformationDriverDecorator extends AbstractDecorator {

    private final PointTransformation transformation;

    public TransformationDriverDecorator(VisitableJob2dDriver driver, PointTransformation transformation) {
        super(driver);
        this.transformation = transformation;
    }

    @Override
    public void setPosition(int x, int y) {
        int[] pt = transformation.transformation(x, y);
        driver.setPosition(pt[0], pt[1]);
    }

    @Override
    public void operateTo(int x, int y) {
        int[] pt = transformation.transformation(x, y);
        driver.operateTo(pt[0], pt[1]);
    }

    @Override
    public String toString() {
        return driver.toString() + " [Transformed: " + transformation.getName() + "]";
    }
}
