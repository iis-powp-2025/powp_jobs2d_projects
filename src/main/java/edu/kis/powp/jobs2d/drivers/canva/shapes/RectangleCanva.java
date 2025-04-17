package edu.kis.powp.jobs2d.drivers.canva.shapes;

import edu.kis.powp.jobs2d.Job2dDriver;

public class RectangleCanva implements CanvaShape {
    private final int width, height;

    public RectangleCanva(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public boolean contains(int x, int y) {
        int a = width / 2;
        int b = height / 2;
        return x >= -a && x <= a && y >= -b && y <= b;
    }

    @Override
    public int[] clipLine(int x0, int y0, int x1, int y1) {
        // Liang-Barsky algorytm, jak wcześniej
        double t0 = 0.0, t1 = 1.0;
        int dx = x1 - x0, dy = y1 - y0;
        int[] bounds = {-width / 2, width / 2, -height / 2, height / 2};
        double[] p = {-dx, dx, -dy, dy};
        double[] q = {x0 - bounds[0], bounds[1] - x0, y0 - bounds[2], bounds[3] - y0};

        for (int i = 0; i < 4; i++) {
            if (p[i] == 0 && q[i] < 0) return new int[]{x0, y0};
            double r = p[i] != 0 ? q[i] / p[i] : 0;
            if (p[i] < 0) t0 = Math.max(t0, r);
            if (p[i] > 0) t1 = Math.min(t1, r);
        }

        if (t0 > t1) return new int[]{x0, y0};
        return new int[]{
                (int)(x0 + t1 * dx),
                (int)(y0 + t1 * dy)
        };
    }

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
