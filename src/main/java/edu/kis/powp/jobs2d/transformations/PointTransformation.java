package edu.kis.powp.jobs2d.transformations;

public interface PointTransformation {
    int[] transformation(int x, int y);
    String getName();
}
