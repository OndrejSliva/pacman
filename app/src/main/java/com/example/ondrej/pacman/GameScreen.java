package com.example.ondrej.pacman;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameScreen extends SurfaceView {

    private MainThread mainThread;
    private Player player;
    private Point point;

    public GameScreen(Context context) {
        super(context);

        mainThread = new MainThread(getHolder(), this);

        setFocusable(true);

        player = new Player(new Rect(100, 100, 150, 150), Color.YELLOW);
        point = new Point(150, 150);
    }

    public void run() {

        mainThread = new MainThread(getHolder(), this);
        mainThread.setRunning(true);
        mainThread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                point.set((int)event.getX(), (int)event.getY());
        }

        return true;
    }

    public void update() {
        player.move();
        //player.update(point);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        //pozadí plátna
        canvas.drawColor(Color.BLACK);

        player.draw(canvas);
    }

    public void setPlayerDirection(int direction) {
        this.player.setDirection(direction);
    }
}
