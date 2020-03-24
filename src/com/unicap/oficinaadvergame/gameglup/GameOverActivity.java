package com.unicap.oficinaadvergame.gameglup;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class GameOverActivity extends Activity /*implements OnClickListener*/{
	
	GameOverView endview;
	@Override
	protected void onCreate(Bundle savedInstanceState) {    
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
	    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	    
	    endview = new GameOverView(this, null);
	    setContentView(endview);
    	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
	
	}
}

