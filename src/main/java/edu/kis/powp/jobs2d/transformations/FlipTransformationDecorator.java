package edu.kis.powp.jobs2d.transformations;
import edu.kis.powp.jobs2d.drivers.VisitableJob2dDriver;
import edu.kis.powp.jobs2d.drivers.visitors.DriverVisitor;

public class FlipTransformationDecorator extends TransformationDecorator {
    private final boolean flipHorizontal;
    private final boolean flipVertical;

    public FlipTransformationDecorator(VisitableJob2dDriver driver, boolean flipHorizontal, boolean flipVertical) {
        super(driver);
        this.flipHorizontal = flipHorizontal;
        this.flipVertical = flipVertical;
    }

    @Override
    protected int[] transformation(int x, int y) {
        int newX = flipHorizontal ? -x : x;
        int newY = flipVertical ? -y : y;
        return new int[] { newX, newY };
    }

    @Override
    public void accept(DriverVisitor visitor) {
        
    }
}