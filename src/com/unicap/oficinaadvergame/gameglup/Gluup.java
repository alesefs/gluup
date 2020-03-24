package com.unicap.oficinaadvergame.gameglup;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.spashscreen.R;

public class Gluup extends Area{

	Bitmap bolhas;
	
	public Gluup(int x, int y, Resources res) {
		super(x, y, 0, 0);
		// TODO Auto-generated constructor stub
		
		if (bolhas == null) {
			bolhas = BitmapFactory.decodeResource(res, R.drawable.bolha);
			bolhas = Bitmap.createScaledBitmap(bolhas, -bolhas.getWidth()*10/100, bolhas.getHeight()*10/100, true);
		}			
	}

	public void mexe(int height, int width) {
		if (getY()>height) {
			setY(getY()-20);
		} else {
			int x = (int) (Math.random() * (width));
			setX(x);
			setY(height*3+2);
		}
	}
	
	public void move(int height, int width) {
		if (getY()>height) {
			setY(getY()-20);
		} else {
			int x = (int) ((Math.random() * width) + width);
			setX(x);
			setY(height*3+2);
		}
	}
	
	public void remove(int height, int width) {
		int x = (int) (Math.random() * (width));
		setX(x);
		setY((int) (height*3 / (Math.random())));		
	}
	
	public void deleta(int height, int width) {
		int x = (int) ((Math.random() * width) + width);
		setX(x);
		setY((int) (height*3 / (Math.random())));
	}
	
	public void draw(Canvas canvas, Paint paint) {
		bolhas = Bitmap.createScaledBitmap(bolhas, -canvas.getWidth()*1/100, canvas.getHeight()*1/100, true);
		canvas.drawBitmap(bolhas, getX(), getY(), paint);
	}
}
