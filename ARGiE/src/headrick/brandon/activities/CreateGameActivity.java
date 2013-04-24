package headrick.brandon.activities;

import java.util.ArrayList;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import headrick.brandon.R;
//import android.app.Activity;
//import android.content.Intent;
//import android.content.res.AssetManager;
import android.support.v4.app.FragmentActivity;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;

import android.os.Bundle;

import headrick.brandon.gamedata.GameState;

//public class CreateGameActivity extends Activity {
public class CreateGameActivity extends FragmentActivity 
implements OnMapClickListener, OnMapLongClickListener, OnCameraChangeListener {
	
	private GoogleMap mMap;
	private ArrayList<LatLng> tempArrList = new ArrayList<LatLng>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_game_screen);
		setupMapIfNeeded();

		mMap.setMyLocationEnabled(true);
		Criteria criteria = new Criteria();
	    LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	    String provider = locationManager.getBestProvider(criteria, false);
	    Location location = locationManager.getLastKnownLocation(provider);
	    LatLng userLoc = new LatLng(location.getLatitude(), location.getLongitude());
	    
	    setupStartLocation(userLoc);
	    
	}
	
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
    }
	
	@Override
	public void onCameraChange(CameraPosition position) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onMapLongClick(LatLng point) {
		// TODO Auto-generated method stub
		System.out.println("lat long is: " + point);
        mMap.addMarker(new MarkerOptions().position(new LatLng(point.latitude, point.longitude)).title("Quest"));
        
        
        if(GameState.getInstance().getRoot()!=null){
        	System.out.println(GameState.getInstance().getTail().getPoint() + " " + point);
        	//mMap.addPolyline((new PolylineOptions()).add(tempArrList.get(tempArrList.size()-1), point));
        	mMap.addPolyline((new PolylineOptions()).add(GameState.getInstance().getTail().getPoint(), point));
        }
        //tempArrList.add(point);
        GameState.getInstance().addQuest("title1", point, "script1", "answer1");
        
		
		
	}
	@Override
	public void onMapClick(LatLng point) {
		// TODO Auto-generated method stub
		
	}
}
