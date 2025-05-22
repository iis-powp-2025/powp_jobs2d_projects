package edu.kis.powp.jobs2d.canva.shapes;

import edu.kis.powp.jobs2d.Job2dDriver;
/**
 * A circular canvas implementation of the {@link CanvaShape} interface.
 * <p>
 * Represents a circular area centered at the origin (0,0) with a specified radius.
 * Provides functionality for containment testing, line clipping, and drawing.
 */
public class CircularCanva implements CanvaShape {
    private final int radius;

    /**
     * Constructs a new {@code CircularCanva} with the specified radius.
     *
     * @param radius the radius of the circular canvas, in arbitrary units
     */
    public CircularCanva(int radius) {
        this.radius = radius;
    }

    /**
     * Checks whether a given point (x, y) lies within or on the boundary
     * of the circular canvas.
     *
     * @param x the x-coordinate of the point
     * @param y the y-coordinate of the point
     * @return {@code true} if the point is inside the circle or on its edge; {@code false} otherwise
     */
    @Override
    public boolean contains(int x, int y) {
        return (x * x + y * y) <= (radius * radius);
    }

    /**
     * Clips a line defined by start and end points so that it stays within the circle.
     * The method samples 100 points along the line and returns the last point
     * that lies within the circle.
     *
     * @param startX the x-coordinate of the line's starting point
     * @param startY the y-coordinate of the line's starting point
     * @param endX the x-coordinate of the line's ending point
     * @param endY the y-coordinate of the line's ending point
     * @return an array containing the clipped x and y coordinates within the circle
     */
    @Override
    public int[] clipLine(int startX, int startY, int endX, int endY) {
        final int steps = 100;
        double dx = (endX - startX) / (double) steps;
        double dy = (endY - startY) / (double) steps;

        int clippedX = startX;
        int clippedY = startY;

        for (int i = 1; i <= steps; i++) {
            int cx = (int) Math.round(startX + dx * i);
            int cy = (int) Math.round(startY + dy * i);
            if (!contains(cx, cy)) {
                break;
            }
            clippedX = cx;
            clippedY = cy;
        }

        return new int[]{clippedX, clippedY};
    }

    /**
     * Draws the circular canvas using the provided {@link Job2dDriver}.
     * The circle is approximated using a 17-segment polygon.
     *
     * @param driver the drawing driver used to render the shape
     */
    @Override
    public void draw(Job2dDriver driver) {
        int steps = 17;
        double angleStep = 2 * Math.PI / steps;

        driver.setPosition(radius, 0);
        for (int i = 1; i <= steps; i++) {
            double angle = angleStep * i;
            int x = (int) Math.round(Math.cos(angle) * radius);
            int y = (int) Math.round(Math.sin(angle) * radius);
            driver.operateTo(x, y);
        }
    }
}

