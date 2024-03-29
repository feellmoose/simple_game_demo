package indi.GeGeGame.entity;

import java.awt.*;

public class Player {
    public int health;
    public int x, y, width, high, speed;
    public Image image;
    public Image imageleft;
    public double angle;
    public boolean w, a, s, d;
    public Boolean openfire = false;

    public Player(int x, int y, Image image, Image image_left, int speed) {
        this.health = 100;
        this.x = x;
        this.y = y;
        this.image = image;
        this.imageleft = image_left;
        this.high = image.getHeight(null);
        this.width = image.getWidth(null);
        this.speed = speed;
    }

    public void move(int x_speed, int y_speed) {
        x += x_speed;
        y += y_speed;
    }


}
