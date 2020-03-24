package com.unicap.oficinaadvergame.gameglup;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.spashscreen.R;

public class GameOverAssets extends Area{

	Bitmap star, bg_end;
	
	public GameOverAssets(int x, int y, Resources res) {
		super(x, y, 0, 0);
		// TODO Auto-generated constructor stub
		
		star = BitmapFactory.decodeResource(res, R.drawable.star_1);
		bg_end = BitmapFactory.decodeResource(res, R.drawable.bg_end);
	}
	
	public void drawgameover(Canvas canvas, Paint paint){
		bg_end = Bitmap.createScaledBitmap(bg_end, this.getWidth(), this.getHeight(), true);    
		canvas.drawBitmap(bg_end, 0, 0, paint);
	}
	
	public void drawstar(Canvas canvas, Paint paint) {
		star = Bitmap.createScaledBitmap(star, canvas.getWidth()*13/100, canvas.getHeight()*15/100, true);
		canvas.drawBitmap(star, getX(), getY(), paint);
	}

}
