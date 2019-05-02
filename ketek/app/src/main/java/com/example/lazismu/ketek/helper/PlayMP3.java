package com.example.lazismu.ketek.helper;

import android.content.Context;
import android.media.MediaPlayer;

import com.example.lazismu.ketek.R;

import java.io.IOException;

public class PlayMP3 {
    static MediaPlayer mp = new MediaPlayer();

    public static void play(Context context) {

        mp = MediaPlayer.create(context, R.raw.niatzakat);

        try {
            mp.prepare();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mp.start();
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                //btnPlay.setEnabled(true);
            }
        });

    }

    public static void stopPLay(){
        mp.stop();
    }
}
