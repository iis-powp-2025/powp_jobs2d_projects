package edu.kis.powp.jobs2d.transformations;

import edu.kis.powp.jobs2d.Job2dDriver;

public class TransformationDriverDecorator implements Job2dDriver {
    private final Job2dDriver driver;
    private final PointTransformation transformation;

    public TransformationDriverDecorator(Job2dDriver driver, PointTransformation transformation) {
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
}
