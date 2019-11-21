package com.example.ondrej.pacman;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SettingActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_layout);
        this.initButtons();
    }

    private void initButtons() {
        Button gestures = (Button) findViewById(R.id.gestuere_control);
        Button buttons = (Button) findViewById(R.id.buttons_control);

        gestures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences appPreferences = getApplicationContext().getSharedPreferences("setting", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = appPreferences.edit();
                editor.putInt("control_type", 2);
                editor.apply();
                Toast.makeText(getApplicationContext(), "Nastaveno ovládání gesty", Toast.LENGTH_SHORT).show();
            }
        });
        buttons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences appPreferences = getApplicationContext().getSharedPreferences("setting", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = appPreferences.edit();
                editor.putInt("control_type", 1);
                editor.apply();
                Toast.makeText(getApplicationContext(), "Nastaveno ovládání tlačítky", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
