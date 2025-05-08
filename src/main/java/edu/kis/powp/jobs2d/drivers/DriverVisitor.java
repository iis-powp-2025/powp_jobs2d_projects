package edu.kis.powp.jobs2d.drivers;

import edu.kis.powp.jobs2d.drivers.adapter.LineDriverAdapter;
import edu.kis.powp.jobs2d.drivers.monitoring.DriverMonitorDecorator;
import edu.kis.powp.jobs2d.transformations.RotateTransformationDecorator;
import edu.kis.powp.jobs2d.transformations.ScaleTransformationDecorator;

public interface DriverVisitor {
    void visit(ComplexDriver dirver);
    void visit(InformativeLoggerDriver driver);
    void visit(LineDriverAdapter driver);
    void visit(RotateTransformationDecorator decorator);
    void visit(ScaleTransformationDecorator decorator);
    void visit(DriverMonitorDecorator decorator);
}
