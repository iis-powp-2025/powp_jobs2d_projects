package edu.kis.powp.jobs2d.command;

public class SimpleCountingVisitor implements DriverCommandVisitor {

    public int count(DriverCommand command) {
        return command.accept(this);
    }

    @Override
    public int visit(SetPositionCommand command) {
        return 1;
    }

    @Override
    public int visit(OperateToCommand command) {
        return 1;
    }

    @Override
    public int visit(ICompoundCommand compoundCommand) {
        int total = 0;
        for (DriverCommand subCommand : compoundCommand) {
            total += subCommand.accept(this);
        }
        return total;
    }
}