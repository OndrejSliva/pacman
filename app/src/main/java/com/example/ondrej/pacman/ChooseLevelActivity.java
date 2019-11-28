package com.example.ondrej.pacman;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ChooseLevelActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_level_layout);
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        this.loadLevels();
    }

    private void loadLevels() {
        TableLayout tableLayout = (TableLayout) findViewById(R.id.level_list);
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getAssets().open("levels.txt")));

            String mLine;
            int imgIdx = 0;
            while ((mLine = bufferedReader.readLine()) != null) {
                String[] values = mLine.split(",");

                int mapWidth = Integer.valueOf(values[0]);
                int mapHeight = Integer.valueOf(values[1]);

                int miniMapWidth = ConstantHelper.SCREEN_WIDTH / 2;
                int smallTileSize = miniMapWidth / mapWidth;
                int miniMapHeight = smallTileSize * mapHeight;

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
                    }else
                    if (value == 0) {
                        canvas.drawRect(x, y, x + smallTileSize, y + smallTileSize, blackPaint);
                    }else
                    if (value == 2) {

                        Bitmap pacmanOpen = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.pacman_open);
                        Bitmap pacmanOpenResized = Bitmap.createScaledBitmap(pacmanOpen, smallTileSize, smallTileSize, false);

                        canvas.drawBitmap(pacmanOpenResized, x, y, null);
                    }else
                    if (value == 3) {

                        Bitmap enemyRed = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.enemy_red);
                        Bitmap enemyRedResized = Bitmap.createScaledBitmap(enemyRed, smallTileSize, smallTileSize, false);

                        canvas.drawBitmap(enemyRedResized, x, y, null);
                    }
                }

                TableRow tableRow = new TableRow(getApplicationContext());
                ImageView imageView = new ImageView(getApplicationContext());
                imageView.setImageBitmap(bitmap);

                final int finalImgIdx = imgIdx;
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), GameActivity.class);
                        intent.putExtra("level", finalImgIdx);
                        startActivity(intent);
                    }
                });

                tableRow.addView(imageView);
                tableRow.setGravity(Gravity.CENTER);
                tableRow.setMinimumHeight((int)(miniMapHeight * 1.5));
                tableLayout.addView(tableRow);

                imgIdx++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
