package edu.kis.powp.jobs2d.command;

import edu.kis.powp.jobs2d.Job2dDriver;

/**
 * DriverCommand interface.
 */
public interface DriverCommand {

    /**
     * Execute command on driver.
     * 
     * @param driver driver.
     */
    void execute(Job2dDriver driver);
  
    /**
     * Creates a new deep copy of itself
     */
    DriverCommand copy();
  
    /**
     * Compares an object
     * @param o object to compare
     */
    boolean equals(Object o);
    /**
     * Accepts a visitor
     * @param visitor visitor to accept
     */
    void accept(DriverCommandVisitor visitor);
}
