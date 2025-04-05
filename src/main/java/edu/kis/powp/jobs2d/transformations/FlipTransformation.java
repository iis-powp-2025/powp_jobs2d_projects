package edu.kis.powp.jobs2d.transformations;

public class FlipTransformation implements Transformation {
    private final boolean flipHorizontal;
    private final boolean flipVertical;

    public FlipTransformation(boolean flipHorizontal, boolean flipVertical) {
        this.flipHorizontal = flipHorizontal;
        this.flipVertical = flipVertical;
    }

    @Override
    public int[] apply(int x, int y) {
        int newX = flipHorizontal ? -x : x;
        int newY = flipVertical ? -y : y;
        return new int[] { newX, newY };
    }
}