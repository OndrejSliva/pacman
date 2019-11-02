package com.example.ondrej.pacman;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class MainThread extends Thread {

    public static final int MAX_FPS = 60;

    private double averageFPS;

    private SurfaceHolder surfaceHolder;

    private GameScreen gamePanel;

    private boolean running;

    private Canvas canvas;

    public MainThread(SurfaceHolder surfaceHolder, GameScreen gamePanel) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;
    }

    public void run() {
        long starTime;
        long timeMills = 1000/MAX_FPS;
        long waitTime;
        int frameCount = 0;
        long totalTime = 0;
        long targetTime = 1000/MAX_FPS;

        while (running) {
            starTime = System.nanoTime();

            canvas = null;

            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    this.gamePanel.draw(canvas);
                    this.gamePanel.update();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            timeMills = (System.nanoTime() - starTime) / 1000000;
            waitTime = targetTime - timeMills;
            try {
                if (waitTime > 0) {
                    this.sleep(waitTime);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            totalTime += System.nanoTime() - starTime;
            frameCount++;

            if (frameCount == MAX_FPS) {
                averageFPS = 1000 / ((totalTime/frameCount) / 1000000);
                frameCount = 0;
                totalTime = 0;
                System.out.println(averageFPS);
            }
        }
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}
