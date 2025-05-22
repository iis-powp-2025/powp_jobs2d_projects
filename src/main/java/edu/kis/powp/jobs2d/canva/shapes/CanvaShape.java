package edu.kis.powp.jobs2d.canva.shapes;

import edu.kis.powp.jobs2d.Job2dDriver;

/**
 * Interface representing a drawable shape on a canvas.
 * Provides methods for checking point containment, clipping lines
 * to the shape boundaries, and drawing the shape using a given driver.
 */
public interface CanvaShape {

    /**
     * Checks whether the point with coordinates (x, y) is inside the shape.
     *
     * @param x the X coordinate of the point
     * @param y the Y coordinate of the point
     * @return true if the point is within the shape; false otherwise
     */
    boolean contains(int x, int y);

    /**
     * Clips a given line to the boundaries of the shape.
     * If the line intersects the shape, returns new endpoint coordinates
     * corresponding to the portion of the line that lies within the shape.
     *
     * @param startX the X coordinate of the start point
     * @param startY the Y coordinate of the start point
     * @param endX the X coordinate of the end point
     * @param endY the Y coordinate of the end point
     * @return an array of 4 integers representing the clipped coordinates:
     *         {newStartX, newStartY, newEndX, newEndY}, or null if the line does not intersect the shape
     */
    int[] clipLine(int startX, int startY, int endX, int endY);

    /**
     * Draws the shape using the provided Job2dDriver.
     *
     * @param driver the driver responsible for drawing
     */
    void draw(Job2dDriver driver);
}
