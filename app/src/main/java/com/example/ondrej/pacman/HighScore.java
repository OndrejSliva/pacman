package com.example.ondrej.pacman;

public class HighScore {

    private String name;

    private int position;

    private int score;

    public HighScore(String name, int position, int score) {
        this.name = name;
        this.position = position;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }

    public int getScore() {
        return score;
    }
}
