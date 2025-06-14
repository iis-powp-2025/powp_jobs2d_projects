package edu.kis.powp.jobs2d;

import edu.kis.powp.jobs2d.canva.shapes.CanvaShape;
import edu.kis.powp.jobs2d.canva.visitor.CommandWithinShapeVisitor;
import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.features.CommandsFeature;
import edu.kis.powp.jobs2d.features.WorkspaceFeature;

import java.util.List;
import java.util.logging.Logger;

public class GetCommandContainmentInCanvasListTest {
    private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public static void execute() {
        DriverCommand currentCommand = CommandsFeature.getDriverCommandManager().getCurrentCommand();
        CanvaShape shape = WorkspaceFeature.getWorkspaceManager().getCurrentCanvaShape();
        if (currentCommand == null) {
            System.out.println("No command loaded.");
            return;
        }
        CommandWithinShapeVisitor visitor = new CommandWithinShapeVisitor(shape);
        currentCommand.accept(visitor);
        List<Boolean> res = visitor.getContainsList();
        logger.info(res.toString());
        visitor.reset();
    }
}
