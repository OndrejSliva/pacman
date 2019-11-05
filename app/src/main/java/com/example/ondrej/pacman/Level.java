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

    private List<Enemy> enemies;

    private Bitmap pacmanOpen;
    private Bitmap pacmanOpenResized;
    private Bitmap pacmanClose;
    private Bitmap pacmanCloseResized;
    private Bitmap enemyRed;
    private Bitmap enemyRedResized;
    private Bitmap enemyBlue;
    private Bitmap enemyBlueResized;
    private Bitmap enemyYellow;
    private Bitmap enemyYellowResized;
    private Bitmap enemyPurple;
    private Bitmap enemyPurpleResized;
    private Bitmap enemiesBitmap[];

    public Level(Resources resources, int width, int heigth) {
        this.width = width;
        this.heigth = heigth;
        this.resources = resources;
        this.walls = new ArrayList<>();
        this.foods = new ArrayList<>();
        this.enemies = new ArrayList<>();
        this.initLevel();
    }

    private void loadImages(int tileSize) {
        pacmanOpen = BitmapFactory.decodeResource(resources, R.drawable.pacman_open);
        pacmanOpenResized = Bitmap.createScaledBitmap(pacmanOpen, tileSize, tileSize, false);
        pacmanClose = BitmapFactory.decodeResource(resources, R.drawable.pacman_close);
        pacmanCloseResized = Bitmap.createScaledBitmap(pacmanClose, tileSize, tileSize, false);
        enemyRed = BitmapFactory.decodeResource(resources, R.drawable.enemy_red);
        enemyRedResized = Bitmap.createScaledBitmap(enemyRed, tileSize, tileSize, false);
        enemyBlue = BitmapFactory.decodeResource(resources, R.drawable.enemy_blue);
        enemyBlueResized = Bitmap.createScaledBitmap(enemyBlue, tileSize, tileSize, false);
        enemyYellow = BitmapFactory.decodeResource(resources, R.drawable.enemy_yellow);
        enemyYellowResized = Bitmap.createScaledBitmap(enemyYellow, tileSize, tileSize, false);
        enemyPurple = BitmapFactory.decodeResource(resources, R.drawable.enemy_purple);
        enemyPurpleResized = Bitmap.createScaledBitmap(enemyPurple, tileSize, tileSize, false);
        enemiesBitmap = new Bitmap[]{enemyRedResized, enemyBlueResized, enemyYellowResized, enemyPurpleResized};
    }

    public void initLevel() {
        //int tileSize = this.width / 20;
        /*Drawable d = resources.getDrawable(R.drawable.map);
        Bitmap bitmap = ((BitmapDrawable)d).getBitmap();*/
        Bitmap mapBitmap = BitmapFactory.decodeResource(resources, R.drawable.map);
        Bitmap mapResized = Bitmap.createScaledBitmap(mapBitmap, 20, 15, false);


        int tileSize = this.width / mapResized.getWidth();
        //int tileSize = 50;
        this.loadImages(tileSize);

        ConstantHelper.TILE_SIZE = tileSize;

        for (int x = 0; x < 20; x++) {
            for (int y = 0; y < 15; y++) {
                int val = map[y][x];

                if(val == 1){  //tile
                    this.walls.add(new Wall(x*ConstantHelper.TILE_SIZE, y * ConstantHelper.TILE_SIZE));
                } else if (val == 2){ //player
                    this.player = new Player(walls, tileSize, pacmanOpenResized, pacmanCloseResized, x * ConstantHelper.TILE_SIZE, y * ConstantHelper.TILE_SIZE);
                } else if (val == 3) {
                    this.enemies.add(new Enemy(tileSize, walls, enemiesBitmap[this.enemies.size() % 4], x * ConstantHelper.TILE_SIZE, y * ConstantHelper.TILE_SIZE));
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

    private void resetToBasePosition() {
        this.player.setBasePosition();
        for (Enemy enemy : enemies) {
            enemy.setBasePosition();
        }

        for (int x = 0; x < 20; x++) {
            for (int y = 0; y < 15; y++) {
                int val = map[y][x];

                if(val == 0 ||  val == 3){  //tile
                    this.foods.add(new Food(x*ConstantHelper.TILE_SIZE, y*ConstantHelper.TILE_SIZE));
                }
            }
        }
    }

    public void draw(Canvas canvas) {
        for (Food food : foods) {
            food.draw(canvas);
        }
        for (Wall wall : walls) {
            wall.draw(canvas);
        }
        for (Enemy enemy : enemies) {
            enemy.draw(canvas);
        }
        player.draw(canvas);
        System.out.println();
    }

    public void update() {
        this.player.update();
        this.checkFoodCollision();
        for (Enemy enemy : enemies) {
            enemy.update();
        }
        if (this.allFoodIsEaten()) {
            this.resetToBasePosition();
        }
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

    private boolean allFoodIsEaten() {
        return this.foods.size() == 0;
    }

}
