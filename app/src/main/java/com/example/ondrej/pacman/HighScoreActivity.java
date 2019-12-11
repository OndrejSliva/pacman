package com.example.ondrej.pacman;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class HighScoreActivity extends Activity {

    private SQLiteDatabase sqLiteDatabase;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.high_score_activity);
        databaseHelper = new DatabaseHelper(getApplicationContext());
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        if (!areLevelsLoaded()) {
            this.loadLevels();
        }
    }

    private boolean areLevelsLoaded() {
        LinearLayout levelsLayout = (LinearLayout) findViewById(R.id.high_scores_levels_layout);
        return levelsLayout.getChildCount() != 0;
    }


    private void loadLevels() {
        LinearLayout levelsLayout = (LinearLayout) findViewById(R.id.high_scores_levels_layout);
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getAssets().open("levels.txt")));
            int maxMiniLevelHeight = 0;

            ArrayList<LinearLayout> levelsLayoutList = new ArrayList<LinearLayout>();
            String mLine;
            int imgIdx = 0;
            while ((mLine = bufferedReader.readLine()) != null) {
                String[] values = mLine.split(",");

                int mapWidth = Integer.valueOf(values[0]);
                int mapHeight = Integer.valueOf(values[1]);

                int miniMapWidth = ConstantHelper.SCREEN_WIDTH / 4;
                int smallTileSize = miniMapWidth / mapWidth;
                int miniMapHeight = smallTileSize * mapHeight;
                maxMiniLevelHeight = miniMapHeight > maxMiniLevelHeight ? miniMapHeight : maxMiniLevelHeight;

                Bitmap bitmap = Bitmap.createBitmap(miniMapWidth, miniMapHeight, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);

                Paint blackPaint = new Paint();
                blackPaint.setColor(Color.BLACK);
                canvas.drawRect(0, 0, miniMapWidth, miniMapHeight, blackPaint);
                for (int i = 2; i < values.length; i++) {
                    int value = Integer.valueOf(values[i]);
                    int y = ((i - 2) / mapWidth) * smallTileSize;
                    int x = ((i - 2) % mapWidth) * smallTileSize;

                    if (value == 1) {
                        Paint paint = new Paint();
                        paint.setColor(Color.BLUE);
                        canvas.drawRect(x, y, x + smallTileSize, y + smallTileSize, paint);
                    } else if (value == 0) {
                        canvas.drawRect(x, y, x + smallTileSize, y + smallTileSize, blackPaint);
                    } else if (value == 2) {
                        Bitmap pacmanOpen = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.pacman_open);
                        Bitmap pacmanOpenResized = Bitmap.createScaledBitmap(pacmanOpen, smallTileSize, smallTileSize, false);
                        canvas.drawBitmap(pacmanOpenResized, x, y, null);
                    } else if (value == 3) {
                        Bitmap enemyRed = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.enemy_red);
                        Bitmap enemyRedResized = Bitmap.createScaledBitmap(enemyRed, smallTileSize, smallTileSize, false);
                        canvas.drawBitmap(enemyRedResized, x, y, null);
                    }
                }

                LinearLayout linearLayout = new LinearLayout(getApplicationContext());
                ImageView imageView = new ImageView(getApplicationContext());
                imageView.setImageBitmap(bitmap);

                final int finalImgIdx = imgIdx;
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    loadScore(finalImgIdx);
                    }
                });

                linearLayout.addView(imageView);
                linearLayout.setGravity(Gravity.CENTER);
                linearLayout.setMinimumWidth((int)(miniMapWidth * 1.5));
                levelsLayoutList.add(linearLayout);

                imgIdx++;
            }

            levelsLayout.setMinimumHeight((int)(maxMiniLevelHeight * 1.5));
            levelsLayout.setGravity(Gravity.CENTER);
            for (LinearLayout linearLayout : levelsLayoutList) {
                linearLayout.setMinimumHeight((int)(maxMiniLevelHeight * 1.5));
                levelsLayout.addView(linearLayout);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadScore(int levelId) {
        ListView listView = (ListView) findViewById(R.id.high_scores_list);
        ArrayList<HighScore> highScores = databaseHelper.getScoresByLevelId(levelId);
        if (highScores.isEmpty()) highScores.add(new HighScore("empty", 0, 0));
        HighScoreAdapter highScoreAdapter = new HighScoreAdapter(HighScoreActivity.this, highScores);
        listView.setAdapter(highScoreAdapter);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
