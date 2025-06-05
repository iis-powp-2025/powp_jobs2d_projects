package edu.kis.powp.jobs2d.features;

import edu.kis.powp.appbase.Application;

import edu.kis.powp.jobs2d.canva.ClippingJobs2dDriverDecorator;
import edu.kis.powp.jobs2d.drivers.SelectWorkspaceMenuOptionListener;
import edu.kis.powp.jobs2d.drivers.WorkspaceManager;
import edu.kis.powp.jobs2d.canva.shapes.CanvaShape;
import edu.kis.powp.jobs2d.drivers.observers.WorkspaceDriverChangeObserver;
import edu.kis.powp.jobs2d.plugin.FeaturePlugin;

import java.util.logging.Logger;

/**
 * Provides functionality for integrating workspace shape selection into the application UI.
 * <p>
 * This class is responsible for setting up a "Workspaces" menu and allowing the user to add and select
 * predefined {@link CanvaShape} instances through menu options. It holds a shared {@link WorkspaceManager}
 * instance to manage the currently active workspace shape.
 */
public class WorkspaceFeature implements FeaturePlugin {
    private static Application app;
    private static WorkspaceManager workspaceManager;
    private static ClippingJobs2dDriverDecorator clipper;


    /**
     * Initializes the workspace plugin by adding a "Workspaces" component menu to the application.
     *
     * @param application the main {@link Application} instance to which the plugin is attached
     */
    public static void setupWorkspacePlugin(Application application) {
        clipper = new ClippingJobs2dDriverDecorator(DriverFeature.getDriverManager().getCurrentDriver());
        workspaceManager = new WorkspaceManager(clipper);
        app = application;
        app.addComponentMenu(WorkspaceFeature.class, "Workspaces");

        WorkspaceDriverChangeObserver observer = new WorkspaceDriverChangeObserver(clipper);
        DriverFeature.getDriverManager().getChangePublisher().addSubscriber(observer);

        Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
        app.addComponentMenuElementWithCheckBox(WorkspaceFeature.class, "Toggle cutting lines", e -> {
            clipper.toggleClipping();
            logger.info("Cutting lines: " + (clipper.isClipping() ? "ENABLED" : "DISABLED"));
        },false);
    }

    @Override
    public void setup(Application application) {
        setupWorkspacePlugin(application);
    }

    /**
     * Adds a selectable workspace shape option to the "Workspaces" menu.
     * When selected, the shape will be applied to the workspace.
     *
     * @param name  the display name of the menu option
     * @param shape the {@link CanvaShape} to apply when the menu option is selected
     */
    public static void addWorkspaceShape(String name, CanvaShape shape) {
        SelectWorkspaceMenuOptionListener listener = new SelectWorkspaceMenuOptionListener(shape, workspaceManager);
        app.addComponentMenuElement(WorkspaceFeature.class, name, listener);
    }

    public static WorkspaceManager getWorkspaceManager() { return workspaceManager; }

    /**
     * Checks whether the "cut outstanding lines" feature is currently enabled.
     * This flag is toggled by the corresponding menu item in the "Workspaces" menu.
     *
     * @return {@code true} if cutting outstanding lines is enabled; {@code false} otherwise
     */
    public static boolean isCutOutstandingLinesEnabled() {
        return clipper.isClipping();
    }

}
