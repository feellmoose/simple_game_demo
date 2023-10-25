package indi.GeGeGame;

import java.awt.*;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class Start {
    public static ThreadPoolExecutor executor = new ThreadPoolExecutor(10,10,1000, TimeUnit.SECONDS,new LinkedBlockingQueue<>());
    public static double start_angle = -1.5;
    public static int frame_rate = 50;
    public static int screen_width = Toolkit.getDefaultToolkit().getScreenSize().width;
    public static int screen_high = Toolkit.getDefaultToolkit().getScreenSize().height;
    public static int screen_x = (screen_width - 1000) / 2;
    public static int screen_y = (screen_high - 500) / 2;
    public static int player_speed = 10;
    public static int basket_speed = 20;
    public static int EP_distance2 = 400;

    public static void main(String[] args) {
        WinGame winGame = new WinGame();
        Sound.playMusic(GameUtil.openFireSoundPath, 2);
        new Thread(() -> {
            while (true) {
                Sound.playMusic(GameUtil.BGMSoundPath, 0.2);
            }
        }).start();
        new EnemyThreads(winGame).start();
        while (true) {
            //如果开始则更新
            if (winGame.game_state == 0) {
                //敌人移动
                for (Enemy e : winGame.enemies) {
                    e.move(winGame.player);
                }
                //获取方向更改player属性
                Point point = MouseInfo.getPointerInfo().getLocation();
                point.translate(-winGame.getLocation().x, -winGame.getLocation().y);
                //player移动
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
                //获取点击更改图片属性,发射篮球+回收篮球
                if (winGame.player.openfire) {
                    //此处开火音频
                    executor.execute(() -> {
                        Sound.playMusic(GameUtil.openFireSoundPath, 2);
                    });
                    //初始化篮球
                    winGame.sunList.add(new Sun(winGame.player.x + winGame.player.width / 2, winGame.player.y + winGame.player.high / 2, GameUtil.getImage(GameUtil.basketballImagePath), Start.basket_speed, winGame.player.angle));
                }
                int size = winGame.sunList.size();
                for (int i = 0; i < size; i++) {
                    Sun sun = winGame.sunList.get(i);
                    sun.move();
                    //若在窗口外删除篮球
                    if (sun.x <= 0 || sun.x >= 1000 || sun.y <= 0 || sun.y >= 500) {
                        size--;
                        if (size > 0 && i < winGame.sunList.size()) {
                            winGame.sunList.remove(i);
                        }
                    }
                    int s2 = winGame.enemies.size();
                    //若命中
                    for (int j = 0; j < s2; j++) {
                        Enemy e = winGame.enemies.get(j);
                        //命中判断
                        if (e.x + e.width > sun.x && sun.x + sun.width > e.x && e.y + e.high > sun.y && sun.y + sun.high > e.y) {
                            e.health -= 10;
                            //此处命中音频
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
