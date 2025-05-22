package edu.kis.powp.jobs2d.drivers.visitors;

import edu.kis.powp.jobs2d.drivers.ComplexDriver;
import edu.kis.powp.jobs2d.drivers.InformativeLoggerDriver;
import edu.kis.powp.jobs2d.drivers.AbstractDecorator;
import edu.kis.powp.jobs2d.drivers.adapter.LineDriverAdapter;

public interface DriverVisitor {
    void visit(ComplexDriver dirver);

    void visit(InformativeLoggerDriver driver);

    void visit(LineDriverAdapter driver);

    void visit(AbstractDecorator decorator);
}
