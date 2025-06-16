package edu.kis.powp.jobs2d.drivers;

import java.util.ArrayList;
import java.util.List;

import edu.kis.powp.jobs2d.drivers.visitors.DriverVisitor;

public class ComplexDriver implements VisitableJob2dDriver {
    private final List<VisitableJob2dDriver> children = new ArrayList<>();

    public void add(VisitableJob2dDriver driver) {
        children.add(driver);
    }

    public void remove(VisitableJob2dDriver driver) {
        children.remove(driver);
    }

    public List<VisitableJob2dDriver> getChildren() {
        return this.children;
    }

    @Override
    public void setPosition(int x, int y) {
        for (VisitableJob2dDriver child : children) {
            child.setPosition(x, y);
        }
    }

    @Override
    public void operateTo(int x, int y) {
        for (VisitableJob2dDriver child : children) {
            child.operateTo(x, y);
        }
    }

    @Override
    public void accept(DriverVisitor visitor) {
        visitor.visit(this);
    }
}
