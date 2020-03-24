package com.unicap.oficinaadvergame.gameglup;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.spashscreen.R;

public class PeixeRosa extends Area{

	Bitmap pink, ponto; //imagem
	int xspeed, yspeed; //velocidades

	public PeixeRosa(int x, int y, Resources res) {
		super(x, y, 0, 0);
		// TODO Auto-generated constructor stub
		
		if (pink == null) {
			pink = BitmapFactory.decodeResource(res, R.drawable.pink_fish);
			ponto = BitmapFactory.decodeResource(res, R.drawable.bolha2);
			pink = Bitmap.createScaledBitmap(pink, -pink.getWidth(),
					pink.getHeight(), true);
		}		
		xspeed = (int) (Math.random() * 2.5 + 1);
		yspeed = (int) (Math.random() * 2.5 + 1);
	}



	public void mexe(int width, int height) {
		// TODO Auto-generated method stub
		setX(getX() + xspeed);

		if (getX() + xspeed > width - pink.getWidth()
				|| getX() + xspeed < width * 0) {
			xspeed *= -1;
			pink = Bitmap.createScaledBitmap(pink, -pink.getWidth(),
					pink.getHeight(), true);
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
		setX((int) (Math.random() *2 + width*0 + 20));
		setY((int) (height - 65));
	}
	
	public void draw(Canvas canvas, Paint paint) {
		// TODO Auto-generated method stub
		pink = Bitmap.createScaledBitmap(pink, canvas.getWidth()*7/100, canvas.getHeight()*5/100, true);
		canvas.drawBitmap(pink, getX(), getY(), paint);
	}

}
