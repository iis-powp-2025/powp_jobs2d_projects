package edu.kis.powp.jobs2d.drivers.canva;

import edu.kis.legacy.drawer.shape.LineFactory;
import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.drivers.adapter.LineDriverAdapter;
import edu.kis.powp.jobs2d.drivers.canva.shapes.CanvaShape;
import edu.kis.powp.jobs2d.features.DrawerFeature;


public class WorkspaceCanva implements Job2dDriver {
    private final Job2dDriver innerDriver;

    private final Job2dDriver borderDriver;

    private int X, Y;
    private CanvaShape bound;

    public WorkspaceCanva(Job2dDriver innerDriver, CanvaShape bound) {
        this.innerDriver = innerDriver;
        this.bound = bound;

        this.borderDriver = new LineDriverAdapter(DrawerFeature.getDrawerController(), LineFactory.getDottedLine(), "border");
    }

    @Override
    public void setPosition(int i, int j) {
        int[] clipped = clipPointToBounds(i, j);
        X = clipped[0];
        Y = clipped[1];
        innerDriver.setPosition(X, Y);
    }

    @Override
    public void operateTo(int i, int j) {
        int[] clipped = bound.clipLine(X, Y, i, j);
        X = clipped[0];
        Y = clipped[1];
        innerDriver.operateTo(X, Y);
    }

    public void drawWorkspaceBoundary() {
        bound.draw(borderDriver);
    }


    private int[] clipPointToBounds(int x, int y) {
        if (bound.contains(x, y)) {
            return new int[]{x, y};
        } else {
            return bound.clipLine(X, Y, x, y);
        }
    }

}
