package com.example.ondrej.pacman;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class GameActivity extends Activity {

    private GameScreen gameScreen;
    private GestureDetector gestureDetector;

    private ImageView playIcon;
    private ImageView pauseIcon;
    private LinearLayout layoutWithPlayPauseButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Intent intent = getIntent();
        int levelId = intent.getIntExtra("level", 0);

        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.game_layout);

        LinearLayout canvasLayout = (LinearLayout) findViewById(R.id.canvas_layout);
        gameScreen = new GameScreen(this, levelId);
        canvasLayout.addView(gameScreen);

        playIcon = new ImageView(getApplicationContext());
        pauseIcon = new ImageView(getApplicationContext());
        layoutWithPlayPauseButtons = (LinearLayout) findViewById(R.id.layout_play_pause);

        this.initPlayPauseIcons();
        this.initControl();
    }

    @Override
    protected void onPause() {
        super.onPause();
        pauseGame();
    }

    private void initPlayPauseIcons() {
        playIcon.setImageBitmap(ImageHelper.getPlayButton());
        pauseIcon.setImageBitmap(ImageHelper.getPauseButton());

        playIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unPauseGame();
            }
        });

        pauseIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseGame();
            }
        });

        layoutWithPlayPauseButtons.addView(pauseIcon);
    }

    private void pauseGame() {
        gameScreen.setPaused();
        layoutWithPlayPauseButtons.removeAllViews();
        layoutWithPlayPauseButtons.addView(playIcon);
    }

    private void unPauseGame() {
        gameScreen.setPlay();
        layoutWithPlayPauseButtons.removeAllViews();
        layoutWithPlayPauseButtons.addView(pauseIcon);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            pauseGame();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    private void initGestureControl(final GameScreen gameScreen) {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.control_layout);
        linearLayout.setGravity(Gravity.CENTER);
        final ImageView imageView = new ImageView(getApplicationContext());
        imageView.setImageBitmap(ImageHelper.getArrowUp());
        linearLayout.addView(imageView);
        gestureDetector = new GestureDetector(this,
            new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onFling(MotionEvent e1, MotionEvent e2, float changeX, float changeY) {
                    if (Math.abs(changeX) > Math.abs(changeY) && Math.abs(changeX) > 5) {
                        if (changeX > 0) {
                            gameScreen.setPlayerDirection(Player.DIRECTION_RIGHT);
                            imageView.setImageBitmap(ImageHelper.getArrowRight());
                        } else {
                            gameScreen.setPlayerDirection(Player.DIRECTION_LEFT);
                            imageView.setImageBitmap(ImageHelper.getArrowLeft());
                        }
                    } else if (Math.abs(changeY) > Math.abs(changeX) && Math.abs(changeY) > 5) {
                        if (changeY > 0) {
                            gameScreen.setPlayerDirection(Player.DIRECTION_DOWN);
                            imageView.setImageBitmap(ImageHelper.getArrowDown());
                        } else {
                            gameScreen.setPlayerDirection(Player.DIRECTION_UP);
                            imageView.setImageBitmap(ImageHelper.getArrowUp());
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
