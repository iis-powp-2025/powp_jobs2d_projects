package edu.kis.powp.jobs2d.features;
import edu.kis.powp.jobs2d.Job2dDriver;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ClicksConverter extends MouseAdapter {
    private final int MOUSE_BUTTON_LEFT = 1;
    private final int MOUSE_BUTTON_RIGHT = 3;

    private static class Point {
        private int x;
        private int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }

    public ClicksConverter(JPanel panel) {
        panel.addMouseListener(this);
    }

    private Point getClickPosition(MouseEvent event) {
        int x = event.getX();
        int y = event.getY();

        int offsetX = event.getComponent().getWidth()/2;
        int offsetY = event.getComponent().getHeight()/2;

        return new Point(x - offsetX, y - offsetY);
    }

    public void mouseClicked(MouseEvent event) {
        Point position = getClickPosition(event);
        int buttonPressed = event.getButton();

        handleDriver(position, buttonPressed);
    }

    private void handleDriver(Point position, int buttonPressed) {
        Job2dDriver driver = DriverFeature.getDriverManager().getCurrentDriver();

        if(buttonPressed == MOUSE_BUTTON_LEFT) {
            driver.operateTo(position.getX(), position.getY());
        }

        if (buttonPressed == MOUSE_BUTTON_RIGHT) {
            driver.setPosition(position.getX(), position.getY());
        }
    }

}
