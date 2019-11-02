package com.example.ondrej.pacman;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.List;

public class Player implements IDrawable {

    public static final int DIRECTION_RIGHT = 0;
    public static final int DIRECTION_LEFT = 2;
    public static final int DIRECTION_UP = 1;
    public static final int DIRECTION_DOWN = 3;

    private Rect rectangle;
    private int color;

    private int actualDirection = DIRECTION_UP;
    private int nextDirection = DIRECTION_UP;
    private List<Wall> walls;
    private int speed = 5;

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

    public void setNextDirection(int nextDirection) {
        this.nextDirection = nextDirection;
        this.setActualDirectionToNextIfCanMoveThere();
    }

    private void setActualDirectionToNextIfCanMoveThere() {
        if (this.canMoveToDirection(this.nextDirection)) {
            this.actualDirection = this.nextDirection;
        }
    }

    //@Override
    public void update() {
        this.setActualDirectionToNextIfCanMoveThere();
        this.move();
    }

    private void move() {
        switch (this.actualDirection) {
            case DIRECTION_RIGHT:
                if (canMoveRight()) {
                    rectangle.left += this.speed;
                    rectangle.right += this.speed;
                }
                break;
            case DIRECTION_LEFT:
                if (canMoveLeft()) {
                    rectangle.left -= this.speed;
                    rectangle.right -= this.speed;
                }
                break;
            case DIRECTION_UP:
                if (canMoveUp()) {
                    rectangle.top -= this.speed;
                    rectangle.bottom -= this.speed;
                }
                break;
            case DIRECTION_DOWN:
                if (canMoveDown()) {
                    rectangle.top += this.speed;
                    rectangle.bottom += this.speed;
                }
                break;
            default:
                break;
        }
    }

    private boolean canMoveRight() {
        return canMove(rectangle.left + this.speed, rectangle.top);
    }

    private boolean canMoveLeft() {
        return canMove(rectangle.left - this.speed, rectangle.top);
    }

    private boolean canMoveUp() {
        return canMove(rectangle.left,rectangle.top - this.speed);
    }

    private boolean canMoveDown() {
        return canMove(rectangle.left,rectangle.top + this.speed);
    }

    private boolean canMoveToDirection(int direction) {
        switch (direction) {
            case DIRECTION_RIGHT:
                return canMoveRight();
            case DIRECTION_LEFT:
                return canMoveLeft();
            case DIRECTION_UP:
                return canMoveUp();
            case DIRECTION_DOWN:
                return canMoveDown();
            default:
                return false;
        }
    }

    private boolean isOnCross() {
        int directionsAvaibleForMove = 0;

        if (canMoveLeft()) directionsAvaibleForMove++;
        if (canMoveRight()) directionsAvaibleForMove++;
        if (canMoveUp()) directionsAvaibleForMove++;
        if (canMoveDown()) directionsAvaibleForMove++;

        return directionsAvaibleForMove > 2;
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

    public Rect getRectangle() {
        return this.rectangle;
    }
}
