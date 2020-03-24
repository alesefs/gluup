package com.unicap.oficinaadvergame.gameglup;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.spashscreen.R;

public class PeixeOuro extends Area{

	Bitmap gold;
	int xspeed, yspeed;
	
	public PeixeOuro(int x, int y, Resources res) {
		super(x, y, 0, 0);
		// TODO Auto-generated constructor stub		
		
		if (gold == null) {
			gold = BitmapFactory.decodeResource(res, R.drawable.gold_fish);
			gold = Bitmap.createScaledBitmap(gold, -gold.getWidth(),
					gold.getHeight(), true);
		}	
		xspeed = (int) (Math.random() * 3 + 1);
		yspeed = (int) (Math.random() * 3 + 1);
	}
	 	
	public void mexe(int width, int height) {
		// TODO Auto-generated method stub
		setX(getX() + xspeed);

		if (getX() + xspeed > width - gold.getWidth()
				|| getX() + xspeed < width * 0) {
			xspeed *= -1;
			gold = Bitmap.createScaledBitmap(gold, -gold.getWidth(), gold.getHeight(), true);
		}
	}
		
	public void subir(int width, int height){
		// TODO Auto-generated method stub
		setY(getY() - yspeed);
	}
	
	public void block(int width, int height) {
		// TODO Auto-generated method stub	
		setY(getY() + yspeed);
	}
	
	public void colisao(int width, int height) {
		// TODO Auto-generated method stub	
		setX((int) (Math.random() * (width*0 + 20)));
		setY((int) (height - 70));
	}
	
	public void draw(Canvas canvas, Paint paint) {
		gold = Bitmap.createScaledBitmap(gold, (int) (canvas.getWidth()*6.75/100), (int) (canvas.getHeight()*4.75/100), true);
		canvas.drawBitmap(gold, getX(), getY(), paint);
	}
		
} 