package indi.GeGeGame;

import java.awt.*;
import java.util.Random;

public class EnemyThreads extends Thread{
    WinGame winGame;
    EnemyThreads(WinGame winGame){
        this.winGame=winGame;

    }

    @Override
    public void run() {
        while (true) {
            //如果开始
            if(winGame.game_state==0) {
                int m = new Random().nextInt(10);
                if (m < 5) {
                    //获得敌人
                    winGame.enemies.add(new Enemy(winGame.player, GameUtil.getImage(GameUtil.enemyImagePath).getScaledInstance(50, 50, Image.SCALE_DEFAULT), 5, 100));
                    //音频
//                t.start();
                    if (m < 1) {
                        Sound.playMusic(GameUtil.enemySoundPath, 1);
                    }
                    //System.out.println(winGame.enemies.size());
                }
                int s2 = winGame.enemies.size();
                //敌人攻击判断
                for (int j = 0; j < s2; j++) {
                    if (j < winGame.enemies.size()) {
                        Enemy e = winGame.enemies.get(j);
                        //命中判断
                        if (e.x + e.width > winGame.player.x && winGame.player.x + winGame.player.width > e.x && e.y + e.high > winGame.player.y && winGame.player.y + winGame.player.high > e.y) {
                            winGame.player.health -= 10;
                            //此处命中音频
                            Sound.playMusic(GameUtil.aPath, 2);

                            if (winGame.player.health <= 0) {
                                //若命中玩家死亡
                                Sound.playMusic(GameUtil.enemyAttackSoundPath, 2);
                                //
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
    }
}
