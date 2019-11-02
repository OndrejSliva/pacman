package com.example.ondrej.pacman;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends Activity {

    private GameScreen gameScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.game_with_buttons_layout);
        Display display = getWindowManager().getDefaultDisplay();
        Point p = new Point();
        display.getSize(p);
        int width = p.x;
        int height = p.y;

        LinearLayout canvasLayout = (LinearLayout) findViewById(R.id.canvas_layout);
        gameScreen = new GameScreen(this, width, height);
        canvasLayout.addView(gameScreen);

        this.initMoveButtons();
        gameScreen.run();
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
