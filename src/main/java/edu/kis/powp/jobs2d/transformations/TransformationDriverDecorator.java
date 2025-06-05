package edu.kis.powp.jobs2d.transformations;

import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.drivers.VisitableJob2dDriver;
import edu.kis.powp.jobs2d.drivers.visitors.DriverVisitor;

public class TransformationDriverDecorator implements VisitableJob2dDriver {
    private final VisitableJob2dDriver driver;
    private final PointTransformation transformation;

    public TransformationDriverDecorator(VisitableJob2dDriver driver, PointTransformation transformation) {
        this.driver = driver;
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

    @Override
    public void accept(DriverVisitor visitor) {

    }
}
