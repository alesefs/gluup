package com.unicap.oficinaadvergame.gameglup;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.example.spashscreen.R;

public class MainActivity extends Activity{

	int timesplash = 3000;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        	getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        		WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.activity_main);
		
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		
	new Handler().postDelayed(new Runnable() {
	           
            @Override
            public void run() {
            	
            	Intent splash = new Intent(MainActivity.this, MenuActivity.class);
            	MainActivity.this.startActivity(splash);
            	MainActivity.this.finish();
            	
            }
        }, timesplash);
	}	
}
