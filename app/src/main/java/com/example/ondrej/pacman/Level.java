package com.example.ondrej.pacman;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
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

    private int [][] map;

    private Player player;
    private Resources resources;
    private List<Wall> walls;
    private List<Food> foods;

    private List<Enemy> enemies;

    private Bitmap enemiesBitmap[];
    private SoundAgent soundAgent;

    private int mapWidth;
    private int mapHeight;

    private int score;
    private int lives = 1;

    private int status;

    private boolean firstEatSound = true;

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
        enemiesBitmap = new Bitmap[]{ImageHelper.getEnemyRed(), ImageHelper.getEnemyBlue(), ImageHelper.getEnemyYellow(), ImageHelper.getEnemyPurple()};
    }

    public void initLevel() {

        ConstantHelper.initGameSizesByMapWidth(this.mapWidth);
        ImageHelper.initLevelImages(resources);
        this.loadImages();

        for (int x = 0; x < this.mapWidth; x++) {
            for (int y = 0; y < this.mapHeight; y++) {
                int val = map[y][x];

                if(val == 1){  //tile
                    this.walls.add(new Wall(x*ConstantHelper.TILE_SIZE, y * ConstantHelper.TILE_SIZE));
                } else if (val == 2){ //player
                    this.player = new Player(walls, x * ConstantHelper.TILE_SIZE, y * ConstantHelper.TILE_SIZE);
                } else if (val == 3) {
                    this.enemies.add(new Enemy(walls, enemiesBitmap[this.enemies.size() % 4], x * ConstantHelper.TILE_SIZE, y * ConstantHelper.TILE_SIZE));
                    this.foods.add(new Food(x*ConstantHelper.TILE_SIZE, y*ConstantHelper.TILE_SIZE));
                } else {
                    this.foods.add(new Food(x*ConstantHelper.TILE_SIZE, y*ConstantHelper.TILE_SIZE));
                }
            }
        }
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
                this.processOnCollision();
            }
        }
    }

    public void checkEndGame() {
        if (this.lives == 0) {
            this.status = STATUS_END;
            return;
        }
        this.resetOnCollision();
    }

    private void resetOnCollision() {
        for(Enemy enemy : enemies){
            enemy.setBasePosition();
        }

        this.player.setBasePosition();
        this.soundAgent.playBeginMusic();
    }

    private void processOnCollision() {
        this.status = STATUS_PAUSE;

        this.lives--;
        soundAgent.playDeathSound();
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
                canvas.drawBitmap(ImageHelper.getPacmanOpenRight(), ConstantHelper.SCREEN_WIDTH - (int)(ConstantHelper.TILE_SIZE * 1.25), ConstantHelper.LIVES_Y_POS, null);
                break;
            case 3:
                canvas.drawBitmap(ImageHelper.getPacmanOpenRight(), ConstantHelper.SCREEN_WIDTH - (int)(ConstantHelper.TILE_SIZE * 3.3), ConstantHelper.LIVES_Y_POS, null);
            case 2:
                canvas.drawBitmap(ImageHelper.getPacmanOpenRight(), ConstantHelper.SCREEN_WIDTH - (int)(ConstantHelper.TILE_SIZE * 2.2), ConstantHelper.LIVES_Y_POS, null);
            case 1:
                canvas.drawBitmap(ImageHelper.getPacmanOpenRight(), ConstantHelper.SCREEN_WIDTH - (int)(ConstantHelper.TILE_SIZE * 1.1), ConstantHelper.LIVES_Y_POS, null);
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
                if (firstEatSound) { soundAgent.playEatSound(); } else { soundAgent.playEatSound2(); }
                firstEatSound = !firstEatSound;
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

    public int getScore() {
        return this.score;
    }

}
