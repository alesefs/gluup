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

public class MenuActivity extends Activity implements OnClickListener{
	 
	MediaPlayer bgsound_menuP;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		requestWindowFeature(Window.FEATURE_NO_TITLE);
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);		
		setContentView(R.layout.activity_menu);		

    	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		
    	Button jogar = (Button)findViewById(R.id.btnjogar);
	    Button ajuda = (Button)findViewById(R.id.btnajuda);
	    Button sobre = (Button)findViewById(R.id.btnsobre);
	    
	    jogar.setOnClickListener(this); 
	    ajuda.setOnClickListener(this);
	    sobre.setOnClickListener(this);
	
	    
	    bgsound_menuP = MediaPlayer.create(this, R.raw.bg_sounds_menus);
	    bgsound_menuP.start();
    	Log.i("init","loop");
    	//bgsound_menuP.isPlaying();
    	//bgsound_menuP.isLooping();
    	bgsound_menuP.setLooping(true);


	}

	public void onClick(View view) {

	    switch(view.getId()) {
        case R.id.btnjogar:
            Intent jogar = new Intent(MenuActivity.this,TimerActivity.class);  
            MenuActivity.this.startActivity(jogar);
            MenuActivity.this.finish();
            break;
    
        case R.id.btnajuda:
            Intent ajuda = new Intent(MenuActivity.this,HelpActivity.class);  
            MenuActivity.this.startActivity(ajuda);
            MenuActivity.this.finish();
            break;
	    
		case R.id.btnsobre:
	        Intent sobre = new Intent(MenuActivity.this,AboutActivity.class);  
	        MenuActivity.this.startActivity(sobre);
	        MenuActivity.this.finish();
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
