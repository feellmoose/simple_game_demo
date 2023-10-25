package indi.GeGeGame;

import indi.GeGeGame.entity.Enemy;
import indi.GeGeGame.entity.Sun;
import indi.GeGeGame.resouce.GameUtil;
import indi.GeGeGame.resouce.Sound;

import java.awt.*;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class Start {
    public static ThreadPoolExecutor executor = new ThreadPoolExecutor(10,14,1000, TimeUnit.SECONDS,new LinkedBlockingQueue<>());
    public static double start_angle = -1.5;
    public static int frame_rate = 50;
//    public static int screen_width = Toolkit.getDefaultToolkit().getScreenSize().width;
//    public static int screen_high = Toolkit.getDefaultToolkit().getScreenSize().height;
//    public static int screen_x = (screen_width - 1000) / 2;
//    public static int screen_y = (screen_high - 500) / 2;
    public static int player_speed = 10;
    public static int basket_speed = 20;
    public static int EP_distance2 = 400;

    public static void main(String[] args) {
        WinGame winGame = new WinGame();
        executor.execute(() -> {
            while (true) {
                Sound.playMusic(GameUtil.BGMSoundPath, 2);
            }
        });
        executor.execute(() -> {
            while (true) {
                if (winGame.game_state == 0) {
                    int m = new Random().nextInt(10);
                    if (m < 5) {
                        winGame.enemies.add(new Enemy(
                                winGame.player,
                                GameUtil.getImage(GameUtil.enemyImagePath).getScaledInstance(100, 100, Image.SCALE_DEFAULT),
                                GameUtil.getImage(GameUtil.rightEnemyImagePath).getScaledInstance(100, 100, Image.SCALE_DEFAULT),
                                5, 100));
                        if (m < 1) {
                            Sound.playMusic(GameUtil.enemySoundPath, 0.2);
                        }
                    }
                    int s2 = winGame.enemies.size();
                    for (int j = 0; j < s2; j++) {
                        if (j < winGame.enemies.size()) {
                            Enemy e = winGame.enemies.get(j);
                            if (e.x + e.width > winGame.player.x && winGame.player.x + winGame.player.width > e.x && e.y + e.high > winGame.player.y && winGame.player.y + winGame.player.high > e.y) {
                                winGame.player.health -= 10;
                                Sound.playMusic(GameUtil.aPath, 0.5);
                                if (winGame.player.health <= 0) {
                                    Sound.playMusic(GameUtil.enemyAttackSoundPath, 0.5);
                                    winGame.game_state = 2;
                                }
                            }
                        }
                    }
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        while (true) {
            if (winGame.game_state == 0) {
                for (Enemy e : winGame.enemies) {
                    e.move(winGame.player);
                }
                Point point = MouseInfo.getPointerInfo().getLocation();
                point.translate(-winGame.getLocation().x, -winGame.getLocation().y);
                if (winGame.player.w && winGame.player.y >= 0) {
                    winGame.player.move(0, -winGame.player.speed);
                }
                if (winGame.player.s && winGame.player.y <= 500 - winGame.player.high) {
                    winGame.player.move(0, winGame.player.speed);
                }
                if (winGame.player.a && winGame.player.x >= 0) {
                    winGame.player.move(-winGame.player.speed, 0);
                }
                if (winGame.player.d && winGame.player.x <= 1000 - winGame.player.width) {
                    winGame.player.move(winGame.player.speed, 0);
                }
                winGame.player.angle = start_angle + Math.atan2(point.x - winGame.player.x - winGame.player.width / 2, -point.y + winGame.player.y + winGame.player.high / 2);
                if (winGame.player.openfire) {
                    executor.execute(() -> Sound.playMusic(GameUtil.openFireSoundPath, 0.5));
                    winGame.sunList.add(new Sun(winGame.player.x + winGame.player.width / 2, winGame.player.y + winGame.player.high / 2, GameUtil.getImage(GameUtil.basketballImagePath), Start.basket_speed, winGame.player.angle));
                }
                int size = winGame.sunList.size();
                for (int i = 0; i < size; i++) {
                    Sun sun = winGame.sunList.get(i);
                    sun.move();
                    if (sun.x <= 0 || sun.x >= 1000 || sun.y <= 0 || sun.y >= 500) {
                        size--;
                        if (size > 0 && i < winGame.sunList.size()) {
                            winGame.sunList.remove(i);
                        }
                    }
                    int s2 = winGame.enemies.size();
                    for (int j = 0; j < s2; j++) {
                        Enemy e = winGame.enemies.get(j);
                        if (e.x + e.width > sun.x && sun.x + sun.width > e.x && e.y + e.high > sun.y && sun.y + sun.high > e.y) {
                            e.health -= 10;
                            if (e.health <= 0) {
                                if (s2 > 0) {
                                    winGame.enemies.remove(j);
                                    winGame.score++;
                                }
                                s2--;
                            }
                            size--;
                            if (i > 0 && i < winGame.sunList.size()) {
                                winGame.sunList.remove(i);
                            }
                        }
                    }
                }
            }
            winGame.update(winGame.getGraphics());
            try {
                Thread.sleep(frame_rate);//帧率设置
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
