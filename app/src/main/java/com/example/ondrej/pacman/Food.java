package com.example.ondrej.pacman;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Food implements IDrawable, IColidable {

    private Rect rectangle;
    private Paint paint;

    public Food (int x, int y) {
        this.paint = new Paint();
        this.paint.setColor(Color.WHITE);
        int left = x+(int)(ConstantHelper.TILE_SIZE*0.4375);
        int top = y+(int)(ConstantHelper.TILE_SIZE*0.4375) + ConstantHelper.PANEL_SIZE;
        this.rectangle = new Rect(left, top, left + ConstantHelper.TILE_SIZE/8, top + ConstantHelper.TILE_SIZE/8);
        //TODO přidat konstantu food size
        //this.rectangle = new Rect(x, y, x+ ConstantHelper.TILE_SIZE, y+ ConstantHelper.TILE_SIZE);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRect(this.rectangle, this.paint);
    }

    @Override
    public boolean colides(Rect rect) {
        return this.rectangle.left < rect.right && rect.left < this.rectangle.right && this.rectangle.top < rect.bottom && rect.top < this.rectangle.bottom;
    }
}
