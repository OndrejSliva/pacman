package com.example.ondrej.pacman;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

public class GameActivity extends Activity {

    private GameScreen gameScreen;
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.game_layout);
        Display display = getWindowManager().getDefaultDisplay();
        Point p = new Point();
        display.getSize(p);
        int width = p.x;
        int height = p.y;

        LinearLayout canvasLayout = (LinearLayout) findViewById(R.id.canvas_layout);
        gameScreen = new GameScreen(this, width, height);
        canvasLayout.addView(gameScreen);

        this.initControl();

        gameScreen.run();
    }

    private void initGestureControl(final GameScreen gameScreen) {
        gestureDetector = new GestureDetector(this,
            new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onFling(MotionEvent e1, MotionEvent e2, float changeX, float changeY) {
                    if (Math.abs(changeX) > Math.abs(changeY) && Math.abs(changeX) > 5) {
                        if (changeX > 0) {
                            gameScreen.setPlayerDirection(Player.DIRECTION_RIGHT);
                        } else {
                            gameScreen.setPlayerDirection(Player.DIRECTION_LEFT);
                        }
                    } else if (Math.abs(changeY) > Math.abs(changeX) && Math.abs(changeY) > 5) {
                        if (changeY > 0) {
                            gameScreen.setPlayerDirection(Player.DIRECTION_DOWN);
                        } else {
                            gameScreen.setPlayerDirection(Player.DIRECTION_UP);
                        }
                    }

                    return super.onFling(e1, e2, changeX, changeY);
                }
            }
        );
    }

    private void initControl() {
        SharedPreferences appPreferences = getApplicationContext().getSharedPreferences("setting", Context.MODE_PRIVATE);
        int controlType = appPreferences.getInt("control_type", 1);

        if (controlType == 1) {
            LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ViewGroup parent = (ViewGroup) findViewById(R.id.control_layout);
            inflater.inflate(R.layout.buttons_control_layout, parent);

            this.initMoveButtons();
        } else if (controlType == 2) {
            LinearLayout gameLayout = findViewById(R.id.game_layout);
            this.initGestureControl(gameScreen);

            gameLayout.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    gestureDetector.onTouchEvent(event);
                    return true;
                }
            });
        }
    }

    private void initMoveButtons() {
        Button buttonUp = (Button)findViewById(R.id.button_up);
        this.initMoveButton(buttonUp, Player.DIRECTION_UP);
        Button buttonDown = (Button)findViewById(R.id.button_down);
        this.initMoveButton(buttonDown, Player.DIRECTION_DOWN);
        Button buttonLeft = (Button)findViewById(R.id.button_left);
        this.initMoveButton(buttonLeft, Player.DIRECTION_LEFT);
        Button buttonRight = (Button)findViewById(R.id.button_right);
        this.initMoveButton(buttonRight, Player.DIRECTION_RIGHT);
    }

    private void initMoveButton(Button button, final int dircetion) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameScreen.setPlayerDirection(dircetion);
            }
        });
    }
}
