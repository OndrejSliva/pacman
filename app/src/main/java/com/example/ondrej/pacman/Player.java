package com.example.ondrej.pacman;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import androidx.constraintlayout.solver.widgets.Rectangle;

import java.util.List;

public class Player implements IDrawable {

    public static final int DIRECTION_RIGHT = 0;
    public static final int DIRECTION_LEFT = 2;
    public static final int DIRECTION_UP = 1;
    public static final int DIRECTION_DOWN = 3;

    private Rect rectangle;
    private int color;

    private int direction = DIRECTION_UP;
    private List<Wall> walls;

    public Player (List<Wall> walls, int size) {

        this.rectangle = new Rect(0, 0, size, size);
        this.color = Color.YELLOW;
        this.walls = walls;
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
                if (canMove(rectangle.left + 5, rectangle.top)) {
                    rectangle.left += 5;
                    rectangle.right += 5;
                }
                break;
            case DIRECTION_LEFT:
                if (canMove(rectangle.left - 5, rectangle.top)) {
                    rectangle.left -= 5;
                    rectangle.right -= 5;
                }
                break;
            case DIRECTION_UP:
                if (canMove(rectangle.left,rectangle.top - 5)) {
                    rectangle.top -= 5;
                    rectangle.bottom -= 5;
                }
                break;
            case DIRECTION_DOWN:
                if (canMove(rectangle.left,rectangle.top + 5)) {
                    rectangle.top += 5;
                    rectangle.bottom += 5;
                }
                break;
            default:
                break;
        }
    }

    private boolean canMove(int nextX, int nextY) {
        Rect nextPositionRectangle = new Rect(nextX, nextY, nextX + ConstantHelper.TILE_SIZE, nextY + ConstantHelper.TILE_SIZE);

        for (Wall w : walls) {
            if (w.colides(nextPositionRectangle)) {
                return false;
            }
        }

        return true;
    }

    public void setPosition(int x, int y) {
        this.rectangle.set(x, y, x+ConstantHelper.TILE_SIZE, y+ConstantHelper.TILE_SIZE);
    }


}
