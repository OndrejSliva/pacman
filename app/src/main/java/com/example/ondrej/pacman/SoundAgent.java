package com.example.ondrej.pacman;

import android.content.Context;
import android.media.MediaPlayer;

public class SoundAgent {

    public static int BEGIN_MUSIC = 0;

    private MediaPlayer[] mediaPlayers = new MediaPlayer[1];

    public SoundAgent(Context context) {

        //TODO další zvuky
        mediaPlayers[BEGIN_MUSIC] = MediaPlayer.create(context, R.raw.pacman_beginning);
    }

    public void playBeginMusic() {
        mediaPlayers[BEGIN_MUSIC].start();
    }

    public MediaPlayer getMediaPlayerForBeginMusic() {
        return mediaPlayers[BEGIN_MUSIC];
    }
}
