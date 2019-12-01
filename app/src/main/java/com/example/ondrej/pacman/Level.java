package com.example.ondrej.pacman;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Level {

    public static int STATUS_RUN = 1;
    public static int STATUS_PAUSE = 2;
    public static int STATUS_END = 3;

    /*private int [][] map = new int[][]{
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 1},
        {1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1},
        {1, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1},
        {1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1},
        {1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1},
        {1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1},
        {1, 0, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 0, 0, 1},
        {1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1},
        {1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1},
        {1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1},
        {1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1},
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 1},
        {1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1},
        {1, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
    };*/

    private int [][] map;

    private Player player;
    private Resources resources;
    private List<Wall> walls;
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
    private SoundAgent soundAgent;

    private int mapWidth;
    private int mapHeight;

    private int score;
    private int lives = 3;

    private int status;

    public Level(Context context, SoundAgent soundAgent, int levelId) {
        this.resources = context.getResources();
        this.walls = new ArrayList<>();
        this.foods = new ArrayList<>();
        this.enemies = new ArrayList<>();
        this.score = 0;
        this.loadMap(context, levelId);
        this.initLevel();
        this.soundAgent = soundAgent;
        this.status = STATUS_PAUSE;
    }

    private void loadMap(Context context, int levelId) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(context.getAssets().open("levels.txt")));

            int line = 0;
            String mLine;
            while ((mLine = bufferedReader.readLine()) != null) {
                if (line++ == levelId) {

                    String[] values = mLine.split(",");

                    this.mapWidth = Integer.valueOf(values[0]);
                    this.mapHeight = Integer.valueOf(values[1]);
                    map = new int[mapHeight][mapWidth];

                    for (int i = 2; i < values.length; i++) {
                        int value = Integer.valueOf(values[i]);
                        map[(i - 2) / mapWidth][(i - 2) % mapWidth] = value;
                    }
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadImages() {
        pacmanOpen = BitmapFactory.decodeResource(resources, R.drawable.pacman_open);
        pacmanOpenResized = Bitmap.createScaledBitmap(pacmanOpen, ConstantHelper.TILE_SIZE, ConstantHelper.TILE_SIZE, false);
        pacmanClose = BitmapFactory.decodeResource(resources, R.drawable.pacman_close);
        pacmanCloseResized = Bitmap.createScaledBitmap(pacmanClose, ConstantHelper.TILE_SIZE, ConstantHelper.TILE_SIZE, false);
        enemyRed = BitmapFactory.decodeResource(resources, R.drawable.enemy_red);
        enemyRedResized = Bitmap.createScaledBitmap(enemyRed, ConstantHelper.TILE_SIZE, ConstantHelper.TILE_SIZE, false);
        enemyBlue = BitmapFactory.decodeResource(resources, R.drawable.enemy_blue);
        enemyBlueResized = Bitmap.createScaledBitmap(enemyBlue, ConstantHelper.TILE_SIZE, ConstantHelper.TILE_SIZE, false);
        enemyYellow = BitmapFactory.decodeResource(resources, R.drawable.enemy_yellow);
        enemyYellowResized = Bitmap.createScaledBitmap(enemyYellow, ConstantHelper.TILE_SIZE, ConstantHelper.TILE_SIZE, false);
        enemyPurple = BitmapFactory.decodeResource(resources, R.drawable.enemy_purple);
        enemyPurpleResized = Bitmap.createScaledBitmap(enemyPurple, ConstantHelper.TILE_SIZE, ConstantHelper.TILE_SIZE, false);
        enemiesBitmap = new Bitmap[]{enemyRedResized, enemyBlueResized, enemyYellowResized, enemyPurpleResized};
    }

    public void initLevel() {
        //int tileSize = this.width / 20;
        /*Drawable d = resources.getDrawable(R.drawable.map);
        Bitmap bitmap = ((BitmapDrawable)d).getBitmap();*/
        //Bitmap mapBitmap = BitmapFactory.decodeResource(resources, R.drawable.map);
        //Bitmap mapResized = Bitmap.createScaledBitmap(mapBitmap, 20, 15, false);


        ConstantHelper.initGameSizesByMapWidth(this.mapWidth);
        this.loadImages();

        for (int x = 0; x < this.mapWidth; x++) {
            for (int y = 0; y < this.mapHeight; y++) {
                int val = map[y][x];

                if(val == 1){  //tile
                    this.walls.add(new Wall(x*ConstantHelper.TILE_SIZE, y * ConstantHelper.TILE_SIZE));
                } else if (val == 2){ //player
                    this.player = new Player(walls, pacmanOpenResized, pacmanCloseResized, x * ConstantHelper.TILE_SIZE, y * ConstantHelper.TILE_SIZE);
                } else if (val == 3) {
                    this.enemies.add(new Enemy(walls, enemiesBitmap[this.enemies.size() % 4], x * ConstantHelper.TILE_SIZE, y * ConstantHelper.TILE_SIZE));
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

        this.status = STATUS_PAUSE;
        this.soundAgent.playBeginMusic();
    }

    private void checkEnemiesCollision() {
        for (Enemy enemy : enemies) {
            if (enemy.colides(this.player.getRectangle())) {
                this.resetOnCollision();
            }
        }
    }

    private void resetOnCollision() {
        this.status = STATUS_PAUSE;

        this.lives--;
        if (this.lives == 0) {
            this.status = STATUS_END;
            return;
        }

        for(Enemy enemy : enemies){
            enemy.setBasePosition();
        }

        this.player.setBasePosition();
        this.soundAgent.playBeginMusic();
    }

    public boolean isEnd() {
        return this.status == STATUS_END;
    }

    public void setStatus(int status) {
        this.status = status;
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
        this.drawScore(canvas);
        this.drawLives(canvas);

    }

    public void update() {
        if (this.status == STATUS_RUN) {
            this.player.update();
            this.checkEnemiesCollision(); //TODO uncoment after testing
            this.checkFoodCollision();
            for (Enemy enemy : enemies) {
                enemy.update();
            }
            if (this.allFoodIsEaten()) {
                this.resetToBasePosition();
            }
        }
    }

    private void drawLives(Canvas canvas) {
        Paint p = new Paint();
        p.setColor(Color.WHITE);
        p.setTextSize(ConstantHelper.TILE_SIZE);

        switch (this.lives){
            default:
                canvas.drawText(Integer.toString(this.lives) + "x", ConstantHelper.SCREEN_WIDTH - (int)(ConstantHelper.TILE_SIZE * 2.5), (int)(ConstantHelper.TILE_SIZE * 1.25), p);
                canvas.drawBitmap(pacmanOpenResized, ConstantHelper.SCREEN_WIDTH - (int)(ConstantHelper.TILE_SIZE * 1.25), ConstantHelper.LIVES_Y_POS, null);
                break;
            case 3:
                canvas.drawBitmap(pacmanOpenResized, ConstantHelper.SCREEN_WIDTH - (int)(ConstantHelper.TILE_SIZE * 3.3), ConstantHelper.LIVES_Y_POS, null);
            case 2:
                canvas.drawBitmap(pacmanOpenResized, ConstantHelper.SCREEN_WIDTH - (int)(ConstantHelper.TILE_SIZE * 2.2), ConstantHelper.LIVES_Y_POS, null);
            case 1:
                canvas.drawBitmap(pacmanOpenResized, ConstantHelper.SCREEN_WIDTH - (int)(ConstantHelper.TILE_SIZE * 1.1), ConstantHelper.LIVES_Y_POS, null);
                break;
        };
    }

    private void drawScore(Canvas canvas) {
        Paint p = new Paint();
        p.setColor(Color.WHITE);
        p.setTextSize(ConstantHelper.TILE_SIZE);

        canvas.drawText(String.valueOf(this.score), (int)(ConstantHelper.TILE_SIZE * 0.25), (int)(ConstantHelper.TILE_SIZE * 1.25), p);
    }

    private void checkFoodCollision() {
        Rect playerRectangle = this.player.getRectangle();
        for (int i = 0; i < this.foods.size(); i++) {
            if (this.foods.get(i).colides(playerRectangle)) {
                this.foods.remove(i);
                this.score += ConstantHelper.POINTS_PER_FOOD_EATEN;
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
