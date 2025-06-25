package edu.kis.powp.jobs2d.command.entries;

public interface CommandEntry {

    int getX();
    void setX(int x);

    int getY();
    void setY(int y);

    String getCommandName();
    void setCommandName(String commandName);

}
