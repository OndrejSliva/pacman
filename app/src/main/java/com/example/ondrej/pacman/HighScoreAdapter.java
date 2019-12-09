package com.example.ondrej.pacman;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class HighScoreAdapter extends ArrayAdapter<HighScore> {

    private final Activity context;
    private final List<HighScore> highScores;

    public HighScoreAdapter(@NonNull Activity context, List<HighScore> highScores) {
        super(context, R.layout.high_score_activity, highScores);
        this.context = context;
        this.highScores = highScores;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.high_score_item, null, true);

        TextView itemPosition = (TextView) rowView.findViewById(R.id.column_position);
        TextView itemName = (TextView) rowView.findViewById(R.id.column_name);
        TextView itemScore = (TextView) rowView.findViewById(R.id.column_score);

        itemName.setText(highScores.get(position).getName());
        itemScore.setText(String.valueOf(highScores.get(position).getScore()));
        itemPosition.setText(String.valueOf(highScores.get(position).getPosition()));

        return rowView;
    }
}
