package edu.kis.powp.jobs2d.drivers;

import edu.kis.powp.jobs2d.canva.shapes.CanvaShape;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelectWorkspaceMenuOptionListener implements ActionListener {
    private final CanvaShape canvaShape;
    private WorkspaceManager workspaceManager;

    public SelectWorkspaceMenuOptionListener(CanvaShape canvaShape, WorkspaceManager workspaceManager) {
        this.canvaShape = canvaShape;
        this.workspaceManager = workspaceManager;
    }

    @Override
    public void actionPerformed(ActionEvent e) { workspaceManager.setWorkspaceCanvaShape(canvaShape); }
}
