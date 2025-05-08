package edu.kis.powp.jobs2d.command.visitor;

import edu.kis.powp.jobs2d.command.*;
import edu.kis.powp.jobs2d.transformations.*;

import java.util.ArrayList;
import java.util.List;

public class DriverCommandTransformVisitor implements DriverCommandVisitor {
    private final List<DriverCommand> transformedCommands = new ArrayList<>();
    private final TransformationDecorator transformer = new TransformationDecorator();

    public void addTransformation(PointTransformation transformation) {
        transformer.addTransformation(transformation);
    }

    public List<DriverCommand> getTransformedCommands() {
        return transformedCommands;
    }

    @Override
    public void visit(SetPositionCommand command) {
        int[] coords = transformer.transform(command.getX(), command.getY());
        transformedCommands.add(new SetPositionCommand(coords[0], coords[1]));
    }

    @Override
    public void visit(OperateToCommand command) {
        int[] coords = transformer.transform(command.getX(), command.getY());
        transformedCommands.add(new OperateToCommand(coords[0], coords[1]));
    }

    @Override
    public void visit(ICompoundCommand command) {
        for (DriverCommand c : command) {
            c.accept(this);
        }
    }
}
