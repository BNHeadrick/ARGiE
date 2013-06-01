package headrick.brandon.activities;


import headrick.brandon.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Provides the user with the application's main navigation options in order for the user
 * to choose what to do next.
 * @author Brandon Headrick
 *
 */
public class TitleScreenActivity extends Activity implements View.OnClickListener {
	/** Called when the activity is first created. */

	Button playGame, createGame, settings, about;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.title_screen);
		initializeVars();

	}

	private void initializeVars() {
		// TODO Auto-generated method stub
		playGame = (Button) findViewById(R.id.bPlayGame);
		createGame = (Button) findViewById(R.id.bCreateGame);
		settings = (Button) findViewById(R.id.bSettings);
		about = (Button) findViewById(R.id.bAbout);
		
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
            /* reimplement this later
			intent = new Intent(TitleScreenActivity.this, GameChooserActivity.class);
			startActivity(intent);
			*/

            intent = new Intent(TitleScreenActivity.this, RunGameActivity.class);
            startActivity(intent);
			break;

		case R.id.bCreateGame:
			intent = new Intent(TitleScreenActivity.this, CreateGameActivity.class);
			startActivity(intent);
			break;

		case R.id.bSettings:
			intent = new Intent(TitleScreenActivity.this, SettingsActivity.class);
			startActivity(intent);
			break;

		case R.id.bAbout:
			intent = new Intent(TitleScreenActivity.this, AboutScreenActivity.class);
			startActivity(intent);
		}
		
	}
}