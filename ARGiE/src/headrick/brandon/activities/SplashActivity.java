package headrick.brandon.activities;

import headrick.brandon.R;
import android.app.Activity;
import android.content.Intent;
//import android.content.res.AssetManager;
import android.os.Bundle;
/**
 * Provides the Splash (title) Screen for the application.
 * May remove later.
 * @author Brandon Headrick
 *
 */
public class SplashActivity extends Activity {

//	MediaPlayer ourSound;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.splash_screen);
		
		Thread timer = new Thread(){
			
			@Override
			public void run(){
				try{
					sleep(1000);
				} 
				catch (InterruptedException ie){
					ie.printStackTrace();
				} 
				
				Intent intent = new Intent(SplashActivity.this, TitleScreenActivity.class);
                startActivity(intent);
			}
		};
		timer.start();

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();	//finish splash activity, get rid of it, unless they open app again.
	}

}