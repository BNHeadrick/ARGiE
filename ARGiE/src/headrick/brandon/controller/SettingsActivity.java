package headrick.brandon.controller;

import headrick.brandon.R;
import android.app.Activity;
import android.os.Bundle;

/**
 * provides global application options to the user
 * @author Brandon Headrick
 *
 */
public class SettingsActivity extends Activity
{

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings_screen);
	}
}
