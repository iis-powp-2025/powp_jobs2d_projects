package edu.kis.powp.jobs2d.drivers.canva.shapes;

import edu.kis.powp.jobs2d.Job2dDriver;

public interface CanvaShape {
    boolean contains(int x, int y);
    int[] clipLine(int x0, int y0, int x1, int y1);
    void draw(Job2dDriver driver);
}