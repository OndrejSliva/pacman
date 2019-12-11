package com.example.ondrej.pacman;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class WriteNameActivity extends Activity {

    private int score;
    private int levelId;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_name_layout);
        Intent intent = getIntent();
        score = intent.getIntExtra("score", 0);
        levelId = intent.getIntExtra("levelId", 0);

        TextView scoreTextView = (TextView) findViewById(R.id.score_text_view);
        scoreTextView.setText(String.valueOf(score));
        databaseHelper = new DatabaseHelper(getApplicationContext());
    }


    public void saveScore(View view) {
        EditText nameEditText = (EditText) findViewById(R.id.edit_text_score_name);
        this.databaseHelper.addScore(nameEditText.getText().toString(), score, levelId);
        Intent intent = new Intent(getApplicationContext(), HighScoreActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            Toast.makeText(getApplicationContext(), "Musíte vyplnit jméno", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
