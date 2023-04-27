package indi.GeGeGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;

public class WinGame extends JFrame {
    //开始：0，暂停：1，失败：2
    public int game_state=1;
    public int score;
    public Player player;
    public LinkedList<Basketball> basketballList=new LinkedList<>();
    public LinkedList<Enemy> enemies=new LinkedList<>();

    //init刷新
    public void updateWinGame(){
        this.player=new Player(0,0,GameUtil.getImage(GameUtil.playerTwoImagePath).getScaledInstance(120,110,Image.SCALE_DEFAULT),GameUtil.getImage(GameUtil.playerThreeImagePath).getScaledInstance(120,110,Image.SCALE_DEFAULT), Start.player_speed);
        this.enemies.clear();
        this.basketballList.clear();
        this.score=0;
    }



    public WinGame(){
        //加载音频


        //获得玩家
        this.player=new Player(0,0,GameUtil.getImage(GameUtil.playerTwoImagePath).getScaledInstance(120,110,Image.SCALE_DEFAULT),GameUtil.getImage(GameUtil.playerThreeImagePath).getScaledInstance(120,110,Image.SCALE_DEFAULT), Start.player_speed);
        this.setTitle("game");
        this.setSize(1000,500);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setBackground(Color.white);
        this.setIconImage(GameUtil.getImage(GameUtil.basketballImagePath));
        //添加监听器
        this.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                char move_char= e.getKeyChar();

                if(move_char=='w'){player.w=true;}
                if(move_char=='s'){player.s=true;}
                if(move_char=='a'){player.a=true;}
                if(move_char=='d'){player.d=true;}
                if(move_char=='r'&&game_state==1){game_state=0;}
                else if(move_char=='r'&&game_state==0){game_state=1;}
                else if(move_char=='r'&&game_state==2){game_state=1;updateWinGame();}
            }

            @Override
            public void keyReleased(KeyEvent e) {
                char move_char= e.getKeyChar();
                if(move_char=='w'){player.w=false;}
                if(move_char=='s'){player.s=false;}
                if(move_char=='a'){player.a=false;}
                if(move_char=='d'){player.d=false;}
            }
        });
        //发射篮球
        this.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                player.openfire=true;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                player.openfire=false;
            }
            //
        });





        //游戏的封装在外面
        this.setVisible(true);

    }


    @Override
    public void update(Graphics g) {
        Image offScreenImage = null;
        if (offScreenImage == null)
            offScreenImage = createImage(1000, 500);
        Graphics gOff = offScreenImage.getGraphics();
        // 调用paint(),将缓冲图象的画笔传入
        paint(gOff);
        // 再将此缓冲图像一次性绘到代表屏幕的Graphics对象，即该方法传入的“g”上
        g.drawImage(offScreenImage, 0, 0, null);



    }

    @Override
    public void paint(Graphics g) {

        //只负责根据属性画图
        //背景
        g.drawImage(GameUtil.getImage(GameUtil.backImagePath).getScaledInstance(1000,500,Image.SCALE_DEFAULT),0,0,null);

        //TODO 添加页面和文本
        if(game_state==0){
            //游戏中画面设计
            g.setColor(Color.ORANGE);
            g.setFont(new Font("仿宋",Font.BOLD,50));//字体，风格，字号
            g.drawString("哥哥的血量:"+player.health,10,100);
            g.drawString("哥哥的得分:"+score,510,100);

        }else if(game_state==1){
            //暂停游戏，按r继续游戏
            g.setColor(Color.ORANGE);
            g.setFont(new Font("仿宋",Font.BOLD,100));//字体，风格，字号
            g.drawString("请按\"r\"开始游戏",100,250);
            g.setFont(new Font("仿宋",Font.BOLD,35));//字体，风格，字号
            g.drawString("\"w,s,a,d\"控制哥哥上下左右移动，鼠标控制方向",100,300);
            g.drawString("鼠标点击控制发射子弹",100,350);
        }else if(game_state==2){
            //游戏失败，累计分数
            g.setColor(Color.ORANGE);
            g.setFont(new Font("仿宋",Font.BOLD,70));//字体，风格，字号
            g.drawString("恭喜哥哥得到了:"+score+"分",175,200);
            g.setFont(new Font("仿宋",Font.BOLD,70));//字体，风格，字号
            g.drawString("请按\"r\"继续游戏",200,300);

        }

        Graphics2D g2=(Graphics2D)g;
        for (Basketball b:
                basketballList) {
            g2.drawImage(b.image,b.x,b.y,this);
        }
        for (Enemy e:
                enemies) {
            //TODO 根据血量完成血条的绘制
            g2.setColor(Color.red);
            g2.fillRect(e.x,e.y-5,e.health/2,4);
            //g2.draw(new Rectangle(e.x-10,e.y-5,e.health,3));
            //System.out.println("drawing");
            g2.drawImage(e.image,e.x,e.y,this);
        }



        if(player.angle>=-3.14/2&&player.angle<=3.14/2) {
            //右
            g2.translate(player.x + player.width/2, player.y + player.high/2);
            g2.rotate(player.angle);
            g2.translate(-player.x - player.width/2, -player.y - player.high/2);
            g2.drawImage(player.image, player.x, player.y, this);
        }else {
            //左
            g2.translate(player.x + player.width/2, player.y + player.high/2);
            g2.rotate(player.angle-3.14);
            g2.translate(-player.x - player.width/2, -player.y - player.high/2);
            g2.drawImage(player.imageleft, player.x, player.y, this);
        }





    }
}
