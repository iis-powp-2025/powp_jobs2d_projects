package edu.kis.powp.jobs2d.command.visitor;

import edu.kis.powp.jobs2d.command.*;

public class SimpleDriverCommandCountingVisitor implements DriverCommandVisitor {
    private int total;

    @Override
    public void visit(SetPositionCommand command) {
        total = 1;
    }

    @Override
    public void visit(OperateToCommand command) {
        total = 1;
    }

    public int getTotal() {
        return total;
    }

    @Override
    public void visit(ICompoundCommand compoundCommand) {
        int total = 0;
        for (DriverCommand subCommand : compoundCommand) {
            subCommand.accept(this);
            total += this.total;
        }

        this.total = total;
    }
}