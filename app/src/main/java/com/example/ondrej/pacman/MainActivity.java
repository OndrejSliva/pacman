package com.example.ondrej.pacman;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadScreenSizeInfo();
    }

    public void start(View view) {
        Intent intent = new Intent(getApplicationContext(), ChooseLevelActivity.class);
        startActivity(intent);
    }

    public void settings(View view) {
        Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
        startActivity(intent);
    }

    public void highScore(View view) {
        Intent intent = new Intent(getApplicationContext(), HighScoreActivity.class);
        startActivity(intent);
    }

    private void loadScreenSizeInfo() {
        Display display = getWindowManager().getDefaultDisplay();
        Point p = new Point();
        display.getSize(p);
        int width = p.x;
        int height = p.y;
        ConstantHelper.setScreenSize(width, height);
        ImageHelper.initSharedImages(getResources());
    }

    public void exit(View view) {
        finishAndRemoveTask();
    }
}
