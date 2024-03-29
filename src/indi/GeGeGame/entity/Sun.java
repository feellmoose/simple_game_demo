package indi.GeGeGame.entity;

import java.awt.*;

public class Sun {
    public int x, y, width, high, speed;
    public Image image;
    public double angle;

    public Sun(int x, int y, Image image, int speed, double angle) {
        this.x = x;
        this.y = y;
        this.image = image;
        this.high = image.getHeight(null);
        this.width = image.getWidth(null);
        this.speed = speed;
        this.angle = angle;
    }

    public void move() {
        y += Math.sin(angle) * speed;
        x += Math.cos(angle) * speed;
    }


}
