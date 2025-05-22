package edu.kis.powp.jobs2d.events;

import java.util.ArrayList;
import java.util.List;
import edu.kis.powp.jobs2d.command.*;

public class StarCommandFactory {
    public static ImmutableComplexCommand createStarCommand() {
        List<DriverCommand> commands = new ArrayList<>();

        commands.add(new SetPositionCommand(0, 100));
        commands.add(new OperateToCommand(38, -31));
        commands.add(new OperateToCommand(-95, 31));
        commands.add(new OperateToCommand(95, 31));
        commands.add(new OperateToCommand(-38, -31));
        commands.add(new OperateToCommand(0, 100));

        return new ImmutableComplexCommand(commands);
    }
}
