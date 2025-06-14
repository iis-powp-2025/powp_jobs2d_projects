package edu.kis.powp.jobs2d.canva.visitor;

import edu.kis.powp.jobs2d.canva.shapes.CanvaShape;
import edu.kis.powp.jobs2d.command.*;

import java.util.ArrayList;
import java.util.List;

public class CommandWithinShapeVisitor implements DriverCommandVisitor {
    private final CanvaShape canvaShape;
    private final List<Boolean> containsList;

    public CommandWithinShapeVisitor(CanvaShape canvaShape) {
        this.canvaShape = canvaShape;
        this.containsList = new ArrayList<>();
    }

    @Override
    public void visit(SetPositionCommand command) {
        boolean result = canvaShape.contains(command.getPosX(), command.getPosY());
        containsList.add(result);
    }

    @Override
    public void visit(OperateToCommand command) {
        boolean result = canvaShape.contains(command.getPosX(), command.getPosY());
        containsList.add(result);
    }

    @Override
    public void visit(ICompoundCommand command) {
        for (DriverCommand subCommand : command) {
            subCommand.accept(this);
        }
    }

    /**
     * Clears the internal list that stores the results.
     */
    public void reset() {
        containsList.clear();
    }

    /**
     * Returns the list of boolean values indicating the result of point containment checks.
     *
     * @return a list of booleans representing containment results for each checked point
     */
    public List<Boolean> getContainsList() {
        return containsList;
    }

    /**
     * Checks whether all commands were contained within the CanvaShape.
     *
     * @return {@code true} if all points were contained, {@code false} otherwise
     */
    public boolean allContained() {
        return containsList.stream().allMatch(Boolean::booleanValue);
    }

    /**
     * Checks whether at least one command was contained within the CanvaShape.
     *
     * @return {@code true} if at least one point was contained, {@code false} otherwise
     */
    public boolean anyContained() {
        return containsList.stream().anyMatch(Boolean::booleanValue);
    }

}
