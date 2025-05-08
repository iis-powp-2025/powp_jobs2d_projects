package edu.kis.powp.jobs2d.transformations;

import java.util.ArrayList;
import java.util.List;

public class TransformationDecorator {
    private final List<PointTransformation> transformations = new ArrayList<>();

    public void addTransformation(PointTransformation transformation) {
        transformations.add(transformation);
    }

    public int[] transform(int x, int y) {
        for (PointTransformation t : transformations) {
            int[] result = t.transformation(x, y);
            x = result[0];
            y = result[1];
        }
        return new int[]{x, y};
    }
}
