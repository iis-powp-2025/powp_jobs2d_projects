package edu.kis.powp.jobs2d.command;

public interface DriverCommandVisitor {
    void visit(SetPositionCommand command);
    void visit(OperateToCommand command);
    void visit(ICompoundCommand command);
}