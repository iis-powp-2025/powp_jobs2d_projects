package edu.kis.powp.jobs2d;

import edu.kis.powp.jobs2d.drivers.InformativeLoggerDriver;
import edu.kis.powp.jobs2d.magicpresets.FiguresJoe;

/**
 * Plotter test.
 * 
 * @author Dominik
 */
public class Job2dDriverTest {
    private static Job2dDriver driver = new InformativeLoggerDriver();

    /**
     * Driver test.
     */
    public static void main(String[] args) {
        FiguresJoe.figureScript1(driver);
    }
}
