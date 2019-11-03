package com.example.ondrej.pacman;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.List;

public class Enemy extends Character {

    private Bitmap image;

    public Enemy(int tileSize, List<Wall> walls, Bitmap enemyBitmap, int x, int y) {
        super(tileSize, walls);
        this.image = enemyBitmap;
        this.rectangle.left = x;
        this.rectangle.top = y;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, rectangle.left, rectangle.top, null);
    }
}
