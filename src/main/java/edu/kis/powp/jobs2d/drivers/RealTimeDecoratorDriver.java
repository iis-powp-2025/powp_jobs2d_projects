package edu.kis.powp.jobs2d.drivers;

import edu.kis.powp.jobs2d.Job2dDriver;

import javax.swing.*;
import java.util.LinkedList;
import java.util.Queue;

public class RealTimeDecoratorDriver implements Job2dDriver {
    private JPanel panel;
    private Queue<Runnable> tasks = new LinkedList<>();
    private final int INTERVAL_MS = 300;

    private final Job2dDriver driver;

    public RealTimeDecoratorDriver(Job2dDriver driver, JPanel panel) {
        this.driver = driver;
        this.panel = panel;
        startTaskExecution();
    }

    @Override
    public void operateTo(int x, int y) {
        if (tasks.isEmpty()) {
            driver.operateTo(x, y);
            panel.repaint();
            driver.setPosition(x, y);
            tasks.add(() -> {
                try {
                    Thread.sleep(INTERVAL_MS);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        } else {
            tasks.add(() -> {
                driver.operateTo(x, y);
                panel.repaint();
                driver.setPosition(x, y);
                try {
                    Thread.sleep(INTERVAL_MS);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    @Override
    public void setPosition(int x, int y) {
        if (tasks.isEmpty()) {
            driver.setPosition(x, y);
        } else {
            tasks.add(() -> {
                driver.setPosition(x, y);
            });
        }
    }

    private void startTaskExecution() {
        new Timer(0, e -> {
            if (!tasks.isEmpty()) {
                SwingUtilities.invokeLater(tasks.poll());
            }
        }).start();
    }
}