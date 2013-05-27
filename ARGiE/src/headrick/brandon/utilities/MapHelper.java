package headrick.brandon.utilities;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.*;
import headrick.brandon.gamedata.Constants;
import headrick.brandon.models.QuestNode;

/**
 * Singleton class that does common map functions that are needed in multiple activities which use mapFragments
 */
public class MapHelper {
    private static MapHelper instance = null;

    private MapHelper(){
        //restrict instantiation
    }

    /**
     * returns the singleton object (instance) for the MapHelper class
     * @return the MapHelper singleton object
     */
    public static MapHelper getInstance(){
        if(instance == null){
            instance = new MapHelper();
        }
        return instance;
    }
    /**
     * places the marker and other drawable feedback for the user on the map
     */
    public void placeMapMarker(GoogleMap mMap, QuestNode questNode, char questLabel){
        LatLng point = questNode.getPoint();

        int alphaLightBlue = Color.argb(70, 173, 216, 230);

        //place a circle in the center of the area in which the quest is to be placed; represents threshold radius.
        CircleOptions circleOptions = new CircleOptions()
                .center(questNode.getPoint())
                .fillColor(alphaLightBlue)
                .strokeWidth(1)
                .radius(questNode.getRadialThreshold()); // In meters

        // Get back the mutable Circle
        mMap.addCircle(circleOptions);

        //place the marker for the quest on the map.
        Marker newMark = mMap.addMarker(new MarkerOptions().position(new LatLng(point.latitude, point.longitude))
                .title("Quest " + String.valueOf(questLabel))
                .snippet("Click this to change this quest's properties!"));

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

        //place a new mark on the map close to the previously added marker which displays the debug label
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(point.latitude, point.longitude))
                        //.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker2))
                .icon(BitmapDescriptorFactory.fromBitmap(bmp))
                .anchor(0.5f, 1)
                .visible(true)
        );

    }
}
