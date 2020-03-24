package com.unicap.oficinaadvergame.gameglup;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.spashscreen.R;

public class PeixeVermelho extends Area{

	Bitmap red; //imagem
	int xspeed, yspeed; //velocidades
	
	
	public PeixeVermelho(int x, int y, Resources res) {
		super(x, y, 0, 0);
		// TODO Auto-generated constructor stub
		
		if (red == null) {
			red = BitmapFactory.decodeResource(res, R.drawable.red_fish);
			red = Bitmap.createScaledBitmap(red, -red.getWidth(),
					red.getHeight(), true);
		}		
		xspeed = (int) (Math.random() * 2.25 + 1);
		yspeed = (int) (Math.random() * 2.25 + 1);
	}



	public void mexe(int width, int height) {
		// TODO Auto-generated method stub
		setX(getX() + xspeed);

		if (getX() + xspeed > width - red.getWidth()
				|| getX() + xspeed < width * 0) {
			xspeed *= -1;
			red = Bitmap.createScaledBitmap(red, -red.getWidth(),
					red.getHeight(), true);
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
		setY((int) (height - 55));
	}
	
	public void draw(Canvas canvas, Paint paint) {
		
		red = Bitmap.createScaledBitmap(red, (int) (canvas.getWidth()*7.5/100), (int) (canvas.getHeight()*5.5/100), true);
		canvas.drawBitmap(red, getX(), getY(), paint);
	}
	
}
