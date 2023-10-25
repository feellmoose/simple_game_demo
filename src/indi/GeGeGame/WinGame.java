package indi.GeGeGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

public class WinGame extends JFrame {
    public int game_state = 1;
    public int score;
    public Player player;
    public LinkedList<Sun> sunList = new LinkedList<>();
    public LinkedList<Enemy> enemies = new LinkedList<>();

    public void updateWinGame() {
        this.player = new Player(0, 0, GameUtil.getImage(GameUtil.playerTwoImagePath).getScaledInstance(120, 110, Image.SCALE_DEFAULT), GameUtil.getImage(GameUtil.playerThreeImagePath).getScaledInstance(120, 110, Image.SCALE_DEFAULT), Start.player_speed);
        this.enemies.clear();
        this.sunList.clear();
        this.score = 0;
    }

    public WinGame() {
        this.player = new Player(0, 0, GameUtil.getImage(GameUtil.playerTwoImagePath).getScaledInstance(120, 110, Image.SCALE_DEFAULT), GameUtil.getImage(GameUtil.playerThreeImagePath).getScaledInstance(120, 110, Image.SCALE_DEFAULT), Start.player_speed);
        this.setTitle("game");
        this.setSize(1000, 500);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setBackground(Color.white);
        this.setIconImage(GameUtil.getImage(GameUtil.basketballImagePath));
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                char move_char = e.getKeyChar();
                if (move_char == 'w') {
                    player.w = true;
                }
                if (move_char == 's') {
                    player.s = true;
                }
                if (move_char == 'a') {
                    player.a = true;
                }
                if (move_char == 'd') {
                    player.d = true;
                }
                if (move_char == 'r' && game_state == 1) {
                    game_state = 0;
                } else if (move_char == 'r' && game_state == 0) {
                    game_state = 1;
                } else if (move_char == 'r' && game_state == 2) {
                    game_state = 1;
                    updateWinGame();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                char move_char = e.getKeyChar();
                if (move_char == 'w') {
                    player.w = false;
                }
                if (move_char == 's') {
                    player.s = false;
                }
                if (move_char == 'a') {
                    player.a = false;
                }
                if (move_char == 'd') {
                    player.d = false;
                }
            }
        });
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                player.openfire = true;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                player.openfire = false;
            }
        });
        this.setVisible(true);
    }


    @Override
    public void update(Graphics g) {
        Image offScreenImage = null;
        if (offScreenImage == null)
            offScreenImage = createImage(1000, 500);
        Graphics gOff = offScreenImage.getGraphics();
        paint(gOff);
        g.drawImage(offScreenImage, 0, 0, null);
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(GameUtil.getImage(GameUtil.backImagePath).getScaledInstance(1000, 500, Image.SCALE_DEFAULT), 0, 0, null);
        if (game_state == 0) {
            g.setColor(Color.ORANGE);
            g.setFont(new Font("仿宋", Font.BOLD, 50));//字体，风格，字号
            g.drawString("向日葵的血量:" + player.health, 10, 100);
            g.drawString("向日葵的得分:" + score, 510, 100);
        } else if (game_state == 1) {
            //暂停游戏，按r继续游戏
            g.setColor(Color.ORANGE);
            g.setFont(new Font("仿宋", Font.BOLD, 100));//字体，风格，字号
            g.drawString("请按\"r\"开始游戏", 100, 250);
            g.setFont(new Font("仿宋", Font.BOLD, 35));//字体，风格，字号
            g.drawString("\"w,s,a,d\"控制向日葵上下左右移动，鼠标控制方向", 100, 300);
            g.drawString("鼠标点击控制发射阳关", 100, 350);
        } else if (game_state == 2) {
            //游戏失败，累计分数
            g.setColor(Color.ORANGE);
            g.setFont(new Font("仿宋", Font.BOLD, 70));//字体，风格，字号
            g.drawString("僵尸吃掉了你的脑子", 175, 200);
            g.setFont(new Font("仿宋", Font.BOLD, 60));//字体，风格，字号
            g.drawString("你得到了:" + score + "分", 200, 300);
            g.drawString("请按\"r\"继续游戏", 200, 390);
        }
        Graphics2D g2 = (Graphics2D) g;
        for (Sun b : sunList) {
            g2.drawImage(b.image, b.x, b.y, this);
        }
        for (Enemy e : enemies) {
            g2.setColor(Color.red);
            g2.fillRect(e.x, e.y - 5, e.health / 2, 4);
            if(player.x>e.x){
                g2.drawImage(e.right,e.x,e.y,this);
            }else {
                g2.drawImage(e.image, e.x, e.y, this);
            }
        }

        if (player.angle >= -3.14 / 2 && player.angle <= 3.14 / 2) {
            //右
            g2.translate(player.x + player.width / 2, player.y + player.high / 2);
            g2.rotate(player.angle);
            g2.translate(-player.x - player.width / 2, -player.y - player.high / 2);
            g2.drawImage(player.image, player.x, player.y, this);
        } else {
            //左
            g2.translate(player.x + player.width / 2, player.y + player.high / 2);
            g2.rotate(player.angle - 3.14);
            g2.translate(-player.x - player.width / 2, -player.y - player.high / 2);
            g2.drawImage(player.imageleft, player.x, player.y, this);
        }

    }
}
