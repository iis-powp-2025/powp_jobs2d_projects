package edu.kis.powp.jobs2d.command;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class ComplexCommandTest {

    static boolean compareCommands(ComplexCommand cc1, ComplexCommand cc2) {
        if (cc1.size() != cc2.size()) {
            return false;
        }
        Iterator<DriverCommand> it1 = cc1.iterator();
        Iterator<DriverCommand> it2 = cc2.iterator();
        while (it1.hasNext() && it2.hasNext()) {
            if (!it1.next().equals(it2.next())) {
                return false;
            }
        }
        return true;
    }

    static void testIterator() {
        ComplexCommand.Builder builder1 = new ComplexCommand.Builder();
        builder1.addCommand(new SetPositionCommand(0, 0));
        builder1.addCommand(new OperateToCommand(10, 10));
        ComplexCommand command = builder1.build();

        ComplexCommand.Builder builder2 = new ComplexCommand.Builder();
        builder2.addCommand(new SetPositionCommand(0, 0));
        builder2.addCommand(new OperateToCommand(10, 10));
        ComplexCommand command2 = builder2.build();

        if (!compareCommands(command, command2)) {
            System.out.println("testIterator failed");
        } else {
            System.out.println("testIterator passed");
        }
    }

    static void testIsEmpty() {
        ComplexCommand.Builder builder = new ComplexCommand.Builder();
        ComplexCommand command = builder.build();

        if (!command.isEmpty()) {
            System.out.println("testIsEmpty failed");
        } else {
            ComplexCommand.Builder builder2 = new ComplexCommand.Builder();
            builder2.addCommand(new SetPositionCommand(0, 0));
            ComplexCommand command2 = builder2.build();

            if (command2.isEmpty()) {
                System.out.println("testIsEmpty failed");
            } else {
                System.out.println("testIsEmpty passed");
            }
        }
    }

    static void testAddCommand() {
        ComplexCommand.Builder builder = new ComplexCommand.Builder();
        builder.addCommand(new SetPositionCommand(0, 0));
        ComplexCommand command = builder.build();

        if (command.size() != 1) {
            System.out.println("testAddCommand failed");
        } else {
            System.out.println("testAddCommand passed");
        }
    }

    static void testBuilderAddCommand() {
        ComplexCommand.Builder builder = new ComplexCommand.Builder();
        builder.addCommand(new SetPositionCommand(0, 0));
        builder.addCommand(new OperateToCommand(10, 10));
        ComplexCommand command = builder.build();

        if (command.size() != 2) {
            System.out.println("testBuilderAddCommand failed");
        } else {
            System.out.println("testBuilderAddCommand passed");
        }
    }

    static void testBuilderAddCommands() {
        ComplexCommand.Builder builder = new ComplexCommand.Builder();
        builder.addCommand(new SetPositionCommand(0, 0));

        List<DriverCommand> additionalCommands = new ArrayList<>();
        additionalCommands.add(new OperateToCommand(10, 10));
        additionalCommands.add(new OperateToCommand(20, 20));
        builder.addCommands(additionalCommands);

        ComplexCommand command = builder.build();

        if (command.size() != 3) {
            System.out.println("testBuilderAddCommands failed");
        } else {
            System.out.println("testBuilderAddCommands passed");
        }
    }

    static void testAddAtIndexCommand() {
        ComplexCommand command = new ComplexCommand();
        command.addCommand(new SetPositionCommand(0, 0));
        command.addCommand(0, new SetPositionCommand(10, 10));

        if (command.size() != 2 || !command.iterator().next().equals(new SetPositionCommand(10, 10))) {
            System.out.println("testAddAtIndexCommand failed");
        } else {
            System.out.println("testAddAtIndexCommand passed");
        }
    }

    static void testRemoveCommand() {
        ComplexCommand command = new ComplexCommand();
        DriverCommand cmd = new SetPositionCommand(0, 0);
        command.addCommand(cmd);
        command.removeCommand(0);

        if (!command.isEmpty()) {
            System.out.println("testRemoveCommand failed");
        } else {
            System.out.println("testRemoveCommand passed");
        }
    }

    static void testCopy() {
        ComplexCommand.Builder builder = new ComplexCommand.Builder();
        builder.addCommand(new SetPositionCommand(10, 10));
        ComplexCommand command = builder.build();
        ComplexCommand copy = (ComplexCommand) command.copy();

        if (command.size() != copy.size() || command == copy) {
            System.out.println("testCopy failed");
        } else {
            System.out.println("testCopy passed");
        }
    }

    static void testComplexCopy() {
        ComplexCommand command = new ComplexCommand.Builder()
                .addCommand(new SetPositionCommand(0, 0))
                .addCommand(new OperateToCommand(10, 10))
                .addCommand(new OperateToCommand(20, 20))
                .addCommand(new SetPositionCommand(0, 0))
                .build();

        ComplexCommand copy = (ComplexCommand) command.copy();

        Iterator<DriverCommand> originalIterator = command.iterator();
        Iterator<DriverCommand> copyIterator = copy.iterator();

        while (originalIterator.hasNext() && copyIterator.hasNext()) {
            DriverCommand originalCommand = originalIterator.next();
            DriverCommand copyCommand = copyIterator.next();

            if (!originalCommand.equals(copyCommand)) {
                System.out.println("testComplexCopy: Commands are not equal!");
                return;
            } else if (originalCommand == copyCommand) {
                System.out.println("testComplexCopy: Commands are the same instance, copy failed!");
                return;
            }
        }

        if (originalIterator.hasNext() || copyIterator.hasNext()) {
            System.out.println("testComplexCopy: Number of commands differ between original and copy!");
            return;
        }

        System.out.println("testComplexCopy passed");
    }

    static void testNestedComplexCopy() {
        ComplexCommand command = new ComplexCommand.Builder()
                .addCommand(new SetPositionCommand(0, 0))
                .addCommand(new OperateToCommand(10, 10))
                .addCommand(new OperateToCommand(20, 20))
                .addCommand(new SetPositionCommand(0, 0))
                .build();

        ComplexCommand command2 = new ComplexCommand.Builder()
                .addCommand(new SetPositionCommand(10, 10))
                .addCommand(new OperateToCommand(50, 50))
                .addCommand(new OperateToCommand(15, 15))
                .build();

        ComplexCommand commandToCopy = new ComplexCommand.Builder()
                .addCommand(command)
                .addCommand(new OperateToCommand(10, 10))
                .addCommand(new OperateToCommand(20, 20))
                .addCommand(new SetPositionCommand(0, 0))
                .addCommand(command2)
                .build();

        ComplexCommand copy = (ComplexCommand) commandToCopy.copy();

        try {
            deepCompareCommands(commandToCopy, copy, 0); // Start at depth 0 for logging
            System.out.println("testNestedComplexCopy passed");
        } catch (AssertionError e) {
            System.out.println("testNestedComplexCopy failed: " + e.getMessage());
        }
    }

    private static void deepCompareCommands(DriverCommand original, DriverCommand copy, int depth) {
        StringBuilder indentBuilder = new StringBuilder(); //indentation for better log readability
        for (int i = 0; i < depth * 4; i++) {
            indentBuilder.append(' ');
        }
        String indent = indentBuilder.toString();

        System.out.printf("%s[Depth %d] Comparing: %s \tvs\t %s%n",
                indent, depth, original, copy);

        if (original == copy) {
            throw new AssertionError(indent + "Commands are the same instance!");
        }

        if (original instanceof ICompoundCommand && copy instanceof ICompoundCommand) {
            ICompoundCommand origComp = (ICompoundCommand) original;
            ICompoundCommand copyComp = (ICompoundCommand) copy;

            Iterator<DriverCommand> origIter = origComp.iterator();
            Iterator<DriverCommand> copyIter = copyComp.iterator();
            int index = 0;

            while (origIter.hasNext() && copyIter.hasNext()) {
                DriverCommand origChild = origIter.next();
                DriverCommand copyChild = copyIter.next();
                deepCompareCommands(origChild, copyChild, depth + 1);
                index++;
            }

            if (origIter.hasNext() || copyIter.hasNext()) {
                throw new AssertionError(indent + "Children count mismatch! Expected: "
                        + (index + (origIter.hasNext() ? " + more" : ""))
                        + " vs Actual: "
                        + (index + (copyIter.hasNext() ? " + more" : "")));
            }
        } else if (!(original instanceof ICompoundCommand) && !(copy instanceof ICompoundCommand)) {
            if (!original.equals(copy)) {
                throw new AssertionError(indent + "Leaf commands differ! "
                        + original + " != " + copy);
            }
        } else {
            throw new AssertionError(indent + "Type mismatch! "
                    + original.getClass().getSimpleName()
                    + " vs " + copy.getClass().getSimpleName());
        }
    }

    static void testRemoveLastCommand() {
        ComplexCommand command = new ComplexCommand();
        command.addCommand(new SetPositionCommand(0, 0));
        command.addCommand(0, new SetPositionCommand(10, 10));
        command.removeLastCommand();

        if (command.size() != 1) {
            System.out.println("testRemoveLastCommand failed");
        } else {
            System.out.println("testRemoveLastCommand passed");
        }
    }

    static void testSetCommand() {
        ComplexCommand command = new ComplexCommand();
        command.addCommand(new SetPositionCommand(0, 0));
        command.setCommand(0, new SetPositionCommand(10, 10));

        if (!command.iterator().next().equals(new SetPositionCommand(10, 10))) {
            System.out.println("testSetCommand failed");
        } else {
            System.out.println("testSetCommand passed");
        }
    }


    static void testClear() {
        ComplexCommand command = new ComplexCommand();
        command.addCommand(new SetPositionCommand(0, 0));
        command.clear();

        if (!command.isEmpty()) {
            System.out.println("testClear failed");
        } else {
            System.out.println("testClear passed");
        }
    }

    static void testEquals() {
        ComplexCommand.Builder builder1 = new ComplexCommand.Builder();
        builder1.addCommand(new SetPositionCommand(10, 10));
        builder1.addCommand(new OperateToCommand(20, 20));
        ComplexCommand command1 = builder1.build();

        ComplexCommand.Builder builder2 = new ComplexCommand.Builder();
        builder2.addCommand(new SetPositionCommand(10, 10));
        builder2.addCommand(new OperateToCommand(20, 20));
        ComplexCommand command2 = builder2.build();

        if (!command1.equals(command2)) {
            System.out.println("testEquals failed");
        } else {
            System.out.println("testEquals passed");
        }
    }


    public static void main(String[] args) {
        testIterator();
        testAddCommand();
        testAddAtIndexCommand();
        testRemoveCommand();
        testRemoveLastCommand();
        testSetCommand();
        testIsEmpty();
        testClear();
        testCopy();
        testComplexCopy();
        testNestedComplexCopy();
        testBuilderAddCommand();
        testBuilderAddCommands();
        testEquals();
    }
}