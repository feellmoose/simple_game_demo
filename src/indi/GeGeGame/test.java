package indi.GeGeGame;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class test {
    public static void main(String[] args) {

        System.out.println(GameUtil.CLASSPATH);
        File file=new File(GameUtil.openFireSoundPath);
        Image image;
        try {
            image=ImageIO.read(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }



}
