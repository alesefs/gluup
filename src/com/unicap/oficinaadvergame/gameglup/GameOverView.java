package com.unicap.oficinaadvergame.gameglup;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.spashscreen.R;

public class GameOverView extends View {

	//imagens e passagem de pontos
	Bitmap gameover, menu, jogar;
	Paint paintText, paint;
	Context context;
	private int hud_pontos_i;
	
	private ArrayList<GameOverAssets> stars;
	int n;
	
	//para botoes
	float mPosX, mPosY, mResTouchX, mResTouchY;
	boolean touching1 = false;
	boolean touching2 = false; 
	
	MediaPlayer bgsound_gameover;
	
	
	public GameOverView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.context = context;
		
		gameover = BitmapFactory.decodeResource(getResources(), R.drawable.bg_end);
		menu = BitmapFactory.decodeResource(getResources(), R.drawable.btn_menu);
		jogar = BitmapFactory.decodeResource(getResources(), R.drawable.btn_jogar);
		hud_pontos_i = ((Activity) context).getIntent().getIntExtra("PONTOS", 0);
		
		
		// bg sound_gameover
		bgsound_gameover = MediaPlayer.create(getContext(), R.raw.bg_sound_gameover);
		bgsound_gameover.start();
	
	}
	

	@SuppressLint("DrawAllocation")
	protected void onDraw (Canvas canvas){
		// TODO Auto-generated method stub
		super.dispatchDraw(canvas);
		super.onDraw(canvas);
		
		//background game over
		gameover = Bitmap.createScaledBitmap(gameover, this.getWidth(), this.getHeight(), true);    
		canvas.drawBitmap(gameover, 0, 0, paint);
		
		
		
		
		
		//numero de estrelas
		if (hud_pontos_i == 0){
			n = 0; 
			stars = new ArrayList<GameOverAssets>();
			for(int i = 0; i < n; i++){
				int x = 0;
				int y = 0;
				stars.add(new GameOverAssets(x, y, getResources()));
			}
		}
		if (hud_pontos_i > 0  && hud_pontos_i <= 70){
			n = 1;
			stars = new ArrayList<GameOverAssets>();
			for(int i = 0; i < n; i++){
				int x = this.getWidth()*45/100;
				int y = this.getHeight()*50/100 - this.getHeight()*5/100;
				stars.add(new GameOverAssets(x, y, getResources()));
			}
		}
		if (hud_pontos_i > 70 && hud_pontos_i <= 150){
			n = 2;
			stars = new ArrayList<GameOverAssets>();
			for(int i = 0; i < n; i++){
				int x = this.getWidth()*35/100 + i * (i + this.getWidth()*15/100);
				int y = this.getHeight()*50/100 - this.getHeight()*5/100;
				stars.add(new GameOverAssets(x, y, getResources()));
			}
		}
		if (hud_pontos_i > 150 && hud_pontos_i <= 250){
			n = 3;
			stars = new ArrayList<GameOverAssets>();
			for(int i = 0; i < n; i++){
				int x = this.getWidth()*25/100 + i * (i + this.getWidth()*15/100);
				int y = this.getHeight()*50/100 - this.getHeight()*5/100;
				stars.add(new GameOverAssets(x, y, getResources()));
			}
		}
		if (hud_pontos_i > 250 && hud_pontos_i <= 400){
			n = 4;
			stars = new ArrayList<GameOverAssets>();
			for(int i = 0; i < n; i++){
				int x = this.getWidth()*15/100 + i * (i + this.getWidth()*15/100);
				int y = this.getHeight()*50/100 - this.getHeight()*5/100;
				stars.add(new GameOverAssets(x, y, getResources()));
			}
		}
		if (hud_pontos_i > 400){
			n = 5;
			stars = new ArrayList<GameOverAssets>();
			for(int i = 0; i < n; i++){
				int x = (int) (this.getWidth()*4.5/100 + i * (i + this.getWidth()*15/100));
				int y = this.getHeight()*50/100 - this.getHeight()*5/100;
				stars.add(new GameOverAssets(x, y, getResources()));
			}
		}
		
		
		for (GameOverAssets sprite : stars) {
			sprite.drawstar(canvas,paint);
		}
		
		
		
		//metodo boolean de toque menu
		if (touching1 == false) {
			//botao menu
			menu = Bitmap.createScaledBitmap(menu, this.getWidth()*13/100, this.getHeight()*21/100, true);    
			canvas.drawBitmap(menu, this.getWidth()*63/100, this.getHeight()*70/100, paint);		
		}

		// tocando
		if (touching1 == true) { // metodo boolean				
			
			menu = Bitmap.createScaledBitmap(menu, this.getWidth()*15/100, this.getHeight()*23/100, true);    
			canvas.drawBitmap(menu, this.getWidth()*63/100, this.getHeight()*70/100, paint);
			
			Context ctx = getContext();
			((Activity) ctx).finish();
			Intent intent = new Intent(ctx, MenuActivity.class);
			ctx.startActivity(intent);
		}
		
		//metodo boolean de toque jogar
		if (touching2 == false) {

			//botao jogar
			jogar = Bitmap.createScaledBitmap(jogar, this.getWidth()*15/100, this.getHeight()*23/100, true);    
			canvas.drawBitmap(jogar, this.getWidth()*78/100, this.getHeight()*55/100, paint);		
			
		}

		// tocando
		if (touching2 == true) { // metodo boolean	

			//botao jogar
			jogar = Bitmap.createScaledBitmap(jogar, this.getWidth()*17/100, this.getHeight()*25/100, true);    
			canvas.drawBitmap(jogar, this.getWidth()*78/100, this.getHeight()*55/100, paint);		
			
			
			Context ctx = getContext();
			((Activity) ctx).finish();
			Intent intent = new Intent(ctx, TimerActivity.class);
			ctx.startActivity(intent);
		}

		
		
		pontos(canvas);
		
	}


	private void pontos(Canvas canvas) {
		// TODO Auto-generated method stub
		paintText = new Paint();
		paintText.setColor(0xff071180);
		paintText.setTextSize((float) (canvas.getWidth()*7/100));
		
		canvas.drawText(" " + hud_pontos_i, (float) (canvas.getWidth()*0 + canvas.getWidth()*55/100), (float) (canvas.getHeight()*0 + canvas.getHeight()*43/100), paintText);
	}


	// onTouchEvent - registrando os toques na tela
	@Override
	public boolean onTouchEvent(MotionEvent motionEvent) {
		// TODO Auto-generated method stub

		// registradores do toque em decimal
		float xPos = (float) motionEvent.getX();
		float yPos = (float) motionEvent.getY();

		// condicional if(se) - me pergunta e eu respondo a ordem
		//if (yPos >= gameover.getHeight()*0 && xPos >= gameover.getWidth()*0) {//tela completa
		if (xPos > this.getWidth()/1.6 + 1 && xPos < this.getWidth()/1.3 + 1 && yPos > this.getHeight()/1.4 && yPos < this.getHeight()/1.1) {//menu
			
			// switch e case - atuadores e condicionadores
			switch (motionEvent.getAction()) { // ação de touch
			case MotionEvent.ACTION_MOVE: // se mover dedo
			case MotionEvent.ACTION_DOWN: // se dedo fixar na tela
				mPosX = motionEvent.getX(); // mPosX recebe a medida da tela em
											// tamanho
				mPosY = motionEvent.getY(); // mPosY recebe a medida da tela em
											// altura
				// evento touching - botoes a esquerda
				touching1 = true;
				break;
			default:
				touching1 = false;
			}
			invalidate();
		}
		
		// condicional if(se) - me pergunta e eu respondo a ordem
		//if (yPos >= gameover.getHeight()*0 && xPos >= gameover.getWidth()*0) {//tela completa
		if (xPos > this.getWidth()/1.2 + 1 && xPos < this.getWidth()/0.9 + 1 && yPos > this.getHeight()/1.9 && yPos < this.getHeight()/1.25) {//jogar
			
			// switch e case - atuadores e condicionadores
			switch (motionEvent.getAction()) { // ação de touch
			case MotionEvent.ACTION_MOVE: // se mover dedo
			case MotionEvent.ACTION_DOWN: // se dedo fixar na tela
				mPosX = motionEvent.getX(); // mPosX recebe a medida da tela em
											// tamanho
				mPosY = motionEvent.getY(); // mPosY recebe a medida da tela em
											// altura
				// evento touching - botoes a esquerda
				touching2 = true;
				break;
			default:
				touching2 = false;
			}
			invalidate();
		}
		return true;
	}
}