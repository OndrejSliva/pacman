package com.example.ondrej.pacman;

import android.graphics.Rect;

import java.util.Arrays;
import java.util.List;

abstract public class Character implements IDrawable {

    protected int actualDirection = DIRECTION_UP;

    //konstanty směrů
    public static final int DIRECTION_RIGHT = 0;
    public static final int DIRECTION_LEFT = 2;
    public static final int DIRECTION_UP = 1;
    public static final int DIRECTION_DOWN = 3;

    protected int[] speedInTime;
    protected int moveTime = 0;

    //obedlník na canvas
    protected Rect rectangle;

    //zdi, abychom mohli řešit pohyb (kolize)
    protected List<Wall> walls;

    public Character(int tileSize, List<Wall> walls) {
        this.rectangle = new Rect(0, 0, tileSize, tileSize);
        this.walls = walls;
        this.speedInTime = getSpeeds(tileSize);
    }

    public void setPosition(int x, int y) {
        this.rectangle.set(x, y, x+ConstantHelper.TILE_SIZE, y+ConstantHelper.TILE_SIZE);
    }

    protected boolean canMoveRight() {
        return canMove(rectangle.left + this.speedInTime[this.moveTime], rectangle.top);
    }

    protected boolean canMoveLeft() {
        return canMove(rectangle.left - this.speedInTime[this.moveTime], rectangle.top);
    }

    protected boolean canMoveUp() {
        return canMove(rectangle.left,rectangle.top - this.speedInTime[this.moveTime]);
    }

    protected boolean canMoveDown() {
        return canMove(rectangle.left,rectangle.top + this.speedInTime[this.moveTime]);
    }

    protected boolean canMoveToDirection(int direction) {
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

    protected boolean canMoveToActualDirection() {
        return canMoveToDirection(this.actualDirection);
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

    private int[] getSpeeds(int tileSize) {
        int[] speeds = new int[10];
        int basicSpeed = tileSize / 10;
        Arrays.fill(speeds, basicSpeed);
        if (tileSize%10 == 0) {
            return speeds;
        }
        int sum = 0;
        for (int speed : speeds) {
            sum += speed;
        }
        int[] indexes = new int[]{0, 5, 7, 2, 9, 4, 8, 3, 6, 1};
        for (int index : indexes) {
            speeds[index] += 1;
            sum += 1;
            if (sum == tileSize) {
                return speeds;
            }
        }

        return speeds;
    }

    protected void moveRight() {
        rectangle.left += this.speedInTime[this.moveTime];
        rectangle.right += this.speedInTime[this.moveTime];
        this.moveTime++;
    }

    protected void moveLeft() {
        rectangle.left -= this.speedInTime[this.moveTime];
        rectangle.right -= this.speedInTime[this.moveTime];
        this.moveTime++;
    }

    protected void moveUp() {
        rectangle.top -= this.speedInTime[this.moveTime];
        rectangle.bottom -= this.speedInTime[this.moveTime];
        this.moveTime++;
    }

    protected void moveDown() {
        rectangle.top += this.speedInTime[this.moveTime];
        rectangle.bottom += this.speedInTime[this.moveTime];
        this.moveTime++;
    }

    protected void move() {
        switch (this.actualDirection) {
            case DIRECTION_RIGHT:
                if (canMoveRight()) { moveRight(); }
                break;
            case DIRECTION_LEFT:
                if (canMoveLeft()) { moveLeft(); }
                break;
            case DIRECTION_UP:
                if (canMoveUp()) { moveUp(); }
                break;
            case DIRECTION_DOWN:
                if (canMoveDown()) { moveDown();}
                break;
            default:
                break;
        }

        this.moveTime = this.moveTime%10;
    }
}
