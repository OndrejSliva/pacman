package com.example.ondrej.pacman;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;

import java.util.List;

public class Player implements IDrawable {

    public static final int DIRECTION_RIGHT = 0;
    public static final int DIRECTION_LEFT = 2;
    public static final int DIRECTION_UP = 1;
    public static final int DIRECTION_DOWN = 3;
    private static final int[] DIRECTIONS = new int[]{
        DIRECTION_RIGHT, DIRECTION_LEFT, DIRECTION_UP, DIRECTION_DOWN
    };


    private Rect rectangle;
    private int color;

    private int actualDirection = DIRECTION_UP;
    private int nextDirection = DIRECTION_UP;
    private List<Wall> walls;
    private int speed = 5;

    private Bitmap[] pacmanOpen;
    private Bitmap[] pacmanClose;

    //otevírání pusy
    private boolean open = false;
    private int openCloseTimer = 0;

    public Player (List<Wall> walls, int size, Bitmap pacmanOpen, Bitmap pacmanClose) {


        this.rectangle = new Rect(0, 0, size, size);
        this.color = Color.YELLOW;
        this.walls = walls;
        this.loadAllPacmanImages(pacmanOpen, pacmanClose);
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(color);
        Bitmap pacman;
        if (this.open) {
            pacman = pacmanOpen[this.actualDirection];
        } else {
            pacman = pacmanClose[this.actualDirection];
        }
        canvas.drawBitmap(pacman, rectangle.left, rectangle.top, null);
        /*canvas.draw
        canvas.drawRect(rectangle, paint);*/
    }

    private void loadAllPacmanImages(Bitmap pacmanOpen, Bitmap pacmanClose) {
        this.pacmanOpen = new Bitmap[4];
        this.pacmanClose = new Bitmap[4];

        this.pacmanOpen[DIRECTION_RIGHT] = pacmanOpen;
        this.pacmanClose[DIRECTION_RIGHT] = pacmanClose;

        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        this.pacmanOpen[DIRECTION_DOWN] = Bitmap.createBitmap(pacmanOpen, 0, 0, pacmanOpen.getWidth(), pacmanOpen.getHeight(), matrix, true);
        this.pacmanClose[DIRECTION_DOWN] = Bitmap.createBitmap(pacmanClose, 0, 0, pacmanClose.getWidth(), pacmanClose.getHeight(), matrix, true);
        matrix = new Matrix();
        matrix.postRotate(270);
        this.pacmanOpen[DIRECTION_UP] = Bitmap.createBitmap(pacmanOpen, 0, 0, pacmanOpen.getWidth(), pacmanOpen.getHeight(), matrix, true);
        this.pacmanClose[DIRECTION_UP] = Bitmap.createBitmap(pacmanClose, 0, 0, pacmanClose.getWidth(), pacmanClose.getHeight(), matrix, true);
        matrix = new Matrix();
        matrix.preScale(-1, 1);
        this.pacmanOpen[DIRECTION_LEFT] = Bitmap.createBitmap(pacmanOpen, 0, 0, pacmanOpen.getWidth(), pacmanOpen.getHeight(), matrix, true);
        this.pacmanClose[DIRECTION_LEFT] = Bitmap.createBitmap(pacmanClose, 0, 0, pacmanClose.getWidth(), pacmanClose.getHeight(), matrix, true);
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
        //slows pacmans mouth open and close
        if(this.openCloseTimer++ == 15){
            this.open = !this.open;
            this.openCloseTimer = 0;
        }
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
