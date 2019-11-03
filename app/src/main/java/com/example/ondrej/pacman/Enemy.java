package com.example.ondrej.pacman;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.List;
import java.util.Random;

public class Enemy extends Character {

    private Bitmap image;

    //generátor pro generování směrů
    private Random randomGenerator;

    //poslední směr
    private int lastDirection;

    public Enemy(int tileSize, List<Wall> walls, Bitmap enemyBitmap, int x, int y) {
        super(tileSize, walls);
        this.randomGenerator = new Random();
        this.image = enemyBitmap;
        this.rectangle.left = x;
        this.rectangle.top = y;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, rectangle.left, rectangle.top, null);
    }

    private boolean isOnCross() {
        int directionsAvaibleForMove = 0;

        if (canMoveLeft()) directionsAvaibleForMove++;
        if (canMoveRight()) directionsAvaibleForMove++;
        if (canMoveUp()) directionsAvaibleForMove++;
        if (canMoveDown()) directionsAvaibleForMove++;

        return directionsAvaibleForMove > 2;
    }

    public void update() {
        this.moveRandom();

    }

    private void moveRandom(){
        if(this.isOnCross()){
            this.setOtherDirectionOnCross();
        } else if(!this.canMoveToActualDirection()) {
            this.setOtherDirection();
        }
        this.moveByActualDirection();
    }

    private void moveByActualDirection(){
        switch (this.actualDirection){
            case Enemy.DIRECTION_RIGHT:
                this.moveRight();
                this.lastDirection = Enemy.DIRECTION_RIGHT;
                break;
            case Enemy.DIRECTION_LEFT:
                this.moveLeft();
                this.lastDirection = Enemy.DIRECTION_LEFT;
                break;
            case Enemy.DIRECTION_UP:
                this.moveUp();
                this.lastDirection = Enemy.DIRECTION_UP;
                break;
            case Enemy.DIRECTION_DOWN:
                this.moveDown();
                this.lastDirection = Enemy.DIRECTION_DOWN;
                break;
        }

        this.moveTime = this.moveTime%10;
    }

    private void setOtherDirectionOnCross(){
        int newDirection = -1;
        boolean canMoveThere = false;

        while(!canMoveThere || newDirection == this.getOppositeDirection()){
            newDirection = randomGenerator.nextInt(4);
            canMoveThere = this.canMoveToDirection(newDirection);
        }
        this.actualDirection = newDirection;
    }

    private int getOppositeDirection(){
        switch (this.actualDirection){
            case Enemy.DIRECTION_RIGHT:
                return Enemy.DIRECTION_LEFT;
            case Enemy.DIRECTION_LEFT:
                return Enemy.DIRECTION_RIGHT;
            case Enemy.DIRECTION_UP:
                return Enemy.DIRECTION_DOWN;
            case Enemy.DIRECTION_DOWN:
                return Enemy.DIRECTION_UP;
        }
        return -1;
    }

    private void setOtherDirection(){
        int newDirection = -1;
        boolean canMoveThere = false;

        while(!canMoveThere || newDirection == this.getOppositeDirection()){
            newDirection = randomGenerator.nextInt(4);
            canMoveThere = this.canMoveToDirection(newDirection);
        }
        this.actualDirection = newDirection;
    }
}
