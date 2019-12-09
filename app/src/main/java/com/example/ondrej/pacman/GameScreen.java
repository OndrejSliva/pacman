package com.example.ondrej.pacman;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.media.MediaPlayer;
import android.view.SurfaceView;

public class GameScreen extends SurfaceView {

    private MainThread mainThread;
    private Level level;
    private Context context;
    private SoundAgent soundAgent;
    private int levelId;

    public GameScreen(Context context, int levelId) {
        super(context);
        this.levelId = levelId;
        this.context = context;
        setFocusable(false);
    }

    public void run() {

        soundAgent = new SoundAgent(context);
        level = new Level(context, soundAgent, levelId);
        mainThread = new MainThread(getHolder(), this);
        soundAgent.playBeginMusic();
        mainThread.setRunning(true);
        mainThread.start();

        MediaPlayer mediaPlayer = soundAgent.getMediaPlayerForBeginMusic();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                level.setStatus(Level.STATUS_RUN);
            }
        });
    }

    public void update() {
        this.level.update();
        this.isEnd();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        this.level.draw(canvas);
    }

    public void setPlayerDirection(int direction) {
        this.level.setPlayerNextDirection(direction);
    }

    private void isEnd() {
        if (this.level.isEnd()) {
            mainThread.setRunning(false);
            Intent intent = new Intent(context, WriteNameActivity.class);
            intent.putExtra("score", level.getScore());
            intent.putExtra("levelId", levelId);
            context.startActivity(intent);
        }
    }
}
