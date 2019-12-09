package com.example.ondrej.pacman;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

public class ImageHelper {

    private static Bitmap arrowDown;
    private static Bitmap arrowRight;
    private static Bitmap arrowUp;
    private static Bitmap arrowLeft;

    private static Bitmap pacmanOpenDown;
    private static Bitmap pacmanOpenRight;
    private static Bitmap pacmanOpenUp;
    private static Bitmap pacmanOpenLeft;

    private static Bitmap pacmanCloseDown;
    private static Bitmap pacmanCloseRight;
    private static Bitmap pacmanCloseUp;
    private static Bitmap pacmanCloseLeft;

    private static Bitmap enemyRed;
    private static Bitmap enemyBlue;
    private static Bitmap enemyYellow;
    private static Bitmap enemyPurple;

    private static int lastTileSize;

    public static void initSharedImages(Resources resources) {
        Bitmap arrow = BitmapFactory.decodeResource(resources, R.drawable.arrow);
        arrowRight = Bitmap.createScaledBitmap(arrow, ConstantHelper.SCREEN_WIDTH / 3, ConstantHelper.SCREEN_WIDTH / 3, false);

        Matrix matrixDown = new Matrix();
        matrixDown.postRotate(90);
        arrowDown = Bitmap.createBitmap(arrowRight, 0, 0, arrowRight.getWidth(), arrowRight.getHeight(), matrixDown, true);

        Matrix matrixLeft = new Matrix();
        matrixLeft.postRotate(180);
        arrowLeft = Bitmap.createBitmap(arrowRight, 0, 0, arrowRight.getWidth(), arrowRight.getHeight(), matrixLeft, true);

        Matrix matrixUp = new Matrix();
        matrixUp.postRotate(270);
        arrowUp = Bitmap.createBitmap(arrowRight, 0, 0, arrowRight.getWidth(), arrowRight.getHeight(), matrixUp, true);
    }

    public static void initLevelImages(Resources resources) {
        //pokud je velikost oplíčka stejná, nepřekresluje
        if (lastTileSize == ConstantHelper.TILE_SIZE) {
            return;
        }
        lastTileSize = ConstantHelper.TILE_SIZE;

        Bitmap pacmanOpen = BitmapFactory.decodeResource(resources, R.drawable.pacman_open);
        pacmanOpenRight = Bitmap.createScaledBitmap(pacmanOpen, ConstantHelper.TILE_SIZE, ConstantHelper.TILE_SIZE, false);
        Bitmap pacmanClose = BitmapFactory.decodeResource(resources, R.drawable.pacman_close);
        pacmanCloseRight = Bitmap.createScaledBitmap(pacmanClose, ConstantHelper.TILE_SIZE, ConstantHelper.TILE_SIZE, false);

        Matrix matrixDown = new Matrix();
        matrixDown.postRotate(90);
        pacmanOpenDown = Bitmap.createBitmap(pacmanOpenRight, 0, 0, pacmanOpenRight.getWidth(), pacmanOpenRight.getHeight(), matrixDown, true);
        pacmanCloseDown = Bitmap.createBitmap(pacmanCloseRight, 0, 0, pacmanCloseRight.getWidth(), pacmanCloseRight.getHeight(), matrixDown, true);

        Matrix matrixUp = new Matrix();
        matrixUp.postRotate(270);
        pacmanOpenUp = Bitmap.createBitmap(pacmanOpenRight, 0, 0, pacmanOpenRight.getWidth(), pacmanOpenRight.getHeight(), matrixUp, true);
        pacmanCloseUp = Bitmap.createBitmap(pacmanCloseRight, 0, 0, pacmanCloseRight.getWidth(), pacmanCloseRight.getHeight(), matrixUp, true);

        Matrix matrixLeft = new Matrix();
        matrixLeft.preScale(-1, 1);
        pacmanOpenLeft = Bitmap.createBitmap(pacmanOpenRight, 0, 0, pacmanOpenRight.getWidth(), pacmanOpenRight.getHeight(), matrixLeft, true);
        pacmanCloseLeft = Bitmap.createBitmap(pacmanCloseRight, 0, 0, pacmanCloseRight.getWidth(), pacmanCloseRight.getHeight(), matrixLeft, true);


        Bitmap enemyRedRaw = BitmapFactory.decodeResource(resources, R.drawable.enemy_red);
        enemyRed = Bitmap.createScaledBitmap(enemyRedRaw, ConstantHelper.TILE_SIZE, ConstantHelper.TILE_SIZE, false);

        Bitmap enemyBlueRaw = BitmapFactory.decodeResource(resources, R.drawable.enemy_blue);
        enemyBlue = Bitmap.createScaledBitmap(enemyBlueRaw, ConstantHelper.TILE_SIZE, ConstantHelper.TILE_SIZE, false);

        Bitmap enemyYellowRaw = BitmapFactory.decodeResource(resources, R.drawable.enemy_yellow);
        enemyYellow = Bitmap.createScaledBitmap(enemyYellowRaw, ConstantHelper.TILE_SIZE, ConstantHelper.TILE_SIZE, false);

        Bitmap enemyPurpleRaw = BitmapFactory.decodeResource(resources, R.drawable.enemy_purple);
        enemyPurple = Bitmap.createScaledBitmap(enemyPurpleRaw, ConstantHelper.TILE_SIZE, ConstantHelper.TILE_SIZE, false);
    }

    public static Bitmap getArrowDown() {
        return arrowDown;
    }

    public static Bitmap getArrowUp() {
        return arrowUp;
    }

    public static Bitmap getArrowLeft() {
        return arrowLeft;
    }

    public static Bitmap getArrowRight() {
        return arrowRight;
    }

    public static Bitmap getPacmanOpenDown() {
        return pacmanOpenDown;
    }

    public static Bitmap getPacmanOpenRight() {
        return pacmanOpenRight;
    }

    public static Bitmap getPacmanOpenUp() {
        return pacmanOpenUp;
    }

    public static Bitmap getPacmanOpenLeft() {
        return pacmanOpenLeft;
    }

    public static Bitmap getPacmanCloseDown() {
        return pacmanCloseDown;
    }

    public static Bitmap getPacmanCloseRight() {
        return pacmanCloseRight;
    }

    public static Bitmap getPacmanCloseUp() {
        return pacmanCloseUp;
    }

    public static Bitmap getPacmanCloseLeft() {
        return pacmanCloseLeft;
    }

    public static Bitmap getEnemyRed() {
        return enemyRed;
    }

    public static Bitmap getEnemyBlue() {
        return enemyBlue;
    }

    public static Bitmap getEnemyYellow() {
        return enemyYellow;
    }

    public static Bitmap getEnemyPurple() {
        return enemyPurple;
    }
}
