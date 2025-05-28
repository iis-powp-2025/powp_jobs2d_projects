package edu.kis.powp.jobs2d.drivers;

import edu.kis.legacy.drawer.shape.LineFactory;
import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.canva.ClippingJobs2dDriverDecorator;
import edu.kis.powp.jobs2d.drivers.adapter.LineDriverAdapter;
import edu.kis.powp.jobs2d.canva.shapes.CanvaShape;
import edu.kis.powp.jobs2d.drivers.observers.WorkspaceDriverChangeObserver;
import edu.kis.powp.jobs2d.features.DrawerFeature;
import edu.kis.powp.jobs2d.features.DriverFeature;

/**
 * Manages the current drawing workspace by handling the canvas shape and its visual representation.
 * <p>
 * This class allows setting a {@link CanvaShape} to represent the workspace area,
 * and automatically renders its border using a special {@link Job2dDriver}.
 */
public class WorkspaceManager {
    private final ClippingJobs2dDriverDecorator clipper;
    private final Job2dDriver borderDriver;

    /**
     * Constructs a new {@code WorkspaceManager} with a preconfigured border drawing driver.
     * <p>
     * The border driver uses a dotted line and is labeled "border".
     */
    public WorkspaceManager() {
        borderDriver = new LineDriverAdapter(
                DrawerFeature.getDrawerController(),
                LineFactory.getDottedLine(),
                "border"
        );
        clipper = new ClippingJobs2dDriverDecorator(DriverFeature.getDriverManager().getCurrentDriver());
    }

    /**
     * Sets the current workspace canvas shape and draws its border.
     * <p>
     * This method is synchronized to prevent concurrent modifications to the workspace shape.
     *
     * @param canvaShape the {@link CanvaShape} to be used as the current workspace area
     */
    public synchronized void setWorkspaceCanvaShape(CanvaShape canvaShape) {
        clipper.setCanvaShape(canvaShape);
        canvaShape.draw(borderDriver);
    }

    /**
     * Returns the clipping decorator used to enforce drawing within the canvas shape.
     * <p>
     * This driver can be registered as the main driver to ensure clipping is respected
     * during user or programmatic drawing operations.
     *
     * @return the {@link ClippingJobs2dDriverDecorator} used for clipping logic
     */
    public ClippingJobs2dDriverDecorator getClipper() { return clipper; }
}
