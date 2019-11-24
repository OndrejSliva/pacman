package com.example.ondrej.pacman;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

import java.util.List;

public class Player extends Character {

    private int nextDirection = DIRECTION_UP;

    private Bitmap[] pacmanOpen;
    private Bitmap[] pacmanClose;

    //otevírání pusy
    private boolean open = true;
    private int openCloseTimer = 0;

    public Player (List<Wall> walls,Bitmap pacmanOpen, Bitmap pacmanClose, int x, int y) {
        super(walls, x, y);
        this.loadAllPacmanImages(pacmanOpen, pacmanClose);
    }

    public void setBasePosition() {
        super.setBasePosition();
        this.open = true;
    }

    @Override
    public void draw(Canvas canvas) {
        Bitmap pacman;
        if (this.open) {
            pacman = pacmanOpen[this.actualDirection];
        } else {
            pacman = pacmanClose[this.actualDirection];
        }
        canvas.drawBitmap(pacman, rectangle.left, rectangle.top, null);
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
            if (this.getOppositeDirection() == nextDirection) {
                if (this.moveTime != 0) {
                    this.decrementTime = this.moveTime;
                    this.moveTime -= 1;
                }
            }
            this.actualDirection = this.nextDirection;
        }
    }

    //@Override
    public void update() {
        this.setActualDirectionToNextIfCanMoveThere();
        this.moveByActualDirection();
        //slows pacmans mouth open and close
        if(this.openCloseTimer++ == 15){
            this.open = !this.open;
            this.openCloseTimer = 0;
        }
    }
}
