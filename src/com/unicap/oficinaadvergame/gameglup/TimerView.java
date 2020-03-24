package com.unicap.oficinaadvergame.gameglup;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.spashscreen.R;

public class TimerView extends View{

	Bitmap bg_timer, btn_mais, btn_menos, btn_jogar;
	private boolean bjogar, bmais, bmenos;
	float mPosX, mPosY, mResTouchX, mResTouchY;
	Paint paint, textpaint = new Paint();
	String tempo;
	int tempo_i = 5;

	
	public TimerView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub		
		setFocusable(true);
		
		bg_timer = BitmapFactory.decodeResource(context.getResources(), R.drawable.bg_timer);
		btn_mais = BitmapFactory.decodeResource(context.getResources(), R.drawable.btn_mais);
		btn_menos = BitmapFactory.decodeResource(context.getResources(), R.drawable.btn_menos);
		btn_jogar = BitmapFactory.decodeResource(context.getResources(), R.drawable.btn_jogar);
	}

	@SuppressLint("DrawAllocation")
	protected void onDraw (Canvas canvas){
		// TODO Auto-generated method stub
		super.dispatchDraw(canvas);
		super.onDraw(canvas);
	
		
		//background
		bg_timer = Bitmap.createScaledBitmap(bg_timer, getWidth(), getHeight(), true);
		canvas.drawBitmap(bg_timer, 0, 0, paint);
						
		btnmais(canvas);
		btnmenos(canvas);
		btnjogar(canvas);
		
		contador(canvas);
	}

	//btn menu
	private void btnjogar(Canvas canvas) {
		// TODO Auto-generated method stub
		// não tocando
		if (bjogar == false) {
			//botao jogar
			btn_jogar = Bitmap.createScaledBitmap(btn_jogar, canvas.getWidth()*13/100, canvas.getHeight()*20/100, true);
			canvas.drawBitmap(btn_jogar,(float) (canvas.getWidth()*0 + canvas.getWidth()*47/100), (float) (canvas.getHeight()*0 + canvas.getHeight()*70/100), paint);

		}

		// tocando
		if (bjogar == true) { // metodo boolean	
			
			//botao jogar
			btn_jogar = Bitmap.createScaledBitmap(btn_jogar, canvas.getWidth()*15/100, canvas.getHeight()*23/100, true);
			canvas.drawBitmap(btn_jogar,(float) (canvas.getWidth()*0 + canvas.getWidth()*47/100), (float) (canvas.getHeight()*0 + canvas.getHeight()*70/100), paint);

			
			Context ctx = getContext();
			((Activity) ctx).finish();
			Intent intent = new Intent(ctx, GameActivity.class);
			intent.putExtra("tempo", tempo_i);
			ctx.startActivity(intent);
			
		}
		invalidate();		
	}

	
	//btn mais
	private void btnmais(Canvas canvas) {
		// TODO Auto-generated method stub
		// não tocando
		if (bmais == false) {
			//botao mais
			btn_mais = Bitmap.createScaledBitmap(btn_mais, canvas.getWidth()*13/100, canvas.getHeight()*20/100, true);
			canvas.drawBitmap(btn_mais,(float) (canvas.getWidth()*0 + canvas.getWidth()*62/100), (float) (canvas.getHeight()*0 + canvas.getHeight()*40/100), paint);
		}

		// tocando
		if (bmais == true) { // metodo boolean	
			//botao mais
			btn_mais = Bitmap.createScaledBitmap(btn_mais, canvas.getWidth()*15/100, canvas.getHeight()*23/100, true);
			canvas.drawBitmap(btn_mais,(float) (canvas.getWidth()*0 + canvas.getWidth()*62/100), (float) (canvas.getHeight()*0 + canvas.getHeight()*40/100), paint);
			
			
			tempo_i += 1;
			
			if (tempo_i > 60){
				tempo_i = 1;
			}
		}
		invalidate();
	}
	
	
	//btn menos
	private void btnmenos(Canvas canvas) {
		// TODO Auto-generated method stub
		// não tocando
		if (bmenos == false) {
			//botao menos
			btn_menos = Bitmap.createScaledBitmap(btn_menos, canvas.getWidth()*13/100, canvas.getHeight()*20/100, true);
			canvas.drawBitmap(btn_menos,canvas.getWidth()*0 + canvas.getWidth()*30/100, (float) (canvas.getHeight()*0 + canvas.getHeight()*40/100), paint);
		}

		// tocando
		if (bmenos == true) { // metodo boolean	
			//botao mais
			btn_menos = Bitmap.createScaledBitmap(btn_menos, canvas.getWidth()*15/100, canvas.getHeight()*23/100, true);
			canvas.drawBitmap(btn_menos, canvas.getWidth()*0 + canvas.getWidth()*30/100, (float) (canvas.getHeight()*0 + canvas.getHeight()*40/100), paint);
			
			
			tempo_i -= 1;
			
			if (tempo_i < 1){
				tempo_i = 60;
			}
		}
		invalidate();		
	}



	private void contador(Canvas canvas) {
		// TODO Auto-generated method stub
		textpaint.setColor(0xff071180);
		textpaint.setTextSize((float) (canvas.getWidth()*10/100));
		
		
		if (tempo_i < 10){
			tempo = "0" + tempo_i;
		}
		else{
			tempo = "" + tempo_i;
		}
		
		canvas.drawText(tempo, (float) (canvas.getWidth()*0 + canvas.getWidth()*47/100), (float) (canvas.getHeight()*0 + canvas.getHeight()*55/100), textpaint);
	
	}
	
	
	// onTouchEvent - registrando os toques na tela
	@Override
	public boolean onTouchEvent(MotionEvent motionEvent) {
		// TODO Auto-generated method stub

		// registradores do toque em decimal
		float xPos = (float) motionEvent.getX();
		float yPos = (float) motionEvent.getY();

		
		//para evento botao jogar	
		//jogar
		if (xPos > this.getWidth()*45/100 + 1 && xPos < this.getWidth()*65/100 + 1 && yPos > this.getHeight()*65/100 && yPos < this.getHeight()*95/100) {

			// switch e case - atuadores e condicionadores
			switch (motionEvent.getAction()) { 
			case MotionEvent.ACTION_DOWN:
			case MotionEvent.ACTION_MOVE:
				mPosX = motionEvent.getX(); 
				mPosY = motionEvent.getY(); 
				
				bjogar = true;
				break;
			default:
				bjogar = false;
			}
			invalidate();
		}
		
		
		//valor +
		if (xPos > this.getWidth()*60/100 + 1 && xPos < this.getWidth()*75/100 + 1 && yPos > this.getHeight()*40/100 + 1 && yPos < this.getHeight()*60/100 + 1) {
			// switch e case - atuadores e condicionadores
			switch (motionEvent.getAction()) { 
			case MotionEvent.ACTION_DOWN:
			case MotionEvent.ACTION_MOVE:
				mPosX = motionEvent.getX(); 
				mPosY = motionEvent.getY(); 
				
				bmais = true;
				break;
			default:
				bmais = false;
			}
			invalidate();
		}
		
		
		//valor -
		if (xPos > this.getWidth()*25/100 + 1 && xPos < this.getWidth()*48/100 + 1 && yPos > this.getHeight()*40/100 + 1 && yPos < this.getHeight()*60/100 + 1) {
			// switch e case - atuadores e condicionadores
			switch (motionEvent.getAction()) { 
			case MotionEvent.ACTION_DOWN:
			case MotionEvent.ACTION_MOVE:
				mPosX = motionEvent.getX(); 
				mPosY = motionEvent.getY(); 
				
				bmenos = true;
				break;
			default:
				bmenos = false;
			}
			invalidate();
		}
		
		return true;
	}
	
}
