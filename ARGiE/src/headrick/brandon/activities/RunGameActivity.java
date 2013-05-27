package headrick.brandon.activities;

import android.content.Context;
import android.location.*;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;
import headrick.brandon.R;
import headrick.brandon.gamedata.Constants;
import headrick.brandon.gamedata.GameSettingsState;
import headrick.brandon.gamedata.GameState;
import headrick.brandon.models.QuestNode;
import headrick.brandon.utilities.MapHelper;

/**
 * Created by Brandon Headrick on 5/26/13.
 * !!as of now, in purely debugging state to prototype the background functionality of checking the user's GPS position.
 * Ideally, will eventually be a generic class that takes in the game previously selected by the user (taking in
 * data from the database) and sets up the GameState and Map in order for this class to manage the rest of the game.
 */
public class RunGameActivity extends FragmentActivity implements GoogleMap.OnMapClickListener,
        GoogleMap.OnMapLongClickListener, GoogleMap.OnCameraChangeListener,
        GoogleMap.OnInfoWindowClickListener, LocationListener, LocationSource{
    private GoogleMap mMap;
    final int RQS_GooglePlayServices = 1;
    private QuestNode nextTargetQuest;
    private LocationManager locationManager;
    private OnLocationChangedListener onLocationChangedListener;
    Criteria criteria;

    GameState gameState;
    GameSettingsState settingsState;
    MapHelper mapHelper;

    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.run_game_screen);
        initializeVars();
        setupMapIfNeeded();

        mMap.setMyLocationEnabled(true);
        criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        String provider = locationManager.getBestProvider(criteria, false);
        Location location = locationManager.getLastKnownLocation(provider);
        LatLng userLoc = new LatLng(location.getLatitude(), location.getLongitude());

        setupStartLocation(userLoc);

        //rebuild the map for configuration changes
        if(!gameState.isEmpty()){
            QuestNode prevQuest = null;
            for(QuestNode aQuest : gameState.getQuestNodes()){
                mapHelper.placeMapMarker(mMap, aQuest, gameState.questAlphaLabel);
                if(aQuest != gameState.getRoot()){
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
        gameState = GameState.getInstance();
        settingsState = GameSettingsState.getInstance();
        mapHelper = MapHelper.getInstance();

        if(!gameState.isEmpty()){
            nextTargetQuest = gameState.getRoot();
        }

        //reset the labels from gameState
        gameState.questAlphaLabel = Constants.INITIAL_ALPHA_LABEL_VAL;
        gameState.questNumLabel = Constants.INITIAL_NUM_LABEL_VAL;
    }

    private void drawQuestPath(QuestNode startNode, QuestNode endNode){
        mMap.addPolyline((new PolylineOptions()).add(startNode.getPoint(), endNode.getPoint()));
    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public void onMapLongClick(LatLng latLng) {

    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {

    }

    @Override
    public void onInfoWindowClick(Marker marker) {

    }

    @Override
    protected void onPause(){
        super.onPause();
        mMap.setLocationSource(null);
        locationManager.removeUpdates(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());

        if (resultCode == ConnectionResult.SUCCESS){
            Toast.makeText(getApplicationContext(),
                    "isGooglePlayServicesAvailable SUCCESS",
                    Toast.LENGTH_LONG).show();

            //Register for location updates using a Criteria, and a callback on the specified looper thread.
            locationManager.requestLocationUpdates(
                    0L,    //minTime
                    0.0f,    //minDistance
                    criteria,  //criteria
                    this,    //listener
                    null);   //looper

            //Replaces the location source of the my-location layer.
            mMap.setLocationSource(this);

        }else{
            GooglePlayServicesUtil.getErrorDialog(resultCode, this, RQS_GooglePlayServices);
        }

    }


    private boolean isAtNextValidLocation(){

        float[] distance = new float[2];
        //below is debug placeholder code; change when refactor of how quests are managed during gameplay
        if(!gameState.isEmpty()){
            Location.distanceBetween( mMap.getMyLocation().getLatitude(), mMap.getMyLocation().getLongitude(),
                    nextTargetQuest.getPoint().latitude, nextTargetQuest.getPoint().longitude, distance);

            if( distance[0] < nextTargetQuest.getRadialThreshold()  ){
                Toast.makeText(getBaseContext(), "Inside " + distance[0] + " " + distance[1], Toast.LENGTH_LONG).show();
                //currentQuest = nextQuest;
                //here a REAL linkedlist implementation needs to exist.
                return true;
            } else {
                //Toast.makeText(getBaseContext(), "Outside " + distance[0] + " " + distance[1], Toast.LENGTH_LONG).show();
            }
        }
        return false;
    }

    @Override
    public void onLocationChanged(Location location) {
        if (onLocationChangedListener != null) {
            onLocationChangedListener.onLocationChanged(location);
            isAtNextValidLocation();
        }
    }

    @Override
    public void onProviderDisabled(String arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderEnabled(String arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
        // TODO Auto-generated method stub

    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        this.onLocationChangedListener = onLocationChangedListener;
    }

    @Override
    public void deactivate() {

        this.onLocationChangedListener = null;

    }
}
