package indi.GeGeGame;

import java.io.File;
import javax.sound.sampled.*;

public class Sound {
    private AudioInputStream audioStream;
    private AudioFormat audioFormat;
    private SourceDataLine sourceDataLine;

    public Sound(String path,double value) {
        playMusic(path,value);
    }
        static void playMusic(String path,double value) {
            try {
                AudioInputStream ais = AudioSystem.getAudioInputStream(new File(path));    //绝对路径
                AudioFormat aif = ais.getFormat();
                final SourceDataLine sdl;
                DataLine.Info info = new DataLine.Info(SourceDataLine.class, aif);
                sdl = (SourceDataLine) AudioSystem.getLine(info);
                sdl.open(aif);
                sdl.start();
                FloatControl fc = (FloatControl) sdl.getControl(FloatControl.Type.MASTER_GAIN);
                // value可以用来设置音量，从0-2.0
                float dB = (float) (Math.log(value == 0.0 ? 0.0001 : value) / Math.log(10.0) * 20.0);
                fc.setValue(dB);
                int nByte = 0;
                final int SIZE = 1024 * 64;
                byte[] buffer = new byte[SIZE];
                while (nByte != -1) {
                    nByte = ais.read(buffer, 0, SIZE);
                    sdl.write(buffer, 0, nByte);
                }
                sdl.stop();
            } catch (Exception e) {
                //e.printStackTrace();
                //未处理的异常，nbyte还是有==-1出现
            }
        }


    }



