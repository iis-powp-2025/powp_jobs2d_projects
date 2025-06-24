package edu.kis.powp.jobs2d.command;

public class CommandTestFactory {
    public static ComplexCommand createDeeplyNestedCommand() {
        ComplexCommand inner1 = new ComplexCommand.Builder()
                .addCommand(new SetPositionCommand(0, 0))
                .addCommand(new OperateToCommand(5, 5))
                .build();

        ComplexCommand inner2 = new ComplexCommand.Builder()
                .addCommand(new SetPositionCommand(10, 10))
                .addCommand(new OperateToCommand(15, 15))
                .build();

        ComplexCommand midLevel = new ComplexCommand.Builder()
                .addCommand(new SetPositionCommand(20, 20))
                .addCommand(inner1)
                .addCommand(new OperateToCommand(25, 25))
                .addCommand(inner2)
                .addCommand(new OperateToCommand(30, 30))
                .build();

        return new ComplexCommand.Builder()
                .addCommand(new SetPositionCommand(100, 100))
                .addCommand(new OperateToCommand(110, 110))
                .addCommand(midLevel)
                .addCommand(new OperateToCommand(120, 120))
                .addCommand(createLeafCommandCluster())
                .build();
    }

    private static ComplexCommand createLeafCommandCluster() {
        return new ComplexCommand.Builder()
                .addCommand(new SetPositionCommand(200, 200))
                .addCommand(new OperateToCommand(210, 210))
                .addCommand(new OperateToCommand(220, 220))
                .build();
    }
}
