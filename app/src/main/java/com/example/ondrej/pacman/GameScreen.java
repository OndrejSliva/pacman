package com.example.ondrej.pacman;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceView;

public class GameScreen extends SurfaceView {

    private MainThread mainThread;
    private Level level;
    private int width;
    private int height;
    private Context context;

    public GameScreen(Context context, int width, int height) {
        super(context);

        this.context = context;
        //mainThread = new MainThread(getHolder(), this);
        setFocusable(true);
        this.width = width;
        this.height = height;
    }

    public void run() {

        level = new Level(getResources(), width, height);
        mainThread = new MainThread(getHolder(), this);
        mainThread.setRunning(true);
        mainThread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //TODO ?
        return true;
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
            Intent intent = new Intent(context, MainActivity.class);
            context.startActivity(intent);
        }
    }
}
