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
     * Zwraca listę wyników sprawdzania zawierania punktów.
     */
    public List<Boolean> getContainsList() {
        return containsList;
    }

    /**
     * Zwraca true, jeśli wszystkie komendy były zawarte w CanvaShape.
     */
    public boolean allContained() {
        return containsList.stream().allMatch(Boolean::booleanValue);
    }

    /**
     * Zwraca true, jeśli przynajmniej jedna komenda była zawarta w CanvaShape.
     */
    public boolean anyContained() {
        return containsList.stream().anyMatch(Boolean::booleanValue);
    }
}
