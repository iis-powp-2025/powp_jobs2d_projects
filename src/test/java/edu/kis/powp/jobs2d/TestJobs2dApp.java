package edu.kis.powp.jobs2d;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.kis.legacy.drawer.panel.DrawPanelController;
import edu.kis.legacy.drawer.shape.LineFactory;
import edu.kis.powp.appbase.Application;
import edu.kis.powp.jobs2d.command.gui.CommandManagerWindow;
import edu.kis.powp.jobs2d.command.gui.CommandManagerWindowCommandChangeObserver;
import edu.kis.powp.jobs2d.drivers.InformativeLoggerDriver;
import edu.kis.powp.jobs2d.drivers.adapter.LineDriverAdapter;
import edu.kis.powp.jobs2d.drivers.canva.WorkspaceCanva;
import edu.kis.powp.jobs2d.drivers.canva.shapes.A4FormatCanva;
import edu.kis.powp.jobs2d.drivers.canva.shapes.CircularCanva;
import edu.kis.powp.jobs2d.drivers.canva.shapes.RectangleCanva;
import edu.kis.powp.jobs2d.drivers.canva.shapes.CanvaShape;
import edu.kis.powp.jobs2d.events.SelectLoadSecretCommandOptionListener;
import edu.kis.powp.jobs2d.events.SelectRunCurrentCommandOptionListener;
import edu.kis.powp.jobs2d.events.SelectTestFigure2OptionListener;
import edu.kis.powp.jobs2d.events.SelectTestFigureOptionListener;
import edu.kis.powp.jobs2d.features.*;
import edu.kis.powp.jobs2d.transformations.FlipTransformationDecorator;
import edu.kis.powp.jobs2d.transformations.RotateTransformationDecorator;
import edu.kis.powp.jobs2d.transformations.ScaleTransformationDecorator;

public class TestJobs2dApp {
    private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    /**
     * Setup test concerning preset figures in context.
     *
     * @param application Application context.
     */
    private static void setupPresetTests(Application application) {
        SelectTestFigureOptionListener selectTestFigureOptionListener = new SelectTestFigureOptionListener(
                DriverFeature.getDriverManager());
        SelectTestFigure2OptionListener selectTestFigure2OptionListener = new SelectTestFigure2OptionListener(
                DriverFeature.getDriverManager());

        application.addTest("Figure Joe 1", selectTestFigureOptionListener);
        application.addTest("Figure Joe 2", selectTestFigure2OptionListener);
    }

    /**
     * Setup test using driver commands in context.
     *
     * @param application Application context.
     */
    private static void setupCommandTests(Application application) {
        application.addTest("Load secret command", new SelectLoadSecretCommandOptionListener());

        application.addTest("Run command", new SelectRunCurrentCommandOptionListener(DriverFeature.getDriverManager()));

    }

    /**
     * Setup driver manager, and set default Job2dDriver for application.
     *
     * @param application Application context.
     */
    private static void setupDrivers(Application application) {
        Job2dDriver loggerDriver = new InformativeLoggerDriver();
        DriverFeature.addDriver("Logger driver", loggerDriver);

        DrawPanelController drawerController = DrawerFeature.getDrawerController();
        Job2dDriver driver = new LineDriverAdapter(drawerController, LineFactory.getBasicLine(), "basic");
        DriverFeature.addDriver("Line Simulator", driver);
        DriverFeature.getDriverManager().setCurrentDriver(driver);

        driver = new LineDriverAdapter(drawerController, LineFactory.getSpecialLine(), "special");
        DriverFeature.addDriver("Special line Simulator", driver);

        driver = new LineDriverAdapter(drawerController, LineFactory.getBasicLine(), "special");
        driver = new RotateTransformationDecorator(driver,45);
        driver = new FlipTransformationDecorator(driver,true,false);
        DriverFeature.addDriver("Rotated and flipped horizontally line Simulator", driver);

        driver = new LineDriverAdapter(drawerController, LineFactory.getSpecialLine(), "special");
        driver = new ScaleTransformationDecorator(driver,2,2);
        driver = new FlipTransformationDecorator(driver,false,true);
        DriverFeature.addDriver("Scaled and flipped vertically special line Simulator", driver);
    }

    private static void setupWorkspaces(Application application) {
        DrawPanelController drawerController = DrawerFeature.getDrawerController();

        Job2dDriver lineForShapeCanva = new LineDriverAdapter(drawerController, LineFactory.getBasicLine(), "basic");
        CanvaShape boundRectangle = new RectangleCanva(400, 400);
        WorkspaceCanva rectangleCanvaDriver = new WorkspaceCanva(lineForShapeCanva, boundRectangle);
        WorkspaceFeature.addWorkspaceShape("Rectangle canvas", rectangleCanvaDriver);

        CanvaShape boundA4Format = new A4FormatCanva();
        WorkspaceCanva a4FormatCanvaDriver = new WorkspaceCanva(lineForShapeCanva, boundA4Format);
        WorkspaceFeature.addWorkspaceShape("A4 format canvas", a4FormatCanvaDriver);


        CanvaShape boundCircular = new CircularCanva(200);
        WorkspaceCanva circularCanvaDriver = new WorkspaceCanva(lineForShapeCanva, boundCircular);
        WorkspaceFeature.addWorkspaceShape("My Circular canvas", circularCanvaDriver);


    }

    private static void setupWindows(Application application) {

        CommandManagerWindow commandManager = new CommandManagerWindow(CommandsFeature.getDriverCommandManager());
        application.addWindowComponent("Command Manager", commandManager);

        CommandManagerWindowCommandChangeObserver windowObserver = new CommandManagerWindowCommandChangeObserver(
                commandManager);
        CommandsFeature.getDriverCommandManager().getChangePublisher().addSubscriber(windowObserver);
    }

    /**
     * Setup menu for adjusting logging settings.
     *
     * @param application Application context.
     */
    private static void setupLogger(Application application) {

        application.addComponentMenu(Logger.class, "Logger", 0);
        application.addComponentMenuElement(Logger.class, "Clear log",
                (ActionEvent e) -> application.flushLoggerOutput());
        application.addComponentMenuElement(Logger.class, "Fine level", (ActionEvent e) -> logger.setLevel(Level.FINE));
        application.addComponentMenuElement(Logger.class, "Info level", (ActionEvent e) -> logger.setLevel(Level.INFO));
        application.addComponentMenuElement(Logger.class, "Warning level",
                (ActionEvent e) -> logger.setLevel(Level.WARNING));
        application.addComponentMenuElement(Logger.class, "Severe level",
                (ActionEvent e) -> logger.setLevel(Level.SEVERE));
        application.addComponentMenuElement(Logger.class, "OFF logging", (ActionEvent e) -> logger.setLevel(Level.OFF));
    }

    private static void setupMouseHandler(Application application) {
        new ClicksConverter(application.getFreePanel());
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                Application app = new Application("Jobs 2D");
                DrawerFeature.setupDrawerPlugin(app);
                CommandsFeature.setupCommandManager();

                DriverFeature.setupDriverPlugin(app);
                setupDrivers(app);

                WorkspaceFeature.setupWorkspacePlugin(app);
                setupWorkspaces(app);

                setupPresetTests(app);
                setupCommandTests(app);

                setupLogger(app);
                setupWindows(app);
                setupMouseHandler(app);

                app.setVisibility(true);
            }
        });
    }

}
