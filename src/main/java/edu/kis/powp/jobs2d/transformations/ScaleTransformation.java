package edu.kis.powp.jobs2d.transformations;

public class ScaleTransformation implements PointTransformation {
    private final double scaleX;
    private final double scaleY;

    public ScaleTransformation(double scaleX, double scaleY) {
        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }

    @Override
    public int[] transformation(int x, int y) {
        int newX = (int) Math.round(scaleX * x);
        int newY = (int) Math.round(scaleY * y);
        return new int[] { newX, newY };
    }

    @Override
    public String getName() {
        return "Scale X: " + scaleX + ", Y: " + scaleY;
    }
}