package edu.kis.powp.jobs2d.transformations;

public class TranslateTransformation implements PointTransformation {
    private final double translateX;
    private final double translateY;

    public TranslateTransformation(double translateX, double translateY) {
        this.translateX = translateX;
        this.translateY = translateY;
    }

    @Override
    public int[] transformation(int x, int y) {
        int newX = (int) Math.round(x + translateX);
        int newY = (int) Math.round(y + translateY);
        return new int[] { newX, newY };
    }

    @Override
    public String getName() {
        return "Moved X: " + translateX + ", Y: " + translateY;
    }
}
