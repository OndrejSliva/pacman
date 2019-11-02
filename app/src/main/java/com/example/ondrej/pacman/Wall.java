package com.example.ondrej.pacman;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Wall implements IDrawable{

    private Rect rect;
    private Paint paint;

    public Wall(int x, int y){
        this.rect = new Rect(x, y, x + ConstantHelper.TILE_SIZE, y + ConstantHelper.TILE_SIZE);
        paint = new Paint();
        paint.setColor(Color.BLUE);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRect(this.rect, paint);
    }

    public boolean colides(Rect rect) {
        return this.rect.intersect(rect);
    }
}
