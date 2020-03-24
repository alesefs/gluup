package com.unicap.oficinaadvergame.gameglup;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.spashscreen.R;

public class Background extends Area{

	Bitmap bg, barco, pedras, pescador1, pescador2;
	int xspeed;
	
	public Background(int x, int y, Resources res) {
		super(x, y, 0, 0);
		// TODO Auto-generated constructor stub
		
		bg = BitmapFactory.decodeResource(res, R.drawable.bg_jogo);
		barco = BitmapFactory.decodeResource(res, R.drawable.barco);
		pedras = BitmapFactory.decodeResource(res, R.drawable.pedras);
		pescador1 = BitmapFactory.decodeResource(res, R.drawable.pescador1);
		pescador2 = BitmapFactory.decodeResource(res, R.drawable.pescador2);
		
		xspeed = (int) (Math.random() * 1 + 1);
	}
	
	public void drawbg(Canvas canvas, Paint paint) {
		bg = Bitmap.createScaledBitmap(bg, canvas.getWidth(), canvas.getHeight(), true);
		canvas.drawBitmap(bg, 0, 0, paint);
	}
	
	public void drawbarco(Canvas canvas, Paint paint) {
		barco = Bitmap.createScaledBitmap(barco, canvas.getWidth()*50/100, canvas.getHeight()*20/100, true);
		canvas.drawBitmap(barco, canvas.getWidth()/2 - barco.getWidth()/2, (float) (canvas.getHeight()*0 + barco.getHeight()/1.65), paint);
	}
	
	public void drawpedras(Canvas canvas, Paint paint) {
		pedras = Bitmap.createScaledBitmap(pedras, canvas.getWidth(), canvas.getHeight(), true);
		canvas.drawBitmap(pedras, 0, 0, paint);
	}
	
	public void drawpesecador1(Canvas canvas, Paint paint) {
		pescador1 = Bitmap.createScaledBitmap(pescador1, canvas.getWidth()*10/100, canvas.getHeight()*30/100, true);
		canvas.drawBitmap(pescador1, canvas.getWidth()/2 - pescador1.getWidth()*2, (float) (canvas.getHeight()*0 + barco.getHeight()/1.2), paint);
	}
	
	public void drawpesecador2(Canvas canvas, Paint paint) {
		pescador2 = Bitmap.createScaledBitmap(pescador2, canvas.getWidth()*8/100, canvas.getHeight()*28/100, true);
		canvas.drawBitmap(pescador2, (float) (canvas.getWidth()/2 + pescador2.getWidth()*1.7), (float) (canvas.getHeight()*0 + barco.getHeight()/1.1), paint);
	}
	
}
