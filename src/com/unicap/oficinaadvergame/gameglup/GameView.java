package com.unicap.oficinaadvergame.gameglup;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.spashscreen.R;

public class GameView extends View implements Runnable{

	//declaração de pintura
	Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG), textpaint = new Paint(), texttempopaint = new Paint(), paintchange = new Paint();
	Canvas canvas;
	
	//imagem background
	private Background bg;

	//peixe preto
	private PeixePreto blacks; 
	
	//array cardume rosa
	private ArrayList<PeixeRosa> pinks;
	int n_pink = 3, j_pink;

	//array cardume azul
	private ArrayList<PeixeAzul> blues;
	int n_blue = 3, j_blue;

	//array cardume azul
	private ArrayList<PeixeVermelho> reds;
	int n_red = 3, j_red;
	
	//peixe ouro
	private PeixeOuro golds; 
	
	//particula E
	private Gluup[] gluup;
	
	//particula D
	private Gluup[] bluup;

	//iniciou o jogo?
	private boolean jogoIniciado = false;

	//registradores de onde voce tocou
	float mPosX, mPosY, mResTouchX, mResTouchY; 

	private boolean touching1; // boolean para ação do botao da esquerda
	private boolean touching2; // boolean para ação do botao da direita
	
	//marcadores de tempo e acção
	private final static int INTERVAL = 15;
	private boolean running = true;
	int timer;
	
	//cronometro
	private long time = System.currentTimeMillis() / 1000;
	int min, sec, currentTime, decrease, segundos;
	int tempo_i;

	
	//pontuação
	String hud_pontos, add_pontos;
	int hud_pontos_i, add_pontos_i;
	
	
	//nivel
	int nv = 70;
	int hud_nv_i;
	int nivel = 1;
	String hud_nv, addlevel;
	
	
	//sons
	MediaPlayer bolhas, fisgada, mordida, up, bgsound;
	
	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		setFocusable(true);
		
		tempo_i = ((Activity) context).getIntent().getIntExtra("tempo", 0);
		
		// bg sound_gameover
		bgsound = MediaPlayer.create(getContext(), R.raw.bgsound_jogo);
		bgsound.start();
		Log.i("init","loop");
		//bgsound.isPlaying();
		//bgsound.isLooping();
		bgsound.setLooping(true);

		
		Thread minhaThread = new Thread(this);
		minhaThread.setPriority(Thread.MIN_PRIORITY);
		minhaThread.start();
	}
	
	//status de inicio de jogo
	public void iniciojogo(){
		// TODO Auto-generated constructor stub
		
		//peixes rosa
		pinks = new ArrayList<PeixeRosa>();
		for(int i = 0; i < n_pink + j_blue; i++){
			int x = (int) (Math.random() * 38 + this.getWidth()/2);
			int y = (int) (Math.random() * 30 + (this.getHeight() + this.getHeight()*15/100));	
			pinks.add(new PeixeRosa(x, y, getResources()));
		}
		
		//peixes azul
		blues = new ArrayList<PeixeAzul>();
		for(int i = 0; i < n_blue + j_blue; i++){
			int x = (int) (Math.random() * 25 + this.getWidth()/2);
			int y = (int) (Math.random() * 30 + (this.getHeight() + this.getHeight()*10/100));		
			blues.add(new PeixeAzul(x, y, getResources()));
		}		
		
		//peixes vermelho
		reds = new ArrayList<PeixeVermelho>();
		for(int i = 0; i < n_red + j_red; i++){
			int x = (int) (Math.random() * 50 + this.getWidth()/2);
			int y = (int) (Math.random() * 30 + (this.getHeight() + this.getHeight()*20/100));		
			reds.add(new PeixeVermelho(x, y, getResources()));
		}
        
        //peixe ouro
		for (int i = 0; i < 1; i++){
			int x = (int) (Math.random() * 50 + this.getWidth()/1.5);
	  		int y = (int) (this.getHeight()/1.2);
			golds = new PeixeOuro(x, y, getResources());
		}
		
		//peixe preto
		for (int i = 0; i < 1; i++){
			int x = (int) (Math.random() * 2 + this.getWidth()/1.5);
			int y = (int) (this.getHeight()/2.65);
	        blacks = new PeixePreto(x, y, getResources());
		}
		
		//background
		for (int i = 0; i < 1; i++){
			int x = 0;
			int y = 0;
	        bg = new Background(x, y, getResources());
		}
	
		// particula E
		gluup = new Gluup[50];
		for (int i = 0; i < gluup.length; i++) {
			int y = this.getHeight() + i * this.getHeight()*10/100;
			int x = (int) (Math.random()*(this.getWidth()*0 + i * this.getWidth()*7/100));
			gluup[i] = new Gluup(x, y, getResources());
		}
		
		// particula D
		bluup = new Gluup[50];
		for (int i = 0; i < bluup.length; i++) {
			int y = this.getHeight() + i * this.getHeight()*10/100;
			int x = (int) ((Math.random()) * (this.getWidth()/0.5 + i * this.getWidth()*3/100));
			bluup[i] = new Gluup(x, y, getResources());
		}
		
		
		
		jogoIniciado = true;
	}
	
	//marcador de erros
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (running) {
			try {
				Thread.sleep(INTERVAL);
			} catch (Exception e) {
				Log.e("Jogo", "gluup... gluup...");
			}
			update();
		}
	}
	
	//metodo de movimento
	private void update() {
		// TODO Auto-generated method stub
		if (jogoIniciado==false) {
			return;
		}
		
		timer++;

		if (timer >= 601) { // se tempo maior de 100 milisegundos ele zera
			timer = 0;
		}

		
		//peixes rosa
		for (PeixeRosa sprite : pinks) {
			sprite.mexe(getWidth(), getHeight()/2);
			
			if (timer % 20 == 0){
				sprite.block(getWidth(), getHeight()/2);	
			}
			if(sprite.getY() > this.getHeight() - 25){
				sprite.subir(getWidth(), getHeight()/2);
			}
		}
		
		//peixes azul
		for (PeixeAzul sprite : blues) {
			sprite.mexe(getWidth(), getHeight()/2);
			
			if (timer % 18 == 0){
				sprite.block(getWidth(), getHeight()/2);	
			}
			if(sprite.getY() > this.getHeight() - 25){
				sprite.subir(getWidth(), getHeight()/2);
			}
		}
		
		//peixes vermelho
		for (PeixeVermelho sprite : reds) {
			sprite.mexe(getWidth(), getHeight()/2);
			
			if (timer % 16 == 0){
				sprite.block(getWidth(), getHeight()/2);	
			}
			if(sprite.getY() > this.getHeight() - 25){
				sprite.subir(getWidth(), getHeight()/2);
			}
		}		
		
		//peixe ouro
			golds.mexe(getWidth(), getHeight() / 2);
			if (timer % 15 == 0){
				golds.block(getWidth(), getHeight()/2);	
			}
			if(golds.getY() > this.getHeight() - 25){
				golds.subir(getWidth(), getHeight()/2);
			}

		//peixe preto
		blacks.mexe(getWidth(), getHeight() / 2);
		
		//invoca o método draw
		postInvalidate();
	}

	//metodo de desenhar na tela
	@SuppressLint("DrawAllocation")
	protected void onDraw (Canvas canvas){
		// TODO Auto-generated method stub
		super.dispatchDraw(canvas);
		super.onDraw(canvas);

		
		if (jogoIniciado==false) {
			iniciojogo();
		}
		
		// background
		bg.drawbg(canvas, paint);
		
		// background
		bg.drawpesecador1(canvas, paint);
		
		// background
		bg.drawbarco(canvas, paint);

		// background
		bg.drawpesecador2(canvas, paint);
			
		// background
		bg.drawpedras(canvas, paint);
		
		//peixes rosa
		for (PeixeRosa sprite : pinks) {
			sprite.draw(canvas,paint);
		}
		
		//peixes azul
		for (PeixeAzul sprite : blues) {
			sprite.draw(canvas,paint);
		}
		
		//peixes vermelho
		for (PeixeVermelho sprite : reds) {
			sprite.draw(canvas,paint);
		}
		
		//peixe ouro
		golds.draw(canvas, paint);
		
		//peixe preto
		blacks.draw(canvas, paint);
		
		//particula E
		for (int i = 0; i < gluup.length; i++) {
			gluup[i].draw(canvas, paint);
		}
		
		//particula D
		for (int i = 0; i < bluup.length; i++) {
			bluup[i].draw(canvas, paint);
		}
		
		anzol1(canvas);
		anzol2(canvas);
		
		tubarãoatack(canvas);
		
		leftside(canvas);
		rightside(canvas);
		
		hud(canvas);
	}
	
	//colição do tubarão
	private void tubarãoatack(Canvas canvas) {
		// TODO Auto-generated method stub
		for (PeixeRosa sprite : pinks) {
			if(sprite.getX() <= blacks.getX() + blacks.black.getWidth()*65/100 && sprite.getX() >= blacks.getX() - blacks.black.getWidth()*35/100
					&& sprite.getY() <= blacks.getY() + blacks.black.getHeight()*65/100 && sprite.getY() >= blacks.getY() - blacks.black.getHeight()*35/100){	
				blacks.mordida(canvas, paint);
				sprite.colisao(getWidth(), getHeight());
				// fx das mordida
				mordida = MediaPlayer.create(getContext(), R.raw.mordida_fx);
				mordida.start();
			}
		}
		for (PeixeAzul sprite : blues) {
			if(sprite.getX() <= blacks.getX() + blacks.black.getWidth()*65/100 && sprite.getX() >= blacks.getX() - blacks.black.getWidth()*35/100
					&& sprite.getY() <= blacks.getY() + blacks.black.getHeight()*65/100 && sprite.getY() >= blacks.getY() - blacks.black.getHeight()*35/100){	
				blacks.mordida(canvas, paint);
				sprite.colisao(getWidth(), getHeight());			
				// fx das mordida
				mordida = MediaPlayer.create(getContext(), R.raw.mordida_fx);
				mordida.start();
			}
		}
		for (PeixeVermelho sprite : reds) {
			if(sprite.getX() <= blacks.getX() + blacks.black.getWidth()*65/100 && sprite.getX() >= blacks.getX() - blacks.black.getWidth()*35/100
					&& sprite.getY() <= blacks.getY() + blacks.black.getHeight()*65/100 && sprite.getY() >= blacks.getY() - blacks.black.getHeight()*35/100){	
				blacks.mordida(canvas, paint);
				sprite.colisao(getWidth(), getHeight());
				// fx das mordida
				mordida = MediaPlayer.create(getContext(), R.raw.mordida_fx);
				mordida.start();
			}
		}
		if(golds.getX() <= blacks.getX() + blacks.black.getWidth()*65/100 && golds.getX() >= blacks.getX() - blacks.black.getWidth()*35/100
				&& golds.getY() <= blacks.getY() + blacks.black.getHeight()*65/100 && golds.getY() >= blacks.getY() - blacks.black.getHeight()*35/100){	
			blacks.mordida(canvas, paint);
			golds.colisao(getWidth(), getHeight());
			// fx das mordida
			mordida = MediaPlayer.create(getContext(), R.raw.mordida_fx);
			mordida.start();
		}
	}

	
	//cardumes colide anzol1
	private void anzol1(Canvas canvas) {
		// TODO Auto-generated method stub
		for (PeixeRosa sprite : pinks) {
			if (sprite.getX() >= canvas.getWidth()/2 - canvas.getWidth()*21/100
				&& sprite.getX() <= canvas.getWidth()/2 - canvas.getWidth()*15/100
				&& sprite.getY() >= canvas.getHeight()/2 - canvas.getHeight()*10/100
				&& sprite.getY() <= canvas.getHeight()/2 - canvas.getHeight()*6/100){
			sprite.colisao(getWidth(), getHeight());
			hud_pontos_i += 5;
					
			//grafico de edição de pontos
			paintchange.setColor(0xfff45fef);
			paintchange.setTextSize((float) (canvas.getWidth()*10/100));
			paintchange.setFakeBoldText(true);
			add_pontos_i = 5;
			add_pontos = "+" + add_pontos_i;
			for (int i = 0; i < 100; i++){
				canvas.drawText(add_pontos, (float) (canvas.getWidth()/2 - canvas.getWidth()*18/100), (float) (canvas.getHeight()/2 - canvas.getHeight()*8/100), paintchange);
			}
			
			// fx das fisgada
			fisgada = MediaPlayer.create(getContext(), R.raw.fisgada_fx);
			fisgada.start();
			}
		}
		for (PeixeAzul sprite : blues) {
			if (sprite.getX() >= canvas.getWidth()/2 - canvas.getWidth()*21/100
				&& sprite.getX() <= canvas.getWidth()/2 - canvas.getWidth()*15/100
				&& sprite.getY() >= canvas.getHeight()/2 - canvas.getHeight()*10/100
				&& sprite.getY() <= canvas.getHeight()/2 - canvas.getHeight()*6/100){
			sprite.colisao(getWidth(), getHeight());
			hud_pontos_i += 7;
			
			//grafico de edição de pontos
			paintchange.setColor(0xff140b92);
			paintchange.setTextSize((float) (canvas.getWidth()*10/100));
			paintchange.setFakeBoldText(true);
			add_pontos_i = 7;
			add_pontos = "+" + add_pontos_i;
			for (int i = 0; i < 100; i++){
				canvas.drawText(add_pontos, (float) (canvas.getWidth()/2 - canvas.getWidth()*18/100), (float) (canvas.getHeight()/2 - canvas.getHeight()*8/100), paintchange);
			}
			
			// fx das fisgada
			fisgada = MediaPlayer.create(getContext(), R.raw.fisgada_fx);
			fisgada.start();
			}
		}
		for (PeixeVermelho sprite : reds) {
			if (sprite.getX() >= canvas.getWidth()/2 - canvas.getWidth()*21/100
				&& sprite.getX() <= canvas.getWidth()/2 - canvas.getWidth()*15/100
				&& sprite.getY() >= canvas.getHeight()/2 - canvas.getHeight()*10/100
				&& sprite.getY() <= canvas.getHeight()/2 - canvas.getHeight()*6/100){
			sprite.colisao(getWidth(), getHeight());
			hud_pontos_i += 10;
			
			//grafico de edição de pontos
			paintchange.setColor(0xfff14f1d);
			paintchange.setTextSize((float) (canvas.getWidth()*10/100));
			paintchange.setFakeBoldText(true);
			add_pontos_i = 10;
			add_pontos = "+" + add_pontos_i;
			for (int i = 0; i < 100; i++){
				canvas.drawText(add_pontos, (float) (canvas.getWidth()/2 - canvas.getWidth()*18/100), (float) (canvas.getHeight()/2 - canvas.getHeight()*8/100), paintchange);
			}
			
			// fx das fisgada
			fisgada = MediaPlayer.create(getContext(), R.raw.fisgada_fx);
			fisgada.start();
			}
		}
		if (golds.getX() >= canvas.getWidth()/2 - canvas.getWidth()*21/100
				&& golds.getX() <= canvas.getWidth()/2 - canvas.getWidth()*15/100
				&& golds.getY() >= canvas.getHeight()/2 - canvas.getHeight()*10/100
				&& golds.getY() <= canvas.getHeight()/2 - canvas.getHeight()*6/100){
			golds.colisao(getWidth(), getHeight());
			hud_pontos_i += 20;
			
			//grafico de edição de pontos
			paintchange.setColor(0xfff2f02d);
			paintchange.setTextSize((float) (canvas.getWidth()*10/100));
			paintchange.setFakeBoldText(true);
			add_pontos_i = 20;
			add_pontos = "+" + add_pontos_i;
			for (int i = 0; i < 100; i++){
				canvas.drawText(add_pontos, (float) (canvas.getWidth()/2 - canvas.getWidth()*18/100), (float) (canvas.getHeight()/2 - canvas.getHeight()*8/100), paintchange);
			}
			
			// fx das fisgada
			fisgada = MediaPlayer.create(getContext(), R.raw.fisgada_fx);
			fisgada.start();
		}
	}

	//cardumes colide anzol2
	private void anzol2(Canvas canvas) {
		// TODO Auto-generated method stub
		for (PeixeRosa sprite : pinks) {
			if (sprite.getX() >= canvas.getWidth()/2 + canvas.getWidth()*11/100
				&& sprite.getX() <= canvas.getWidth()/2 + canvas.getWidth()*15/100
				&& sprite.getY() >= canvas.getHeight()/2 - canvas.getHeight()*10/100
				&& sprite.getY() <= canvas.getHeight()/2 - canvas.getHeight()*6/100){
			sprite.colisao(getWidth(), getHeight());
			hud_pontos_i += 5;
			
			//grafico de edição de pontos
			paintchange.setColor(0xfff45fef);
			paintchange.setTextSize((float) (canvas.getWidth()*10/100));
			paintchange.setFakeBoldText(true);
			add_pontos_i = 5;
			add_pontos = "+" + add_pontos_i;
			
			for (int i = 0; i < 100; i++){
				canvas.drawText(add_pontos, (float) (canvas.getWidth()/2 + canvas.getWidth()*13/100), (float) (canvas.getHeight()/2 - canvas.getHeight()*8/100), paintchange);
			}
			
			// fx das fisgada
			fisgada = MediaPlayer.create(getContext(), R.raw.fisgada_fx);
			fisgada.start();
			}
		}
		for (PeixeAzul sprite : blues) {
			if (sprite.getX() >= canvas.getWidth()/2 + canvas.getWidth()*11/100
				&& sprite.getX() <= canvas.getWidth()/2 + canvas.getWidth()*15/100
				&& sprite.getY() >= canvas.getHeight()/2 - canvas.getHeight()*10/100
				&& sprite.getY() <= canvas.getHeight()/2 - canvas.getHeight()*6/100){
			sprite.colisao(getWidth(), getHeight());
			hud_pontos_i += 7;
			
			//grafico de edição de pontos
			paintchange.setColor(0xff140b92);
			paintchange.setTextSize((float) (canvas.getWidth()*10/100));
			paintchange.setFakeBoldText(true);
			add_pontos_i = 7;
			add_pontos = "+" + add_pontos_i;
			for (int i = 0; i < 100; i++){
				canvas.drawText(add_pontos, (float) (canvas.getWidth()/2 + canvas.getWidth()*13/100), (float) (canvas.getHeight()/2 - canvas.getHeight()*8/100), paintchange);
			}
			
			// fx das fisgada
			fisgada = MediaPlayer.create(getContext(), R.raw.fisgada_fx);
			fisgada.start();
			}
		}
		for (PeixeVermelho sprite : reds) {
			if (sprite.getX() >= canvas.getWidth()/2 + canvas.getWidth()*11/100
				&& sprite.getX() <= canvas.getWidth()/2 + canvas.getWidth()*15/100
				&& sprite.getY() >= canvas.getHeight()/2 - canvas.getHeight()*10/100
				&& sprite.getY() <= canvas.getHeight()/2 - canvas.getHeight()*6/100){
			sprite.colisao(getWidth(), getHeight());
			hud_pontos_i += 10;
			
			//grafico de edição de pontos
			paintchange.setColor(0xfff14f1d);
			paintchange.setTextSize((float) (canvas.getWidth()*10/100));
			paintchange.setFakeBoldText(true);
			add_pontos_i = 10;
			add_pontos = "+" + add_pontos_i;
			for (int i = 0; i < 100; i++){
				canvas.drawText(add_pontos, (float) (canvas.getWidth()/2 + canvas.getWidth()*13/100), (float) (canvas.getHeight()/2 - canvas.getHeight()*8/100), paintchange);
			}
			
			// fx das fisgada
			fisgada = MediaPlayer.create(getContext(), R.raw.fisgada_fx);
			fisgada.start();
			}
		}
		if (golds.getX() >= canvas.getWidth()/2 + canvas.getWidth()*11/100
				&& golds.getX() <= canvas.getWidth()/2 + canvas.getWidth()*15/100
				&& golds.getY() >= canvas.getHeight()/2 - canvas.getHeight()*10/100
				&& golds.getY() <= canvas.getHeight()/2 - canvas.getHeight()*6/100){
			golds.colisao(getWidth(), getHeight());
			hud_pontos_i += 20;
			
			//grafico de edição de pontos
			paintchange.setColor(0xfff2f02d);
			paintchange.setTextSize((float) (canvas.getWidth()*10/100));
			paintchange.setFakeBoldText(true);
			add_pontos_i = 20;
			add_pontos = "+" + add_pontos_i;
			for (int i = 0; i < 100; i++){
				canvas.drawText(add_pontos, (float) (canvas.getWidth()/2 + canvas.getWidth()*13/100), (float) (canvas.getHeight()/2 - canvas.getHeight()*8/100), paintchange);
			}
			
			// fx das fisgada
			fisgada = MediaPlayer.create(getContext(), R.raw.fisgada_fx);
			fisgada.start();	
		}
	}
	
	
	//lado esquerdo
	private void leftside(Canvas canvas) {
		// TODO Auto-generated method stub
		if (touching1 == false){
			for (int i = 0; i < gluup.length; i++) {
				gluup[i].remove(getHeight()/3, getWidth()/2);
			}
			return;
		}
		
		if (touching1 == true){
			
			if (timer >= 121 && timer <= 150 || timer >= 271 && timer <= 300 || timer >= 421 && timer <= 450 || timer >= 571 && timer <= 600) {
				touching1 = false;
			}
			
			// bolhas fx
			bolhas = MediaPlayer.create(getContext(), R.raw.bolhas_fx);
			bolhas.start();
		
			//para peixe ouro
			for (int i = 0; i < 1; i++) {
				
				if(golds.getX() + golds.xspeed < canvas.getWidth()/2 + 1){
					golds.subir(getWidth(), getHeight() / 2);		
				}
				
				if(golds.getY() + golds.yspeed <= canvas.getHeight()/3 + 25){
					golds.block(getWidth(), getHeight() / 2);
				}
			}
			
			//para peixes rosa
			for (PeixeRosa sprite : pinks) {
				
				if(sprite.getX() + sprite.xspeed < canvas.getWidth()/2 + 1){
					sprite.subir(getWidth(), getHeight() / 2);	
				}
				
				if(sprite.getY() + sprite.yspeed <= canvas.getHeight()/3 + 25){
					sprite.block(getWidth(), getHeight() / 2);
				}
			}
			
			//para peixes azul	
			for (PeixeAzul sprite : blues) {
					
				if(sprite.getX() + sprite.xspeed < canvas.getWidth()/2 + 1){
					sprite.subir(getWidth(), getHeight() / 2);	
				}
				
				if(sprite.getY() + sprite.yspeed <= canvas.getHeight()/3 + 25){
					sprite.block(getWidth(), getHeight() / 2);
				}
			}
			
			//para peixes vermelhos
			for (PeixeVermelho sprite : reds) {
				if(sprite.getX() + sprite.xspeed < canvas.getWidth()/2 + 1){
					sprite.subir(getWidth(), getHeight() / 2);	
				}
				
				if(sprite.getY() + sprite.yspeed <= canvas.getHeight()/3 + 25){
					sprite.block(getWidth(), getHeight() / 2);
				}
			}
			
			//particula E
			for (int i = 0; i < gluup.length; i++) {
				gluup[i].mexe(getHeight()/3, getWidth()/2);
			}
			
		}	
		
		invalidate();
	}
	

	//lado direito
	private void rightside(Canvas canvas) {
		// TODO Auto-generated method stub
		if (touching2 == false){
			for (int i = 0; i < bluup.length; i++) {
				bluup[i].deleta(getHeight()/3, getWidth()/2);
			}
			return;
		}
		
		if (touching2 == true){
			
			if (timer >= 121 && timer <= 150 || timer >= 271 && timer <= 300 || timer >= 421 && timer <= 450 || timer >= 571 && timer <= 600) {
				touching2 = false;
			}
				
			// bolhas fx
			bolhas = MediaPlayer.create(getContext(), R.raw.bolhas_fx);
			bolhas.start();
			
			//para peixe ouro
			for (int i = 0; i < 1; i++) {
	
				if(golds.getX() + golds.xspeed > canvas.getWidth()/2 - 1){
					golds.subir(getWidth(), getHeight() / 2);		
				}
				
				if(golds.getY() + golds.yspeed <= canvas.getHeight()/3 + 25){
					golds.block(getWidth(), getHeight() / 2);
				}		
			}
			
			//para peixes rosa
			for (PeixeRosa sprite : pinks) {
				
				if(sprite.getX() + sprite.xspeed > canvas.getWidth()/2 - 1){
					sprite.subir(getWidth(), getHeight() / 2);	
				}
				
				if(sprite.getY() + sprite.yspeed <= canvas.getHeight()/3 + 25){
					sprite.block(getWidth(), getHeight() / 2);
				}
			}
			
			//para peixes azul	
			for (PeixeAzul sprite : blues) {
					
				if(sprite.getX() + sprite.xspeed > canvas.getWidth()/2 - 1){
					sprite.subir(getWidth(), getHeight() / 2);	
				}
				
				if(sprite.getY() + sprite.yspeed <= canvas.getHeight()/3 + 25){
					sprite.block(getWidth(), getHeight() / 2);
				}
			}
			
			//para peixes vermelhos
			for (PeixeVermelho sprite : reds) {
				
				if(sprite.getX() + sprite.xspeed > canvas.getWidth()/2 - 1){
					sprite.subir(getWidth(), getHeight() / 2);	
				}
				
				if(sprite.getY() + sprite.yspeed <= canvas.getHeight()/3 + 25){
					sprite.block(getWidth(), getHeight() / 2);
				}				
			}

			//particula D
			for (int i = 0; i < bluup.length; i++) {
				bluup[i].move(getHeight()/3, (int) (getWidth()/2));
			}
		}		
		invalidate();
	}
		

	// onTouchEvent - registrando os toques na tela
	@Override
	public boolean onTouchEvent(MotionEvent motionEvent) {
		// TODO Auto-generated method stub

		// registradores do toque em decimal
		float xPos = (float) motionEvent.getX();
		float yPos = (float) motionEvent.getY();

		//menor que a metade da tela
		// condicional if(se) - me pergunta e eu respondo a ordem
		if (xPos < this.getWidth()/2 + 1 && yPos > this.getHeight()/4) {

			// switch e case - atuadores e condicionadores
			switch (motionEvent.getAction()) { // ação de touch
			case MotionEvent.ACTION_MOVE: // se mover dedo
			case MotionEvent.ACTION_DOWN:
				mPosX = motionEvent.getX(); // mPosX recebe a medida da tela em tamanho
				mPosY = motionEvent.getY(); // mPosY recebe a medida da tela em altura
				
				//libera touch 1
				touching1 = true;		
				
				break;
			default:
				
				//trava touch 1
				touching1 = false;
			}
			invalidate();
		}
			
		//maior que a metade da tela
		// condicional if(se) - me pergunta e eu respondo a ordem
		if (xPos > this.getWidth()/2 - 1 && yPos > this.getHeight()/4) {

			// switch e case - atuadores e condicionadores
			switch (motionEvent.getAction()) { // ação de touch
			case MotionEvent.ACTION_MOVE: // se mover dedo
			case MotionEvent.ACTION_DOWN:
				mPosX = motionEvent.getX(); // mPosX recebe a medida da tela em tamanho
				mPosY = motionEvent.getY(); // mPosY recebe a medida da tela em altura
				
				//libera touch 2
				touching2 = true;

				break;
			default:
				//trava touch 2
				touching2 = false;
			}
			invalidate();
		}

		return true;
	}
	
	//hud
	private void hud(Canvas canvas) {
		// TODO Auto-generated method stub
		String tempo;
		
		textpaint.setColor(0xff018bbc);
		textpaint.setTextSize((float) (canvas.getWidth()*5/100));
		
		texttempopaint.setColor(0xff018bbc);
		texttempopaint.setTextSize((float) (canvas.getWidth()*5/100));
		
		
		segundos = tempo_i * 60;
		
		currentTime = (int) ((System.currentTimeMillis()/1000)-time);
		decrease = segundos - currentTime;
		
		min = decrease / 60;
		sec = decrease % 60;
		
		if ( min == 0 && sec <= 15){
			texttempopaint.setColor(Color.RED);
			texttempopaint.setFakeBoldText(true);
			textpaint.setFakeBoldText(true);
			
			if(sec % 2 == 0){
				texttempopaint.setShadowLayer(1, nivel, nivel, Color.TRANSPARENT);
			} else {
				texttempopaint.setShadowLayer(3, 3, 3, Color.RED);			
			}
		}
		
		
		if (sec < 10){
			tempo = min + ":0" + sec;
		} else {
			tempo = min + ":" + sec;
		}
		
		canvas.drawText(tempo, (float) (canvas.getWidth()*0 + canvas.getWidth()*15/100), (float) (canvas.getHeight()*0 + canvas.getHeight()*8.3/100), texttempopaint);

		
		if (min == 0 && sec == 1){	
			gameover();
		}
		
		//pontos
		hud_pontos = "" + hud_pontos_i;
		canvas.drawText(hud_pontos, (float) (canvas.getWidth() - canvas.getWidth()*17/100), (float) (canvas.getHeight()*0 + canvas.getHeight()*8.3/100), textpaint);
		
		//nivel
		hud_nv_i = hud_pontos_i / nv + 1;
		int lvl = hud_nv_i;
		if(nivel != lvl && hud_pontos_i > 0){
			nivel = lvl;
			//azul
			if (blues.size() <= 7){
				int x_azul = (int) (Math.random() * 25 + this.getWidth()/2);
				int y_azul = (int) (Math.random() * 30 + (this.getHeight() + this.getHeight()*10/100));
				blues.add(new PeixeAzul(x_azul, y_azul, getResources()));
			}
			//rosa
			if (pinks.size() <= 7){
				int x_rosa = (int) (Math.random() * 38 + this.getWidth()/2);
				int y_rosa = (int) (Math.random() * 30 + (this.getHeight() + this.getHeight()*15/100));
				pinks.add(new PeixeRosa(x_rosa, y_rosa, getResources()));
			}
			//vermelho
			if (reds.size() <= 7){
				int x_vermelho = (int) (Math.random() * 50 + this.getWidth()/2);
				int y_vermelho = (int) (Math.random() * 30 + (this.getHeight() + this.getHeight()*20/100));
				reds.add(new PeixeVermelho(x_vermelho, y_vermelho, getResources()));
			}
			
			//grafico de edição de pontos
			paintchange.setColor(0xffdcb451);
			paintchange.setTextSize((float) (canvas.getWidth()*10/100));
			paintchange.setFakeBoldText(true);
			addlevel = "NIVEL " + nivel;
			for (int i = 0; i < 50; i++){
				canvas.drawText(addlevel, (float) (canvas.getWidth()/2 - canvas.getWidth()*18/100), (float) (canvas.getHeight()/2 + canvas.getHeight()*8/100), paintchange);
			}
			
			// fx do up
			up = MediaPlayer.create(getContext(), R.raw.up_fx);
			up.start();
		}
 
		hud_nv = "" + nivel;
		canvas.drawText(hud_nv, (float) (canvas.getWidth() - canvas.getWidth()*50/100), (float) (canvas.getHeight()*0 + canvas.getHeight()*8.3/100), textpaint);
		
		
		//marcardor de FPS
		String FPS = "counter: " + timer;
		canvas.drawText(FPS, this.getWidth() - 100, this.getHeight() + 15, paint);
	}

	
	//game over
	private void gameover() {
		// TODO Auto-generated method stub
		running = false;
		Context ctx = getContext();
		((Activity) ctx).finish();
		Intent intent = new Intent(ctx, GameOverActivity.class);
		intent.putExtra("PONTOS", hud_pontos_i);
		ctx.startActivity(intent);
	}

	//termina o jogo
	public void release() {
	// TODO Auto-generated method stub
		running = false;
	}
	
}
