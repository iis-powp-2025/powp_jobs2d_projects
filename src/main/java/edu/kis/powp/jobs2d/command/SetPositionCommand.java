package edu.kis.powp.jobs2d.command;

import edu.kis.powp.jobs2d.Job2dDriver;

/**
 * Implementation of Job2dDriverCommand for setPosition command functionality.
 */
public class SetPositionCommand implements DriverCommand {

    private final int posX, posY;

    public SetPositionCommand(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public int getX() {
        return posX;
    }

    public int getY() {
        return posY;
    }

    @Override
    public String toString() {
        return String.format("SetPositionCommand (x=%d, y=%d)", posX, posY);
    }

    @Override
    public void execute(Job2dDriver driver) {
        driver.setPosition(posX, posY);
    }

    @Override
    public DriverCommand copy() {
        return new SetPositionCommand(this.posX, this.posY);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof SetPositionCommand))
            return false;

        SetPositionCommand second = (SetPositionCommand) o;

        if (posX != second.posX)
            return false;
        return posY == second.posY;
    }

    @Override
    public void accept(DriverCommandVisitor visitor) {
        visitor.visit(this);
    }
}
