package edu.kis.powp.jobs2d.drivers;

import edu.kis.powp.jobs2d.drivers.adapter.LineDriverAdapter;
import edu.kis.powp.jobs2d.drivers.monitoring.DriverMonitorDecorator;
import edu.kis.powp.jobs2d.transformations.RotateTransformationDecorator;
import edu.kis.powp.jobs2d.transformations.ScaleTransformationDecorator;

public class SimpleDriverCountingVisitor implements DriverVisitor{
    private int total;

    @Override
    public void visit(ComplexDriver driver){
        total = driver.getChildrenSize();
    }

    @Override
    public void visit(InformativeLoggerDriver driver){
        total = 1;
    }

    @Override
    public void visit(LineDriverAdapter driver){
        total = 1;
    }

    @Override
    public void visit(RotateTransformationDecorator decorator){
        total = 1;
    }

    @Override
    public void visit(ScaleTransformationDecorator decorator){
        total = 1;
    }

    @Override
    public void visit(DriverMonitorDecorator decorator){
        total = 1;
    }

    public int getTotal(){
        return total;
    }
}
