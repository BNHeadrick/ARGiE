package headrick.brandon.activities;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.model.*;

import headrick.brandon.R;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;

import android.os.Bundle;

import headrick.brandon.gamedata.GameSettingsState;
import headrick.brandon.gamedata.GameState;
import headrick.brandon.gamedata.Constants;
import headrick.brandon.models.QuestNode;
import headrick.brandon.utilities.DBReadWrite;
import headrick.brandon.utilities.MapHelper;

/**
 * The main view for game creation.  Provides the tools for CRUD of a game's
 * properties, including quest placement.
 * @author Brandon Headrick
 *
 */
public class CreateGameActivity extends FragmentActivity 
implements OnMapClickListener, OnMapLongClickListener, 
OnCameraChangeListener, OnInfoWindowClickListener, View.OnClickListener,
OnMarkerClickListener
{

	private GoogleMap mMap;
	Button saveGame, clearGame, deleteQuest, moveQuest, gameOptions;
	DBReadWrite dbReadWrite;
	AlertDialog.Builder alert;
	AlertDialog alertDialog;

    GameState gameState;
    GameSettingsState settingsState;
    MapHelper mapHelper;
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
	    
	    //rebuild the map for configuration changes
	    if(!GameState.getInstance().isEmpty()){
	    	QuestNode prevQuest = null;
	    	for(QuestNode aQuest : gameState.getQuestNodes()){
                MapHelper.getInstance().placeMapMarker(mMap, aQuest, gameState.questAlphaLabel);
	    		if(aQuest != GameState.getInstance().getRoot()){
	    			drawQuestPath(prevQuest, aQuest);
	    		}
	    		gameState.questAlphaLabel++;
	    		prevQuest = aQuest;
	    	}
	    }
	    
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
		deleteQuest = (Button) findViewById(R.id.bDeleteQuest);
		moveQuest = (Button) findViewById(R.id.bMoveQuest);
		gameOptions = (Button) findViewById(R.id.bGameOptions);

		saveGame.setOnClickListener(this);
        gameOptions.setOnClickListener(this);
		clearGame.setOnClickListener(this);

        gameState = GameState.getInstance();
        settingsState = GameSettingsState.getInstance();
        mapHelper = MapHelper.getInstance();

        //reset the labels from gameState
        gameState.questAlphaLabel = Constants.INITIAL_ALPHA_LABEL_VAL;
        gameState.questNumLabel = Constants.INITIAL_NUM_LABEL_VAL;
	}
    
	public void onClick(View v) {
		// TODO Auto-generated method stub
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
                            "Quest Saved! (SORT OF!)", Toast.LENGTH_LONG).show();
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
                    gameState.questAlphaLabel = Constants.INITIAL_ALPHA_LABEL_VAL;
                    gameState.removeAllQuests();
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
                Intent intent;
                intent = new Intent(CreateGameActivity.this, GameOptionsActivity.class);
                startActivity(intent);

                break;
		}
		
		
	}
	
	@Override
	public void onCameraChange(CameraPosition position) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onMapLongClick(LatLng point) {
		//the following draws a new label and path for the user and adds the new quest to the GameState object
		QuestNode newQuest = new QuestNode("title"+String.valueOf(gameState.questAlphaLabel), point,
                "script" + String.valueOf(gameState.questAlphaLabel), "answer"+String.valueOf(gameState.questAlphaLabel),
                settingsState.getGlobalRadiusThreshold());
		mapHelper.placeMapMarker(mMap, newQuest, gameState.questAlphaLabel);
		if(!gameState.isEmpty()){
			drawQuestPath(gameState.getTail(), newQuest);
		}
		gameState.addQuest(newQuest);
        gameState.questAlphaLabel++;

	}
	
	private void drawQuestPath(QuestNode startNode, QuestNode endNode){
		mMap.addPolyline((new PolylineOptions()).add(startNode.getPoint(), endNode.getPoint()));
	}
	
	@Override
	public void onMapClick(LatLng point) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onInfoWindowClick(Marker marker) {
		// TODO Auto-generated method stub
		//marker.remove();
		for(QuestNode aQuest : gameState.getQuestNodes()){
			if(aQuest.getMapMarker().equals(marker)){
				Intent intent = new Intent(CreateGameActivity.this, EditQuestActivity.class);
				startActivity(intent);
			}
		}
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outBundle){
		super.onSaveInstanceState(outBundle);
		
		
	}

	@Override
	public boolean onMarkerClick(Marker arg0) {
		// TODO Auto-generated method stub
		return false;
	}


}
