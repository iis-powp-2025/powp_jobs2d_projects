package edu.kis.powp.jobs2d.command;

public interface DriverCommandVisitor {
    int visit(SetPositionCommand command);
    int visit(OperateToCommand command);
    int visit(ICompoundCommand command);
}