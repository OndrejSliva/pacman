package com.example.ondrej.pacman;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import androidx.constraintlayout.solver.widgets.Rectangle;

public class Player implements IDrawable {

    public static final int DIRECTION_RIGHT = 0;
    public static final int DIRECTION_LEFT = 2;
    public static final int DIRECTION_UP = 1;
    public static final int DIRECTION_DOWN = 3;

    private Rect rectangle;
    private int color;

    private int direction;

    public Player (int size) {

        this.rectangle = new Rect(0, 0, size, size);
        this.color = Color.YELLOW;
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawRect(rectangle, paint);
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    //@Override
    public void update() {
        //TODO
    }

    public void move() {
        switch (this.direction) {
            case DIRECTION_RIGHT:
                rectangle.left += 5;
                rectangle.right += 5;
                break;
            case DIRECTION_LEFT:
                rectangle.left -= 5;
                rectangle.right -= 5;
                break;
            case DIRECTION_UP:
                rectangle.top -= 5;
                rectangle.bottom -= 5;
                break;
            case DIRECTION_DOWN:
                rectangle.top += 5;
                rectangle.bottom += 5;
                break;
            default:
                break;
        }
    }

    public void setPosition(int x, int y) {
        this.rectangle.set(x, y, x+ConstantHelper.TILE_SIZE, y+ConstantHelper.TILE_SIZE);
    }


}
