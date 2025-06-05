package edu.kis.powp.jobs2d.canva.shapes;

import edu.kis.powp.jobs2d.Job2dDriver;
/**
 * A rectangular canvas implementation of the {@link CanvaShape} interface.
 * <p>
 * Represents a centered rectangle (origin at 0,0) with specified width and height.
 * Provides functionality to check if a point is within bounds, clip a line segment to the rectangle,
 * and render the shape using a drawing driver.
 */
public class RectangleCanva implements CanvaShape {
    private final int width, height;

    /**
     * Constructs a new {@code RectangleCanva} with the given width and height.
     *
     * @param width  the total width of the rectangle, in arbitrary units
     * @param height the total height of the rectangle, in arbitrary units
     */
    public RectangleCanva(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * Checks whether a given point (x, y) lies within the rectangle bounds.
     * The rectangle is centered at the origin.
     *
     * @param x the x-coordinate of the point
     * @param y the y-coordinate of the point
     * @return {@code true} if the point lies inside or on the edge of the rectangle; {@code false} otherwise
     */
    @Override
    public boolean contains(int x, int y) {
        int a = width / 2;
        int b = height / 2;
        return x >= -a && x <= a && y >= -b && y <= b;
    }

    /**
     * Clips a line segment to the rectangular canvas using the Liang–Barsky algorithm.
     * If the line lies completely outside the rectangle, the original start point is returned.
     *
     * @param startX the x-coordinate of the line's starting point
     * @param startY the y-coordinate of the line's starting point
     * @param endX   the x-coordinate of the line's ending point
     * @param endY   the y-coordinate of the line's ending point
     * @return an array containing the clipped x and y coordinates within the rectangle
     */
    @Override
    public int[] clipLine(int startX, int startY, int endX, int endY) {
        double t0 = 0.0, t1 = 1.0;
        int dx = endX - startX, dy = endY - startY;
        int[] bounds = {-width / 2, width / 2, -height / 2, height / 2};
        double[] p = {-dx, dx, -dy, dy};
        double[] q = {startX - bounds[0], bounds[1] - startX, startY - bounds[2], bounds[3] - startY};

        for (int i = 0; i < 4; i++) {
            if (p[i] == 0 && q[i] < 0) return new int[]{startX, startY};
            double r = p[i] != 0 ? q[i] / p[i] : 0;
            if (p[i] < 0) t0 = Math.max(t0, r);
            if (p[i] > 0) t1 = Math.min(t1, r);
        }

        if (t0 > t1) return new int[]{startX, startY};
        return new int[]{
                (int) (startX + t1 * dx),
                (int) (startY + t1 * dy)
        };
    }

    /**
     * Draws the rectangular canvas using the provided {@link Job2dDriver}.
     * The rectangle is drawn starting from the bottom-left corner in a clockwise direction.
     *
     * @param driver the drawing driver used to render the rectangle
     */
    @Override
    public void draw(Job2dDriver driver) {
        int halfWidth = width / 2;
        int halfHeight = height / 2;

        driver.setPosition(-halfWidth, -halfHeight);
        driver.operateTo(halfWidth, -halfHeight);
        driver.operateTo(halfWidth, halfHeight);
        driver.operateTo(-halfWidth, halfHeight);
        driver.operateTo(-halfWidth, -halfHeight);
    }
}
