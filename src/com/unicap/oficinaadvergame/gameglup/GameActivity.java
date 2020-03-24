package com.unicap.oficinaadvergame.gameglup;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class GameActivity extends Activity{
	
	GameView view; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		view = new GameView (this, null);
		setContentView(view);
    	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
	
	} 
	
	
	@Override
	protected void onPause() {
		super.onPause();
		view.bgsound.stop();
    }     

    @Override
    protected void onDestroy() {
    	super.onDestroy();
    	view.bgsound.release();
    	view.release();
    }     

	/*@Override
	protected void onResume() {
	super.onResume();
		if(view.bgsound != null)
			view.bgsound.start();
		}*/
}