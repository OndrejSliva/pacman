package com.example.ondrej.pacman;

public class ConstantHelper {
    //TODO delete až nebude potřeba

    public static int TILE_SIZE;
    public static int PANEL_SIZE;
    public static int WIDTH;
    public static int LIVES_Y_POS;
    public static final int POINTS_PER_FOOD_EATEN = 10;


    public static void init(int width, int height, int tileSize) {
        TILE_SIZE = tileSize;
        PANEL_SIZE = (int)(TILE_SIZE * 1.5) ;
        WIDTH = width;
        LIVES_Y_POS = (int)(ConstantHelper.TILE_SIZE * 0.25);
    }
}
