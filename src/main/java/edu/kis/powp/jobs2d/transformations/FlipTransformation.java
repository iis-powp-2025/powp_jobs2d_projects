package edu.kis.powp.jobs2d.transformations;

public class FlipTransformation implements PointTransformation {
    private final boolean flipHorizontal;
    private final boolean flipVertical;

    public FlipTransformation(boolean flipHorizontal, boolean flipVertical) {
        this.flipHorizontal = flipHorizontal;
        this.flipVertical = flipVertical;
    }

    @Override
    public int[] transformation(int x, int y) {
        int newX = flipHorizontal ? -x : x;
        int newY = flipVertical ? -y : y;
        return new int[]{newX, newY};
    }

    @Override
    public String getName() {
        return (flipHorizontal && flipVertical) ? "Flip: (H) & (V)" :
                flipVertical ? "Flip: (V)" : flipHorizontal ? "Flip: (H)" : "";
    }
}