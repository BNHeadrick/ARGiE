package headrick.brandon.controller;

import com.google.android.gms.common.GooglePlayServicesUtil;

import headrick.brandon.R;
import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

public class AboutScreenActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_screen);
		
		TextView legal = (TextView)findViewById(R.id.legal);
		legal.setText(GooglePlayServicesUtil.getOpenSourceSoftwareLicenseInfo(this));
		legal.setMovementMethod(new ScrollingMovementMethod());
	}
}
