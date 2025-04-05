package edu.kis.powp.jobs2d.drivers;

import edu.kis.legacy.drawer.panel.DrawPanelController;
import edu.kis.legacy.drawer.shape.ILine;
import edu.kis.legacy.drawer.shape.LineFactory;
import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.transformations.Transformation;
import java.util.ArrayList;
import java.util.List;

public class TransformationDriver implements Job2dDriver {
    private int startX = 0, startY = 0;
    private final DrawPanelController drawPanelController;
    private final List<Transformation> transformations = new ArrayList<>();

    public TransformationDriver(DrawPanelController drawPanelController) {
        this.drawPanelController = drawPanelController;
    }

    public void addTransformation(Transformation transformation) {
        transformations.add(transformation);
    }

    private int[] applyTransformations(int x, int y) {
        int[] point = {x,y};
        for (Transformation t : transformations) {
            point = t.apply(x,y);
        }
        return point;
    }

    @Override
    public void setPosition(int x, int y) {
        int[] transformed = applyTransformations(x, y);
        this.startX = transformed[0];
        this.startY = transformed[1];
    }

    @Override
    public void operateTo(int x, int y) {
        int[] endPoint = applyTransformations(x, y);

        ILine line = LineFactory.getBasicLine();
        line.setStartCoordinates(this.startX, this.startY);
        this.setPosition(x, y);
        line.setEndCoordinates(endPoint[0], endPoint[1]);

        drawPanelController.drawLine(line);
    }
}