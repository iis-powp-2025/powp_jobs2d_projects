package edu.kis.powp.jobs2d.drivers.visitors;

import edu.kis.powp.jobs2d.drivers.ComplexDriver;
import edu.kis.powp.jobs2d.drivers.InformativeLoggerDriver;
import edu.kis.powp.jobs2d.drivers.VisitableJob2dDriver;
import edu.kis.powp.jobs2d.drivers.adapter.LineDriverAdapter;
import edu.kis.powp.jobs2d.drivers.monitoring.DriverMonitorDecorator;
import edu.kis.powp.jobs2d.transformations.RotateTransformationDecorator;
import edu.kis.powp.jobs2d.transformations.ScaleTransformationDecorator;

public class SimpleDriverCountingVisitor implements DriverVisitor {
    private int total;

    @Override
    public void visit(ComplexDriver driver) {
        total = 0;
        for (VisitableJob2dDriver children : driver.getChildren()) {
            children.accept(this);
            total++;
        }
    }

    @Override
    public void visit(InformativeLoggerDriver driver) {
        total = 1;
    }

    @Override
    public void visit(LineDriverAdapter driver) {
        total = 1;
    }

    @Override
    public void visit(RotateTransformationDecorator decorator) {
        total = 0;
        decorator.accept(this);
        ++total;
    }

    @Override
    public void visit(ScaleTransformationDecorator decorator) {
        total = 0;
        decorator.accept(this);
        ++total;
    }

    @Override
    public void visit(DriverMonitorDecorator decorator) {
        total = 0;
        decorator.accept(this);
        ++total;
    }

    public int getTotal() {
        return total;
    }
}
