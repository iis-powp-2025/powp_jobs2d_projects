package edu.kis.powp.jobs2d.command.entries;

public class JsonCommandEntry implements CommandEntry {
    private String commandName;
    private int x;
    private int y;

    @Override
    public String getCommandName() { return commandName; }
    public void setCommandName(String commandName) { this.commandName = commandName; }

    @Override
    public int getX() { return x; }
    public void setX(int x) { this.x = x; }

    @Override
    public int getY() { return y; }
    public void setY(int y) { this.y = y; }
}
