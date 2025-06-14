package edu.kis.powp.jobs2d.transformations;

import edu.kis.powp.jobs2d.drivers.VisitableJob2dDriver;

public class ScaleTransformationDecorator extends TransformationDecorator {
    private final double scaleX;
    private final double scaleY;

    public ScaleTransformationDecorator(VisitableJob2dDriver driver, double scaleX, double scaleY) {
        super(driver);
        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }

    @Override
    protected int[] transformation(int x, int y) {
        int newX = (int) Math.round(scaleX * x);
        int newY = (int) Math.round(scaleY * y);
        return new int[] { newX, newY };
    }
}