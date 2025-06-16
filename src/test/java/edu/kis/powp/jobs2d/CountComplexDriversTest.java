package edu.kis.powp.jobs2d;

import edu.kis.legacy.drawer.shape.LineFactory;
import edu.kis.powp.jobs2d.drivers.ComplexDriver;
import edu.kis.powp.jobs2d.drivers.adapter.LineDriverAdapter;
import edu.kis.powp.jobs2d.drivers.visitors.SimpleDriverCountingVisitor;
import edu.kis.powp.jobs2d.features.DrawerFeature;

public class CountComplexDriversTest {
    public static void main(String[] args){
        // ComplexDriver -> (LineDriverAdapter, ComplexDriver -> (LineDriverAdapter, LineDriverAdapter))

        
        // |-ComplexDriver
        //    |-LineDriverAdapter
        //    |-ComplexDriver
        //        |-LineDriverAdapter
        //        |-LineDriverAdapter


        ComplexDriver complexDriver = new ComplexDriver();

        LineDriverAdapter lineDriverAdapter = new LineDriverAdapter(DrawerFeature.getDrawerController(), LineFactory.getBasicLine(), null);

        complexDriver.add(lineDriverAdapter);
        complexDriver.add(lineDriverAdapter);

        ComplexDriver complexDriver2 = new ComplexDriver();

        complexDriver2.add(lineDriverAdapter);
        complexDriver2.add(complexDriver);

        SimpleDriverCountingVisitor visitor = new SimpleDriverCountingVisitor();
        complexDriver2.accept(visitor);
        int count = visitor.getTotal();
        System.out.println("Total: " + count);
    }
}
