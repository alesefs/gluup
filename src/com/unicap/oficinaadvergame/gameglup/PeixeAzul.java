package com.unicap.oficinaadvergame.gameglup;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.spashscreen.R;

public class PeixeAzul extends Area{

	Bitmap blue; //imagem
	int xspeed, yspeed; //velocidades
		
	public PeixeAzul(int x, int y, Resources res) {
		super(x, y, 0, 0);
		// TODO Auto-generated constructor stub
		
		if (blue == null) {
			blue = BitmapFactory.decodeResource(res, R.drawable.blue_fish);
			blue = Bitmap.createScaledBitmap(blue, -blue.getWidth(),
					blue.getHeight(), true);
		}		
		
		xspeed = (int) (Math.random() * 2.75 + 1);
		yspeed = (int) (Math.random() * 2.75 + 1);
	}



	public void mexe(int width, int height) {
		// TODO Auto-generated method stub
		setX(getX() + xspeed);

		if (getX() + xspeed > width - blue.getWidth()
				|| getX() + xspeed < width * 0){
			xspeed *= -1;
			blue = Bitmap.createScaledBitmap(blue, -blue.getWidth(),
					blue.getHeight(), true);
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

	
	public void colisao(int height, int width) {
		// TODO Auto-generated method stub
		setX((int) (Math.random() * (width*0 + 20)));
		setY((int) (height - 55));
	}
	
	public void draw(Canvas canvas, Paint paint) {
		blue = Bitmap.createScaledBitmap(blue, canvas.getWidth()*8/100, canvas.getHeight()*6/100, true);
		canvas.drawBitmap(blue, getX(), getY(), paint);
	}
}
