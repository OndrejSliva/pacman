package com.example.ondrej.pacman;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.List;

public class Player extends Character {

    private int nextDirection = DIRECTION_UP;

    private Bitmap[] pacmanOpen;
    private Bitmap[] pacmanClose;

    //otevírání pusy
    private boolean open = true;
    private int openCloseTimer = 0;

    public Player (List<Wall> walls, int x, int y) {
        super(walls, x, y);
        this.initAllPacmanImages();
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

    private void initAllPacmanImages() {
        this.pacmanOpen = new Bitmap[4];
        this.pacmanClose = new Bitmap[4];

        this.pacmanOpen[DIRECTION_RIGHT] = ImageHelper.getPacmanOpenRight();
        this.pacmanClose[DIRECTION_RIGHT] = ImageHelper.getPacmanCloseRight();
        this.pacmanOpen[DIRECTION_DOWN] = ImageHelper.getPacmanOpenDown();
        this.pacmanClose[DIRECTION_DOWN] = ImageHelper.getPacmanCloseDown();
        this.pacmanOpen[DIRECTION_UP] = ImageHelper.getPacmanOpenUp();
        this.pacmanClose[DIRECTION_UP] = ImageHelper.getPacmanCloseUp();
        this.pacmanOpen[DIRECTION_LEFT] = ImageHelper.getPacmanOpenLeft();
        this.pacmanClose[DIRECTION_LEFT] = ImageHelper.getPacmanCloseLeft();
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
