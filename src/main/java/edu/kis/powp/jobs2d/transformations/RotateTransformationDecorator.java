package edu.kis.powp.jobs2d.transformations;

import edu.kis.powp.jobs2d.drivers.VisitableJob2dDriver;

public class RotateTransformationDecorator extends TransformationDecorator {
    private final double cos, sin;

    public RotateTransformationDecorator(VisitableJob2dDriver driver, double degrees) {
        super(driver);
        double radians = Math.toRadians(degrees);
        this.cos = Math.cos(radians);
        this.sin = Math.sin(radians);
    }

    @Override
    protected int[] transformation(int x, int y) {
        return new int[] {
                (int) Math.round(x * cos - y * sin),
                (int) Math.round(x * sin + y * cos)
        };
    }

}