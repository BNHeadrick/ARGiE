package headrick.brandon.controller;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.*;
import headrick.brandon.R;
import headrick.brandon.gamedata.Constants;
import headrick.brandon.gamedata.GameState;
import headrick.brandon.model.QuestNode;

/**
 * Created by Brandon Headrick on 5/26/13.
 * !!as of now, in purely debugging state to prototype the background functionality of checking the user's GPS position.
 * Ideally, will eventually be a generic class that takes in the game previously selected by the user (taking in
 * data from the database) and sets up the GameState and Map in order for this class to manage the rest of the game.
 */
public class RunGameActivity extends FragmentActivity implements GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener, GoogleMap.OnCameraChangeListener, GoogleMap.OnInfoWindowClickListener {
    private GoogleMap mMap;
    private char questLabel = Constants.INITIAL_LABEL_VAL; //temporariry just for debugging; remove later.

    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.run_game_screen);
        //initializeVars();
        setupMapIfNeeded();

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
            for(QuestNode aQuest : GameState.getInstance().getQuestNodes()){
                placeMapMarker(aQuest);
                if(aQuest != GameState.getInstance().getRoot()){
                    drawQuestPath(prevQuest, aQuest);
                }
                questLabel++;
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

    }

    /**
     * places the marker and sets the marker reference for the
     */
    private void placeMapMarker(QuestNode questNode){
        LatLng point = questNode.getPoint();

        Marker newMark = mMap.addMarker(new MarkerOptions().position(new LatLng(point.latitude, point.longitude))
                .title("Quest " + String.valueOf(questLabel))
                .snippet(questNode.getTitle()));

        questNode.setMapMarker(newMark);

        Bitmap.Config conf = Bitmap.Config.ARGB_8888;
        Bitmap bmp = Bitmap.createBitmap(200, 50, conf);
        Canvas canvas = new Canvas(bmp);

        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(55);

        //below has debugging code!**/
        canvas.drawText(String.valueOf(questLabel), Constants.LABEL_X_OFFSET, Constants.LABEL_Y_OFFSET, paint); // paint defines the text color, stroke width, size

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(point.latitude, point.longitude))
                        //.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker2))
                .icon(BitmapDescriptorFactory.fromBitmap(bmp))
                .anchor(0.5f, 1)
                .visible(true)
        );

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
}
