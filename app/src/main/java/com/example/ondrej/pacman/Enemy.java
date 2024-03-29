package com.example.ondrej.pacman;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.List;
import java.util.Random;

public class Enemy extends Character {

    private Bitmap image;

    //generátor pro generování směrů
    private Random randomGenerator;

    //poslední směr
    private int lastDirection;


    public Enemy(List<Wall> walls, Bitmap enemyBitmap, int x, int y) {
        super(walls, x, y);
        this.randomGenerator = new Random();
        this.image = enemyBitmap;
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

    private void setOtherDirectionOnCross(){
        int newDirection = -1;
        boolean canMoveThere = false;

        while(!canMoveThere || newDirection == this.getOppositeDirection()){
            newDirection = randomGenerator.nextInt(4);
            canMoveThere = this.canMoveToDirection(newDirection);
        }
        this.actualDirection = newDirection;
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

    public boolean colides(Rect rect) {
        return this.rectangle.left < rect.right && rect.left < this.rectangle.right && this.rectangle.top < rect.bottom && rect.top < this.rectangle.bottom;
    }
}
