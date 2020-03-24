package com.unicap.oficinaadvergame.gameglup;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.spashscreen.R;

public class AboutActivity extends Activity implements OnClickListener{
	
	MediaPlayer bgsound_menuP;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		 requestWindowFeature(Window.FEATURE_NO_TITLE);
	        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
	            WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.activity_about);
		
    	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

	    Button menu = (Button)findViewById(R.id.btnmenu);
    	Button jogar = (Button)findViewById(R.id.btnjogar);
	    
    	menu.setOnClickListener(this);
	    jogar.setOnClickListener(this);
    	
	    bgsound_menuP = MediaPlayer.create(this, R.raw.bg_sounds_menus);
	    bgsound_menuP.start();
    	Log.i("init","loop");
    	//bgsound_menuP.isPlaying();
    	//bgsound_menuP.isLooping();
    	bgsound_menuP.setLooping(true);
    	
	}
	
	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch(view.getId()) {
        case R.id.btnmenu:
            Intent menu = new Intent(AboutActivity.this,MenuActivity.class);  
            AboutActivity.this.startActivity(menu);
            AboutActivity.this.finish();
            break;
        case R.id.btnjogar:
            Intent jogar = new Intent(AboutActivity.this,TimerActivity.class);  
            AboutActivity.this.startActivity(jogar);
            AboutActivity.this.finish();
            break;
	    }
	}
	

	@Override
	protected void onPause() {
		super.onPause();
		bgsound_menuP.stop();
    }     

    @Override
    protected void onDestroy() {
    	super.onDestroy();
    	bgsound_menuP.release();
    }     

	@Override
	protected void onResume() {
	super.onResume();
		if(bgsound_menuP != null)
			bgsound_menuP.start();
		}

	
}
