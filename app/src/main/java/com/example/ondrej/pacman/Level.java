package com.example.ondrej.pacman;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.List;

public class Level {

    private Player player;
    private Resources resources;
    private List<Wall> walls;
    private int width;
    private int heigth;


    public Level(Resources resources, int width, int heigth) {
        this.width = width;
        this.heigth = heigth;
        this.resources = resources;
        this.walls = new ArrayList<>();
        this.initLevel();

    }

    public void initLevel() {
        Drawable d = resources.getDrawable(R.drawable.map);
        Bitmap bitmap = ((BitmapDrawable)d).getBitmap();

        int tileSize = this.width / bitmap.getWidth();

        ConstantHelper.TILE_SIZE = tileSize;
        this.player = new Player(tileSize);

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int val = bitmap.getPixel(x, y);

                if(val == 0xFF000000){  //tile
                    this.walls.add(new Wall(x*ConstantHelper.TILE_SIZE, y*ConstantHelper.TILE_SIZE));
                } else if (val == 0xFF0002FF){ //player
                    this.player.setPosition(x * ConstantHelper.TILE_SIZE, y * ConstantHelper.TILE_SIZE);;
                }
            }
        }
    }

    public void drawFirst(Canvas canvas) {
        //TODO delete ?
        /*//pozadí plátna
        canvas.drawColor(Color.BLACK);

        for (Wall w: walls) {
            w.draw(canvas);
            break;
        }*/
    }

    public void draw(Canvas canvas) {
        //pozadí plátna
        canvas.drawColor(Color.BLACK);
        for (Wall w: walls) {
            w.draw(canvas);
        }
        player.draw(canvas);
    }

    public void update() {
        this.player.move();
    }

    public void setPlayerDirection(int direction) {
        this.player.setDirection(direction);
    }

}
