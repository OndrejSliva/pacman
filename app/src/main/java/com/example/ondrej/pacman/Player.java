package com.example.ondrej.pacman;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import androidx.constraintlayout.solver.widgets.Rectangle;

public class Player implements GameObject {

    public static final int DIRECTION_RIGHT = 0;
    public static final int DIRECTION_LEFT = 2;
    public static final int DIRECTION_UP = 1;
    public static final int DIRECTION_DOWN = 3;

    private Rect rectangle;
    private int color;

    private int direction;

    public Player (Rect rectangle, int color) {
        this.rectangle = rectangle;
        this.color = color;
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

    @Override
    public void update() { }

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

    public void update(Point point) {
        rectangle.set(point.x -  rectangle.width() /2, point.y - rectangle.height()/2,
                point.x + rectangle.width()/2, point.y + rectangle.height()/2);
    }
}
