package edu.kis.powp.jobs2d.drivers;

import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.drivers.visitors.DriverVisitor;

import javax.swing.*;
import java.util.LinkedList;
import java.util.Queue;

public class RealTimeDecoratorDriver implements VisitableJob2dDriver {
    private final JPanel panel;
    private final Queue<Runnable> tasks = new LinkedList<>();
    private static int queuedX = 0;
    private static int queuedY = 0;

    private final int INTERVAL_MS;
    private final int INTERVAL_LINE_LENGTH;
    private final Job2dDriver driver;

    public RealTimeDecoratorDriver(Job2dDriver driver, JPanel panel, int intervalMs, int intervalLineLength) {
        this.driver = driver;
        this.panel = panel;
        this.INTERVAL_LINE_LENGTH = intervalLineLength;
        this.INTERVAL_MS = intervalMs;
        startTaskExecution();
    }

    @Override
    public void operateTo(int x, int y) {
        int startX = queuedX;
        int startY = queuedY;

        queuedX = x;
        queuedY = y;

        double distance = Math.hypot(x - startX, y - startY);
        int steps = Math.max((int) (distance / INTERVAL_LINE_LENGTH), 1);
        double dx = (x - startX) / (double) steps;
        double dy = (y - startY) / (double) steps;

        for (int i = 1; i <= steps; i++) {
            final int stepX = (int) Math.round(startX + i * dx);
            final int stepY = (int) Math.round(startY + i * dy);

            tasks.add(() -> {
                driver.operateTo(stepX, stepY);
                panel.repaint();
            });
            tasks.add(() -> {
                try {
                    Thread.sleep(INTERVAL_MS);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            });
        }
    }

    @Override
    public void setPosition(int x, int y) {
        queuedX = x;
        queuedY = y;

        if (tasks.isEmpty()) {
            driver.setPosition(x, y);
        } else {
            tasks.add(() -> driver.setPosition(x, y));
        }
    }

    private void startTaskExecution() {
        new Timer(0, e -> {
            if (!tasks.isEmpty()) {
                SwingUtilities.invokeLater(tasks.poll());
            }
        }).start();
    }

    public static int getQueuedX() {
        return queuedX;
    }
    public static int getQueuedY() {
        return queuedY;
    }

    @Override
    public void accept(DriverVisitor visitor) {

    }
}
