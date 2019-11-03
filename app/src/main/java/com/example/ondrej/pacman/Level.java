package com.example.ondrej.pacman;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.List;

public class Level {

    private int [][] map = new int[][]{
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
        {1, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1},
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 1},
        {1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1},
        {1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1},
        {1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1},
        {1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 1, 0, 0, 0, 1},
        {1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1},
        {1, 0, 1, 0, 0, 0, 0, 0, 1, 2, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1},
        {1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1},
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 1},
        {1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1},
        {1, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
    };

    private Player player;
    private Resources resources;
    private List<Wall> walls;
    private int width;
    private int heigth;
    private List<Food> foods;

    public Level(Resources resources, int width, int heigth) {
        this.width = width;
        this.heigth = heigth;
        this.resources = resources;
        this.walls = new ArrayList<>();
        this.foods = new ArrayList<>();
        this.initLevel();
    }

    public void initLevel() {
        //int tileSize = this.width / 20;
        /*Drawable d = resources.getDrawable(R.drawable.map);
        Bitmap bitmap = ((BitmapDrawable)d).getBitmap();*/
        Bitmap mapBitmap = BitmapFactory.decodeResource(resources, R.drawable.map);
        Bitmap mapResized = Bitmap.createScaledBitmap(mapBitmap, 20, 15, false);


        int tileSize = this.width / mapResized.getWidth();
        //int tileSize = 50;

        //todo vyzkoušet resize i na mapu
        Bitmap pacmanOpen = BitmapFactory.decodeResource(resources, R.drawable.pacman_open);
        Bitmap pacmanOpenResized = Bitmap.createScaledBitmap(pacmanOpen, tileSize, tileSize, false);
        Bitmap pacmanClose = BitmapFactory.decodeResource(resources, R.drawable.pacman_close);
        Bitmap pacmanCloseResized = Bitmap.createScaledBitmap(pacmanClose, tileSize, tileSize, false);


        ConstantHelper.TILE_SIZE = tileSize;
        this.player = new Player(walls, tileSize, pacmanOpenResized, pacmanCloseResized);

        for (int x = 0; x < 20; x++) {
            for (int y = 0; y < 15; y++) {
                int val = map[y][x];

                if(val == 1){  //tile
                    this.walls.add(new Wall(x*ConstantHelper.TILE_SIZE, y*ConstantHelper.TILE_SIZE));
                } else if (val == 2){ //player
                    this.player.setPosition(x * ConstantHelper.TILE_SIZE, y * ConstantHelper.TILE_SIZE);
                } else if (val == 3) {
                    this.foods.add(new Food(x*ConstantHelper.TILE_SIZE, y*ConstantHelper.TILE_SIZE));
                } else {
                    this.foods.add(new Food(x*ConstantHelper.TILE_SIZE, y*ConstantHelper.TILE_SIZE));
                }
            }
        }

        /*int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int val = bitmap.getPixel(x, y);

                if(val == 0xFF000000){  //tile
                    this.walls.add(new Wall(x*ConstantHelper.TILE_SIZE, y*ConstantHelper.TILE_SIZE));
                } else if (val == 0xFF0002FF){ //player
                    this.player.setPosition(x * ConstantHelper.TILE_SIZE, y * ConstantHelper.TILE_SIZE);
                } else {
                    this.foods.add(new Food(x*ConstantHelper.TILE_SIZE, y*ConstantHelper.TILE_SIZE));
                }
            }
        }*/
    }

    public void draw(Canvas canvas) {
        //pozadí plátna
        //canvas.drawColor(Color.BLACK);
        for (Food f: foods) {
            f.draw(canvas);
        }
        for (Wall w: walls) {
            w.draw(canvas);
        }
        player.draw(canvas);
        System.out.println();
    }

    public void update() {
        this.player.update();
        this.checkFoodCollision();
    }

    private void checkFoodCollision() {
        Rect playerRectangle = this.player.getRectangle();
        for (int i = 0; i < this.foods.size(); i++) {
            if (this.foods.get(i).colides(playerRectangle)) {
                this.foods.remove(i);
                break;
            }
        }
    }

    public void setPlayerNextDirection(int direction) {
        this.player.setNextDirection(direction);
    }

}
