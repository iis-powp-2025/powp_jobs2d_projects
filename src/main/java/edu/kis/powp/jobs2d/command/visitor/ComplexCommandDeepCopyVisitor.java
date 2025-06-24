package edu.kis.powp.jobs2d.command.visitor;

import edu.kis.powp.jobs2d.command.*;

public class ComplexCommandDeepCopyVisitor implements DriverCommandVisitor {
    private DriverCommand commandCopy;

    public DriverCommand getCopy() {
        return commandCopy;
    }

    @Override
    public void visit(OperateToCommand command) {
        commandCopy = new OperateToCommand(command.getX(), command.getY());
    }

    @Override
    public void visit(SetPositionCommand command) {
        commandCopy = new SetPositionCommand(command.getX(), command.getY());
    }

    @Override
    public void visit(ICompoundCommand command) {
        ComplexCommand exactCopy = new ComplexCommand();
        for (DriverCommand subCommand : command) {
            subCommand.accept(this);
            exactCopy.addCommand(commandCopy);
        }

        commandCopy = exactCopy;
    }
}