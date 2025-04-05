package edu.kis.powp.jobs2d.transformations;

public class ScaleTransformation implements Transformation {
    private final double scale_x;
    private final double scale_y;

    public ScaleTransformation(double scaleX, double scaleY) {
        scale_x = scaleX;
        scale_y = scaleY;
    }


    @Override
    public int[] apply(int x, int y) {
        int newX = (int)(scale_x * x);
        int newY = (int)(scale_y * y);
        return new int[] { newX, newY };
    }
}