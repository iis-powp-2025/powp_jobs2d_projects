package edu.kis.powp.jobs2d.drivers.canva.shapes;

import edu.kis.powp.jobs2d.Job2dDriver;

public class CircularCanva implements CanvaShape {
    private final int radius;

    public CircularCanva(int radius) {
        this.radius = radius;
    }

    @Override
    public boolean contains(int x, int y) {
        // Sprawdzenie czy punkt (x, y) znajduje się wewnątrz okręgu o promieniu radius
        return (x * x + y * y) <= (radius * radius);
    }

    @Override
    public int[] clipLine(int x0, int y0, int x1, int y1) {
        final int steps = 100;
        double dx = (x1 - x0) / (double) steps;
        double dy = (y1 - y0) / (double) steps;

        int clippedX = x0;
        int clippedY = y0;

        // Iteracyjnie poszukujemy punktu przecięcia odcinka z okręgiem
        for (int i = 1; i <= steps; i++) {
            int cx = (int) Math.round(x0 + dx * i);
            int cy = (int) Math.round(y0 + dy * i);
            if (!contains(cx, cy)) {
                break;
            }
            clippedX = cx;
            clippedY = cy;
        }

        return new int[]{clippedX, clippedY};
    }

    @Override
    public void draw(Job2dDriver driver) {
        int steps = 17; // liczba punktów wykorzystywanych do przybliżenia okręgu
        double angleStep = 2 * Math.PI / steps;

        // Ustalenie początkowego położenia na okręgu (punkt na osi x)
        driver.setPosition(radius, 0);
        for (int i = 1; i <= steps; i++) {
            double angle = angleStep * i;
            int x = (int) Math.round(Math.cos(angle) * radius);
            int y = (int) Math.round(Math.sin(angle) * radius);
            driver.operateTo(x, y);
        }
    }
}
