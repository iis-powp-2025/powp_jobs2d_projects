package edu.kis.powp.jobs2d.canva;

import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.canva.shapes.CanvaShape;

/**
 * A {@link Job2dDriver} decorator that adds optional clipping behavior to constrain
 * drawing operations within a defined {@link CanvaShape} workspace.
 * <p>
 * This decorator intercepts all drawing commands (setPosition, operateTo) and, depending on
 * the clipping mode, restricts them to stay within the provided canvas shape bounds.
 * It is designed to be dynamically enabled or disabled via {@link #toggleClipping()} or {@link #setClipping(boolean)}.
 */
public class ClippingJobs2dDriverDecorator implements Job2dDriver {
    private Job2dDriver innerDriver;
    private CanvaShape canvaShape;
    private boolean clipping = false;

    private int currentX, currentY;

    /**
     * Constructs a decorator for the given {@code innerDriver}.
     *
     * @param innerDriver the driver to be wrapped and enhanced with clipping logic
     */
    public ClippingJobs2dDriverDecorator(Job2dDriver innerDriver) {
        this.innerDriver = innerDriver;
    }

    public CanvaShape getCanvasShape() { return canvaShape; }
    public void setCanvaShape(CanvaShape canvaShape) { this.canvaShape = canvaShape; }

    public Job2dDriver getInnerDriver() { return innerDriver; }
    public void setInnerDriver(Job2dDriver innerDriver) { this.innerDriver = innerDriver; }

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
        if ( innerDriver == null )
            return;

        int[] clipped = clipPointToBounds(x, y);
        currentX = clipped[0];
        currentY = clipped[1];
        innerDriver.setPosition(currentX,  currentY);
    }

    /**
     * Draws a line from the current position to the given coordinates,
     * optionally clipped to the canvas shape bounds.
     */
    @Override
    public void operateTo(int x, int y) {
        if ( innerDriver == null )
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
        innerDriver.operateTo(currentX,  currentY);
    }

    private int[] clipPointToBounds(int x, int y) {
        if (canvaShape == null || canvaShape.contains(x, y)) {
            return new int[]{x, y};
        } else {
            return canvaShape.clipLine(currentX,  currentY, x, y);
        }
    }
}
