package edu.kis.powp.jobs2d.command;

import java.util.Iterator;

/**
 * Interface extending Job2dDriverCommand to execute more than one command.
 */
public interface ICompoundCommand extends DriverCommand, Iterable<DriverCommand> {
    default void accept(DriverCommandVisitor visitor) {
        visitor.visit(this);
    }
}
