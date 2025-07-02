package edu.kis.powp.jobs2d;

import edu.kis.legacy.drawer.panel.DrawPanelController;
import edu.kis.legacy.drawer.shape.LineFactory;
import edu.kis.powp.appbase.Application;
import edu.kis.powp.jobs2d.canva.factories.RectangleCanvaFactory;
import edu.kis.powp.jobs2d.canva.shapes.CanvaShape;
import edu.kis.powp.jobs2d.canva.shapes.CircularCanva;
import edu.kis.powp.jobs2d.canva.shapes.RectangleCanva;
import edu.kis.powp.jobs2d.command.manager.CommandParsingStrategiesManager;
import edu.kis.powp.jobs2d.command.gui.CommandManagerWindow;
import edu.kis.powp.jobs2d.command.gui.CommandManagerWindowCommandChangeObserver;
import edu.kis.powp.jobs2d.command.manager.CommandHistoryManager;
import edu.kis.powp.jobs2d.command.strategy.CsvParsingStrategy;
import edu.kis.powp.jobs2d.command.strategy.JsonParsingStrategy;
import edu.kis.powp.jobs2d.command.strategy.ParsingStrategy;
import edu.kis.powp.jobs2d.drivers.ComplexDriver;
import edu.kis.powp.jobs2d.drivers.InformativeLoggerDriver;
import edu.kis.powp.jobs2d.drivers.RealTimeDecoratorDriver;
import edu.kis.powp.jobs2d.drivers.VisitableJob2dDriver;
import edu.kis.powp.jobs2d.drivers.adapter.LineDriverAdapter;
import edu.kis.powp.jobs2d.events.*;
import edu.kis.powp.jobs2d.features.*;
import edu.kis.powp.jobs2d.plugin.FeatureManager;
import edu.kis.powp.jobs2d.transformations.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


public class TestJobs2dApp {
    private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    /**
     * Setup test concerning preset figures in context.
     *
     * @param application Application context.
     */
    private static void setupPresetTests(Application application) {
        SelectTestFigureOptionListener selectTestFigureOptionListener = new SelectTestFigureOptionListener(DriverFeature.getDriverManager());
        SelectTestFigure2OptionListener selectTestFigure2OptionListener = new SelectTestFigure2OptionListener(DriverFeature.getDriverManager());

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
        application.addTest("Load notSecret command", new SelectLoadNotSoSecretCommandOptionListener());

        application.addTest("Run command", new SelectRunCurrentCommandOptionListener(DriverFeature.getDriverManager()));

        application.addTest("Count subcommands", (e) -> CountCommandsTest.execute());
        application.addTest("Count drivers", (e) -> CountDriversTest.execute());

        application.addTest("Check if inside canvas", (e) -> CheckIfInsideCanvasTest.execute());
        application.addTest("Show containment list", (e) -> GetCommandContainmentInCanvasListTest.execute());


        application.addTest("Transform: Rotate 45", new TransformCurrentCommandOptionListener(new RotateTransformation(45)));
        application.addTest("Transform: Scale 2x", new TransformCurrentCommandOptionListener(new ScaleTransformation(2, 2)));
        application.addTest("Transform: Move by (50, 25)", new TransformCurrentCommandOptionListener(new TranslateTransformation(50, 25)));
        application.addTest("Transform: Flip Horizontal", new TransformCurrentCommandOptionListener(new FlipTransformation(true, false)));
        application.addTest("Transform: Flip Vertical", new TransformCurrentCommandOptionListener(new FlipTransformation(false, true)));
    }


    /**
     * Setup driver manager, and set default Job2dDriver for application.
     *
     * @param application Application context.
     */
    private static void setupDrivers(Application application) {
        VisitableJob2dDriver loggerDriver = new InformativeLoggerDriver();
        DriverFeature.addDriver("Logger driver", loggerDriver);

        DrawPanelController drawerController = DrawerFeature.getDrawerController();
        VisitableJob2dDriver basicLineDriver = new LineDriverAdapter(drawerController, LineFactory.getBasicLine(), "basic");
        DriverFeature.addDriver("Line Simulator", basicLineDriver);
        DriverFeature.getDriverManager().setCurrentDriver(basicLineDriver);

        ComplexDriver complexDriver = new ComplexDriver();
        complexDriver.add(loggerDriver);
        complexDriver.add(basicLineDriver);

        DriverFeature.addDriver("Line & Logger (Composite)", complexDriver);

        DriverFeature.getDriverManager().setCurrentDriver(basicLineDriver);

        VisitableJob2dDriver driver = new LineDriverAdapter(drawerController, LineFactory.getSpecialLine(), "special");
        DriverFeature.addDriver("Special line Simulator", driver);

        TransformationComposite composite = new TransformationComposite();
        composite.addTransformation(new RotateTransformation(45));
        composite.addTransformation(new FlipTransformation(true, false));

        driver = new LineDriverAdapter(drawerController, LineFactory.getBasicLine(), "special");
        driver = new TransformationDriverDecorator(driver, composite);
        DriverFeature.addDriver("Rotated and flipped horizontally line Simulator", driver);

        TransformationComposite composite2 = new TransformationComposite();
        composite2.addTransformation(new ScaleTransformation(2, 2));
        composite2.addTransformation(new FlipTransformation(false, true));

        driver = new LineDriverAdapter(drawerController, LineFactory.getSpecialLine(), "special");
        driver = new TransformationDriverDecorator(driver, composite2);
        DriverFeature.addDriver("Scaled and flipped vertically special line Simulator", driver);

        driver = new RealTimeDecoratorDriver(new LineDriverAdapter(drawerController, LineFactory.getBasicLine(), "basic"), application.getFreePanel(), 30, 10);
        DriverFeature.addDriver("Basic line Simulator with real time drawing", driver);
        driver = new RealTimeDecoratorDriver(new LineDriverAdapter(drawerController, LineFactory.getSpecialLine(), "special"), application.getFreePanel(), 30, 10);
        DriverFeature.addDriver("Special line Simulator with real time drawing", driver);
    }

    private static void setupWorkspaces() {
        Map<String, CanvaShape> workspaceShapes = new HashMap<>();
        workspaceShapes.put("Rectangle canvas", new RectangleCanva(400, 400));
        workspaceShapes.put("A4 format canvas", RectangleCanvaFactory.getVerticalA4Canva());
        workspaceShapes.put("My Circular canvas", new CircularCanva(200));
        workspaceShapes.put("Letter canvas", RectangleCanvaFactory.getLetterCanva());
        workspaceShapes.put("A3 canvas", RectangleCanvaFactory.getVerticalA3Canva());

        for (Map.Entry<String, CanvaShape> entry : workspaceShapes.entrySet()) {
            WorkspaceFeature.addWorkspaceShape(entry.getKey(), entry.getValue());
        }
    }

    private static void setupWindows(Application application) {

        CommandHistoryManager commandHistoryManager = new CommandHistoryManager(CommandsFeature.getDriverCommandManager());

        List<ParsingStrategy> parsingStrategies = new ArrayList<>();
        parsingStrategies.add(new JsonParsingStrategy());
        parsingStrategies.add(new CsvParsingStrategy());
        CommandParsingStrategiesManager commandParsingStrategiesManager = new CommandParsingStrategiesManager(parsingStrategies);

        CommandManagerWindow commandManager = new CommandManagerWindow(CommandsFeature.getDriverCommandManager(), commandHistoryManager, commandParsingStrategiesManager);
        application.addWindowComponent("Command Manager", commandManager);

        CommandManagerWindowCommandChangeObserver windowObserver = new CommandManagerWindowCommandChangeObserver(commandManager);

        CommandsFeature.getDriverCommandManager().getChangePublisher().addSubscriber(windowObserver);
        CommandsFeature.getDriverCommandManager().getChangePublisher().addSubscriber(commandHistoryManager);
    }

    /**
     * Setup menu for adjusting logging settings.
     *
     * @param application Application context.
     */
    private static void setupLogger(Application application) {

        application.addComponentMenu(Logger.class, "Logger", 0);
        application.addComponentMenuElement(Logger.class, "Clear log", (ActionEvent e) -> application.flushLoggerOutput());
        application.addComponentMenuElement(Logger.class, "Fine level", (ActionEvent e) -> logger.setLevel(Level.FINE));
        application.addComponentMenuElement(Logger.class, "Info level", (ActionEvent e) -> logger.setLevel(Level.INFO));
        application.addComponentMenuElement(Logger.class, "Warning level", (ActionEvent e) -> logger.setLevel(Level.WARNING));
        application.addComponentMenuElement(Logger.class, "Severe level", (ActionEvent e) -> logger.setLevel(Level.SEVERE));
        application.addComponentMenuElement(Logger.class, "OFF logging", (ActionEvent e) -> logger.setLevel(Level.OFF));
    }

    private static void setupMouseHandler(Application application) {
        new ClicksConverter(application.getFreePanel());
    }

    public static void setup(Application application) {
        FeatureManager.registerFeature(new DriverFeature());
        FeatureManager.registerFeature(new DrawerFeature());
        FeatureManager.registerFeature(new WorkspaceFeature());
        FeatureManager.registerFeature(new CommandsFeature());

        FeatureManager.registerFeature(new DriverMonitorFeature());
        FeatureManager.initializeAll(application);
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                Application app = new Application("Jobs 2D");
                setup(app);
                setupDrivers(app);

                setupWorkspaces();

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
