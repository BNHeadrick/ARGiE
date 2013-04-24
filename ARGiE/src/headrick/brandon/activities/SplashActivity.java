package headrick.brandon.activities;

import headrick.brandon.R;
import android.app.Activity;
import android.content.Intent;
//import android.content.res.AssetManager;
import android.os.Bundle;

public class SplashActivity extends Activity {

//	MediaPlayer ourSound;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		//AssetManager assetManager = getAssets();		
		//FileCopier.copy("earlyInput.xml", assetManager);
		
		setContentView(R.layout.splash_screen);
		
		// copy default-quest to sd card
		//FileCopier.copy("default-quest.xml", getAssets());
		
		
		Thread timer = new Thread(){
			
			@Override
			public void run(){
				try{
					sleep(30);
				} 
				catch (InterruptedException ie){
					ie.printStackTrace();
				} 
				
				Intent ourIntent = new Intent(SplashActivity.this, TitleScreenActivity.class);
                startActivity(ourIntent);
				
			}
		};
		timer.start();

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
//		ourSound.release();
		finish();	//finish splash activity, get rid of it, unless they open app again.
	}

}