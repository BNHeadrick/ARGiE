package headrick.brandon.controller;

//import java.util.ArrayList;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import headrick.brandon.R;
//import android.app.Activity;
//import android.content.Intent;
//import android.content.res.AssetManager;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;
import android.widget.ToggleButton;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;

import android.os.Bundle;

import headrick.brandon.gamedata.GameState;
import headrick.brandon.gamedata.Constants;
import headrick.brandon.utilities.DBReadWrite;

/**
 * The main view for game creation.  Provides the tools for CRUD of a game's
 * properties, including quest placement.
 * @author Brandon Headrick
 *
 */
public class CreateGameActivity extends FragmentActivity 
implements OnMapClickListener, OnMapLongClickListener, 
OnCameraChangeListener, OnInfoWindowClickListener, View.OnClickListener, 
OnCheckedChangeListener{
	private char questLabel = Constants.INITIAL_LABEL_VAL; //temporariry just for debugging; remove later.
	private GoogleMap mMap;
	Button saveGame, clearGame, deleteQuest, moveQuest, gameOptions;
	ToggleButton editGame;
	DBReadWrite dbReadWrite;
	AlertDialog.Builder alert;
	AlertDialog alertDialog;
	//private ArrayList<LatLng> tempArrList = new ArrayList<LatLng>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_game_screen);
		initializeVars();
		setupMapIfNeeded();
		dbReadWrite = new DBReadWrite(this.getApplicationContext());
		

		mMap.setMyLocationEnabled(true);
		Criteria criteria = new Criteria();
	    LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	    String provider = locationManager.getBestProvider(criteria, false);
	    Location location = locationManager.getLastKnownLocation(provider);
	    LatLng userLoc = new LatLng(location.getLatitude(), location.getLongitude());
	    
	    setupStartLocation(userLoc);
	    
	}
	
	/**
	 * Moves the map to the passed in location
	 * @param point the LatLng object used to move the map to a specific location
	 */
	private void setupStartLocation(LatLng point){
		CameraUpdate center=
		        CameraUpdateFactory.newLatLng(new LatLng(point.latitude,
		                                                 point.longitude));
		    CameraUpdate zoom=CameraUpdateFactory.zoomTo(15);

		    mMap.moveCamera(center);
		    mMap.animateCamera(zoom);
	}
	
	private void setupMapIfNeeded() {
        if (mMap == null) {
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            if (mMap != null) {
                setupMap();
            }
        }
    }

    private void setupMap() {
        mMap.setOnMapClickListener(this);
        mMap.setOnMapLongClickListener(this);
        mMap.setOnCameraChangeListener(this);
        mMap.setOnInfoWindowClickListener(this);
    }
    
    private void initializeVars() {
		// TODO Auto-generated method stub
		saveGame = (Button) findViewById(R.id.bSaveGame);
		clearGame = (Button) findViewById(R.id.bClearGame);
		editGame = (ToggleButton) findViewById(R.id.tbEditGame);
		deleteQuest = (Button) findViewById(R.id.bDeleteQuest);
		moveQuest = (Button) findViewById(R.id.bMoveQuest);
		gameOptions = (Button) findViewById(R.id.bGameOptions);
		
		
		saveGame.setOnClickListener(this);
		clearGame.setOnClickListener(this);
		editGame.setOnCheckedChangeListener(this);
	}
    
	public void onClick(View v) {
		// TODO Auto-generated method stub
//		Intent intent;
		switch (v.getId()){
		case R.id.bSaveGame:
			Log.w("myApp", "bSaveGame");
			
			alert = new AlertDialog.Builder(this);
            alert.setTitle("Save The Game Map");
            alert.setMessage("Are you sure you want to save the current game map?");
 
            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            	dbReadWrite.writeQuestData();
    			Toast.makeText(getApplicationContext(), 
                        "Quest Saved!", Toast.LENGTH_LONG).show();
              }
            });
 
            alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
              public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
                  dialog.cancel();
              }
            });
            alertDialog = alert.create();
            alertDialog.show();
			
			
			break;
		
			
		case R.id.bClearGame:
			alert = new AlertDialog.Builder(this);
            alert.setTitle("Clear The Game Map");
            alert.setMessage("Are you sure you want to clear the game of all quests?");
 
            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            	questLabel = Constants.INITIAL_LABEL_VAL;
    			GameState.getInstance().removeAllQuests();
    			mMap.clear();
    			Toast.makeText(getApplicationContext(), 
                        "Game Map Cleared!", Toast.LENGTH_LONG).show();
              }
            });
 
            alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
              public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
                  dialog.cancel();
              }
            });
            alertDialog = alert.create();
            alertDialog.show();

			
			break;
			
		case R.id.bDeleteQuest:
			
			
			break;
		case R.id.bMoveQuest:
			
			
			break;
		case R.id.bGameOptions:
			
			
			break;
		}
		
		
	}
	
	@Override
	public void onCameraChange(CameraPosition position) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onMapLongClick(LatLng point) {
		// TODO Auto-generated method stub
		//System.out.println("lat long is: " + point);
		
		mMap.addMarker(new MarkerOptions().position(new LatLng(point.latitude, point.longitude))
			.title("Quest " + String.valueOf(questLabel)));
		
		Bitmap.Config conf = Bitmap.Config.ARGB_8888; 
		Bitmap bmp = Bitmap.createBitmap(200, 50, conf); 
		Canvas canvas = new Canvas(bmp);
		
		Paint paint = new Paint();
		paint.setColor(Color.RED);
		paint.setStyle(Style.FILL);
		paint.setTextSize(55);
		
		//below has debugging code!**/
		canvas.drawText(String.valueOf(questLabel), Constants.LABEL_X_OFFSET, Constants.LABEL_Y_OFFSET, paint); // paint defines the text color, stroke width, size
		
		/**/
		
		
		mMap.addMarker(new MarkerOptions()
			.position(new LatLng(point.latitude, point.longitude))
		    //.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker2))
		    .icon(BitmapDescriptorFactory.fromBitmap(bmp))
		    .anchor(0.5f, 1)
		    .visible(true)
		    );
		
        
        
        
        if(!GameState.getInstance().isEmpty()){
        	System.out.println(GameState.getInstance().getTail().getPoint() + " " + point);
        	//mMap.addPolyline((new PolylineOptions()).add(tempArrList.get(tempArrList.size()-1), point));
        	mMap.addPolyline((new PolylineOptions()).add(GameState.getInstance().getTail().getPoint(), point));
        }
        //tempArrList.add(point);
        GameState.getInstance().addQuest("title"+String.valueOf(questLabel), point, "script" + String.valueOf(questLabel), "answer"+String.valueOf(questLabel));
        
        questLabel++;
		
		
	}
	
	
	@Override
	public void onMapClick(LatLng point) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onInfoWindowClick(Marker marker) {
		// TODO Auto-generated method stub
		
	}

	//if the user clicks the edit button, change the viewable buttons on the bottom button bar to
	//reflect the changed context
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		if(!isChecked)
		{
			gameOptions.setVisibility(View.INVISIBLE);
			deleteQuest.setVisibility(View.VISIBLE);
			moveQuest.setVisibility(View.VISIBLE);
			
		}
		else{
			gameOptions.setVisibility(View.VISIBLE);
			deleteQuest.setVisibility(View.INVISIBLE);
			moveQuest.setVisibility(View.INVISIBLE);
		}
	}
}
