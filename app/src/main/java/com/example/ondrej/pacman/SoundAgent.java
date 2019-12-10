package com.example.ondrej.pacman;

import android.content.Context;
import android.media.MediaPlayer;

public class SoundAgent {

    public static int BEGIN_MUSIC = 0;
    public static int DEATH_SOUND = 1;
    public static int EAT_SOUND = 2;
    public static int EAT_SOUND_2 = 3;

    private MediaPlayer[] mediaPlayers = new MediaPlayer[4];

    public SoundAgent(Context context) {
        mediaPlayers[BEGIN_MUSIC] = MediaPlayer.create(context, R.raw.pacman_beginning);
        mediaPlayers[DEATH_SOUND] = MediaPlayer.create(context, R.raw.death);
        mediaPlayers[EAT_SOUND] = MediaPlayer.create(context, R.raw.eat_1);
        mediaPlayers[EAT_SOUND_2] = MediaPlayer.create(context, R.raw.eat_2);
    }

    public void playBeginMusic() {
        mediaPlayers[BEGIN_MUSIC].start();
    }

    public void playDeathSound() {
        mediaPlayers[DEATH_SOUND].start();
    }

    public void playEatSound() {
        mediaPlayers[EAT_SOUND].start();
    }

    public void playEatSound2() {
        mediaPlayers[EAT_SOUND_2].start();
    }

    public MediaPlayer getMediaPlayerForBeginMusic() {
        return mediaPlayers[BEGIN_MUSIC];
    }

    public MediaPlayer getMediaPlayerForDeathSound() {
        return mediaPlayers[DEATH_SOUND];
    }
}
