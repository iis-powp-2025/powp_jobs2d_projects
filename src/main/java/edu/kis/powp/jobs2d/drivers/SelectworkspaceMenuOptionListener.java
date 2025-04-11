package edu.kis.powp.jobs2d.drivers;

import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.drivers.canva.WorkspaceCanva;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelectworkspaceMenuOptionListener implements ActionListener {
    private DriverManager driverManager;
    private Job2dDriver driver = null;
    private WorkspaceCanva canvas = null;

    public SelectworkspaceMenuOptionListener(WorkspaceCanva canva, DriverManager driverManager) {
        this.driverManager = driverManager;
        this.driver = canva;
        this.canvas = canva;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        driverManager.setCurrentDriver(driver);
        this.canvas.drawWorkspaceBoundary();
    }
}
