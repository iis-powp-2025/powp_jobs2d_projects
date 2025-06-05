package edu.kis.powp.jobs2d.drivers.visitors;

import edu.kis.powp.jobs2d.drivers.ComplexDriver;
import edu.kis.powp.jobs2d.drivers.InformativeLoggerDriver;
import edu.kis.powp.jobs2d.drivers.AbstractDecorator;
import edu.kis.powp.jobs2d.drivers.VisitableJob2dDriver;
import edu.kis.powp.jobs2d.drivers.adapter.LineDriverAdapter;

public class SimpleDriverCountingVisitor implements DriverVisitor {
    private int total;

    @Override
    public void visit(ComplexDriver driver) {
        for (VisitableJob2dDriver children : driver.getChildren()) {
            children.accept(this);
        }
        total++;
    }

    @Override
    public void visit(InformativeLoggerDriver driver) {
        total++;
    }

    @Override
    public void visit(LineDriverAdapter driver) {
        total++;
    }

    @Override
    public void visit(AbstractDecorator decorator) {
        decorator.getDriver().accept(this);
        total++;
    }

    public int getTotal() {
        return total;
    }

    public void reset() {
        total = 0;
    }
}
