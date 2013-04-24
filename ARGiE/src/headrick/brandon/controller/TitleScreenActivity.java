package headrick.brandon.controller;


import headrick.brandon.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TitleScreenActivity extends Activity implements View.OnClickListener {
	/** Called when the activity is first created. */

	Button playGame, createGame, settings, about;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		@SuppressWarnings("unused")
		TextView tv = new TextView(this);
		setContentView(R.layout.title_screen);
		initializeVars();

	}

	private void initializeVars() {
		// TODO Auto-generated method stub
		playGame = (Button) findViewById(R.id.bPlayGame);
		createGame = (Button) findViewById(R.id.bCreateGame);
		settings = (Button) findViewById(R.id.bSettings);
		about = (Button) findViewById(R.id.bAbout);
		Log.d("RAD", ""+settings);
		playGame.setOnClickListener(this);
		createGame.setOnClickListener(this);
		settings.setOnClickListener(this);
		about.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent;
		switch (v.getId()){
		case R.id.bPlayGame:
			Log.w("myApp", "newgame");
			intent = new Intent(TitleScreenActivity.this, QuestChooserActivity.class);
			startActivity(intent);
			break;
		case R.id.bCreateGame:
			Log.w("myApp", "creategame");
			intent = new Intent(TitleScreenActivity.this, CreateGameActivity.class);
			startActivity(intent);
			break;
		case R.id.bSettings:
			Log.w("myApp", "settings");
			intent = new Intent(TitleScreenActivity.this, SettingsActivity.class);
			startActivity(intent);
			break;
		case R.id.bAbout:
			Log.w("myApp", "about");
			intent = new Intent(TitleScreenActivity.this, AboutScreenActivity.class);
			startActivity(intent);
		}
		
	}
}