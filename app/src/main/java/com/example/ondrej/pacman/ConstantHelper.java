package com.example.ondrej.pacman;

import android.graphics.Point;
import android.view.Display;

public class ConstantHelper {
    //TODO delete až nebude potřeba

    public static int TILE_SIZE;
    public static int PANEL_SIZE;
    public static int LIVES_Y_POS;
    public static final int POINTS_PER_FOOD_EATEN = 10;

    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;

    public static void setScreenSize(int width, int height) {
        SCREEN_WIDTH = width;
        SCREEN_HEIGHT = height;
    }

    public static void initGameSizesByMapWidth(int mapWidth) {
        TILE_SIZE = ConstantHelper.SCREEN_WIDTH / mapWidth;
        PANEL_SIZE = (int)(TILE_SIZE * 1.5) ;
        LIVES_Y_POS = (int)(ConstantHelper.TILE_SIZE * 0.25);
    }
}
