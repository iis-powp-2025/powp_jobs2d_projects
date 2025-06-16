package edu.kis.powp.jobs2d.canva;

import edu.kis.powp.jobs2d.canva.shapes.CanvaShape;
import edu.kis.powp.jobs2d.drivers.AbstractDecorator;
import edu.kis.powp.jobs2d.drivers.VisitableJob2dDriver;
import edu.kis.powp.jobs2d.features.WorkspaceFeature;

/**
 * A {@link VisitableJob2dDriver} decorator that adds optional clipping behavior to constrain
 * drawing operations within a defined {@link CanvaShape} workspace.
 * <p>
 * This decorator intercepts all drawing commands (setPosition, operateTo) and, depending on
 * the clipping mode, restricts them to stay within the provided canvas shape bounds.
 * It is designed to be dynamically enabled or disabled via {@link #toggleClipping()} or {@link #setClipping(boolean)}.
 */
public class ClippingJobs2dDriverDecorator extends AbstractDecorator {
    private boolean clipping = false;

    private int currentX, currentY;

    public ClippingJobs2dDriverDecorator(VisitableJob2dDriver driver) {
        super(driver);
    }

    public void setDriver(VisitableJob2dDriver driver) { super.driver = driver; }

    public boolean isClipping() { return clipping; }

    /**
     * Toggles the clipping mode on or off.
     * When enabled, drawing operations are restricted to the canvas shape.
     */
    public void toggleClipping() { this.clipping = !this.clipping; }

    /**
     * Explicitly sets the clipping mode.
     *
     * @param value {@code true} to enable clipping, {@code false} to disable
     */
    public void setClipping(boolean value) { this.clipping = value; }

    /**
     * Sets the drawing position, optionally clipped to the canvas shape bounds.
     */
    @Override
    public void setPosition(int x, int y) {
        if ( super.driver == null )
            return;

        int[] clipped = clipPointToBounds(x, y);
        currentX = clipped[0];
        currentY = clipped[1];
        super.driver.setPosition(currentX,  currentY);
    }

    /**
     * Draws a line from the current position to the given coordinates,
     * optionally clipped to the canvas shape bounds.
     */
    @Override
    public void operateTo(int x, int y) {
        if ( super.driver == null )
            return;
        int[] clipped;
        if( clipping ) {
            clipped = clipPointToBounds(x, y);
        }
        else {
            clipped = new int[] {x, y};
        }

        currentX = clipped[0];
        currentY = clipped[1];
        super.driver.operateTo(currentX,  currentY);
    }

    private int[] clipPointToBounds(int x, int y) {
        CanvaShape shape = WorkspaceFeature.getWorkspaceManager().getCurrentCanvaShape();
        if (shape == null || shape.contains(x, y)) {
            return new int[]{x, y};
        } else {
            return shape.clipLine(currentX,  currentY, x, y);
        }
    }
}
