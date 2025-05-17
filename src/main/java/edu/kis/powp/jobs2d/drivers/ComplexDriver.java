package edu.kis.powp.jobs2d.drivers;

import edu.kis.powp.jobs2d.Job2dDriver;

import java.util.ArrayList;
import java.util.List;

public class ComplexDriver implements VisitableJob2dDriver {
    private final List<Job2dDriver> children = new ArrayList<>();

    public void add(Job2dDriver driver) {
        children.add(driver);
    }

    public void remove(Job2dDriver driver) {
        children.remove(driver);
    }

    public int getChildrenSize() {
        return children.size();
    }

    @Override
    public void setPosition(int x, int y) {
        for (Job2dDriver child : children) {
            child.setPosition(x, y);
        }
    }

    @Override
    public void operateTo(int x, int y) {
        for (Job2dDriver child : children) {
            child.operateTo(x, y);
        }
    }

    @Override
    public void accept(DriverVisitor visitor) {
        visitor.visit(this);
    }
}
