package com.unicap.oficinaadvergame.gameglup;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.spashscreen.R;

public class PeixePreto extends Area{

	Bitmap black, black2;
	int xspeed;
	int morde;
	
	public PeixePreto(int x, int y, Resources res) {
		super(x, y, 0, 0);
		// TODO Auto-generated constructor stub		
		
		if (black == null) {
			black = BitmapFactory.decodeResource(res, R.drawable.black_fish);
			black2 = BitmapFactory.decodeResource(res, R.drawable.black_fish2);
			black = Bitmap.createScaledBitmap(black, black.getWidth(),
					black.getHeight(), true);
		}	
			xspeed = (int) (Math.random() * 1 + 1);	
	}
	 
	
	public void mexe(int width, int height) {
		// TODO Auto-generated method stub
		setX(getX() + xspeed);

		if (getX() + xspeed > width - black.getWidth()
				|| getX() + xspeed < width * 0) {
			xspeed *= -1;
			black = Bitmap.createScaledBitmap(black, -black.getWidth(), black.getHeight(), true);
		}
	}
	
	
	
	
	public void draw(Canvas canvas, Paint paint) {
		black = Bitmap.createScaledBitmap(black, canvas.getWidth()*18/100, canvas.getHeight()*12/100, true);
		canvas.drawBitmap(black, getX(), getY(), paint);
	}


	public void mordida(Canvas canvas, Paint paint) {
		// TODO Auto-generated method stub		
		morde ++;
		
		if (morde > 0 && morde < 200){
			if (xspeed == 1){
				black2 = Bitmap.createScaledBitmap(black2, canvas.getWidth()*18/100, canvas.getHeight()*12/100, true);
			} else {
				black2 = Bitmap.createScaledBitmap(black2, -canvas.getWidth()*18/100, canvas.getHeight()*12/100, true);
			}
				canvas.drawBitmap(black2, getX(), getY(), paint);
		} else {
			if (xspeed == 1){
				black2 = Bitmap.createScaledBitmap(black2, canvas.getWidth()*18/100, canvas.getHeight()*12/100, true);
			} else {
				black2 = Bitmap.createScaledBitmap(black2, -canvas.getWidth()*18/100, canvas.getHeight()*12/100, true);
			}
			canvas.drawBitmap(black, getX(), getY(), paint);
		}
	}
		
} 