package edu.kis.powp.jobs2d.command;

import edu.kis.powp.jobs2d.Job2dDriver;

/**
 * Implementation of Job2dDriverCommand for operateTo command functionality.
 */
public class OperateToCommand implements DriverCommand {

    private final int posX, posY;

    public OperateToCommand(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    @Override
    public void execute(Job2dDriver driver) {
        driver.operateTo(posX, posY);
    }

    @Override
    public DriverCommand copy(){
        return new OperateToCommand(this.posX,this.posY);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OperateToCommand)) return false;

        OperateToCommand second = (OperateToCommand) o;

        if (posX != second.posX) return false;
        return posY == second.posY;
    }

    public int getX() {
        return posX;
    }

    public int getY() {
        return posY;
    }

    @Override
    public void accept(DriverCommandVisitor visitor) {
        visitor.visit(this);
    }
}