package indi.GeGeGame.resouce;

import javax.sound.sampled.*;
import java.io.File;

public class Sound {
    public Sound(String path, double value) {
        playMusic(path, value);
    }

    static public void playMusic(String path, double value) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(new File(path));    //绝对路径
            AudioFormat aif = ais.getFormat();
            final SourceDataLine sdl;
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, aif);
            sdl = (SourceDataLine) AudioSystem.getLine(info);
            sdl.open(aif);
            sdl.start();
            FloatControl fc = (FloatControl) sdl.getControl(FloatControl.Type.MASTER_GAIN);
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
            //
        }
    }

}



