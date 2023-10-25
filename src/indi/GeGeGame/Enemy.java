package indi.GeGeGame;

import java.awt.*;
import java.util.Random;

public class Enemy {
    public int x, y, width, high, speed;
    public Image image;
    public Image right;
    public int health;

    public Enemy(Player player, Image image,Image right, int speed, int health) {
        this.speed = speed;
        this.image = image;
        this.right = right;
        this.width = image.getWidth(null);
        this.high = image.getHeight(null);
        this.health = health;
        //在地图内，player远处生产xy
        while (this.x > 1000 || this.x <= 0 || this.y <= 0 || this.y > 500 || Math.sqrt(player.x + player.width / 2 - this.x - this.width) + Math.sqrt(player.y + player.high / 2 - this.y - this.high) < Start.EP_distance2) {
            Random random = new Random();
            this.x = random.nextInt(1000);
            this.y = random.nextInt(500);
        }
    }

    public void move(Player player) {
        double angle = Math.atan2(player.y - this.y, player.x - this.x);
        this.x += speed * Math.cos(angle);
        this.y += speed * Math.sin(angle);
    }


}
