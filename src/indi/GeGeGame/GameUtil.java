package indi.GeGeGame;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class GameUtil {
    //打成jar包不行
    //public static final String CLASSPATH="classpath:/";
    //打成jar包
    public static final String CLASSPATH="";
    public static String openFireSoundPath= CLASSPATH+"sounds/Che.wav";
    public static String BGMSoundPath= CLASSPATH+"sounds/jinitaimai.wav";
    public static String enemySoundPath= CLASSPATH+"sounds/enemy.wav";
    public static String enemyAttackSoundPath= CLASSPATH+"sounds/enemyAttack.wav";
    public static String aPath= CLASSPATH+"sounds/a.wav";


    //ImagePath
    public static String backImagePath=CLASSPATH+"Images/R-C.png";
    public static String basketballImagePath=CLASSPATH+"Images/R-C(1)(1).png";
    public static String playerTwoImagePath=CLASSPATH+"Images/R-C(1).png";
    public static String playerThreeImagePath= CLASSPATH+"Images/left.png";
    public static String enemyImagePath= CLASSPATH+"Images/download.png";


    public static Image getImage(String path){

        BufferedImage image=null;
        try{
            image=ImageIO.read(new File(path));
        }catch (Exception e){}

        return image;
    }




}
