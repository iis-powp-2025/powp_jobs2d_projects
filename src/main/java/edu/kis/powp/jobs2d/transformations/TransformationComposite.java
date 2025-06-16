package edu.kis.powp.jobs2d.transformations;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TransformationComposite implements PointTransformation{
    private final List<PointTransformation> transformations = new ArrayList<>();

    public void addTransformation(PointTransformation transformation) {
        transformations.add(transformation);
    }

    public int[] transformation(int x, int y) {
        for (PointTransformation t : transformations) {
            int[] result = t.transformation(x, y);
            x = result[0];
            y = result[1];
        }
        return new int[]{x, y};
    }

    @Override
    public String getName() {
        StringBuilder transformationNames = new StringBuilder();
        for (PointTransformation t : transformations) {
            transformationNames.append(t.getName() + " ");
        }
        return transformationNames.toString();
    }
}
