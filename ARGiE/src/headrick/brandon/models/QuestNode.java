package headrick.brandon.models;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

/**
 * Quest Object that holds individual quest data.
 * @author Brandon Headrick
 *
 */
public class QuestNode {
	private String title = null, script = null, answer = null;
	private LatLng point;
	private int id;	//only used for database
    private double radialThreshold;
	private Marker mapMarker;
	
	public QuestNode(){
		
	}

    //constructor used when creating objects outside of database influence
	public QuestNode(String title, LatLng point, String script, String answer, double radialThreshold){
		this.title = title;
		this.point = point;
		this.script = script;
		this.answer = answer;
        this.radialThreshold = radialThreshold;
    }
	
	//constructor to be used when creating objects from a database
	public QuestNode(int id, String title, LatLng point, String script, String answer, double radialThreshold){
		this.id = id;
		this.title = title;
		this.point = point;
		this.script = script;
		this.answer = answer;
        this.radialThreshold = radialThreshold;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	public LatLng getPoint() {
		return point;
	}

	public void setPoint(LatLng point) {
		this.point = point;
	}
	
	public double getLatitude(){
		return point.latitude;
	}
	
	public double getLongitude(){
		return point.longitude;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Marker getMapMarker() {
		return mapMarker;
	}

	public void setMapMarker(Marker mapMarker) {
		this.mapMarker = mapMarker;
	}

    public void setRadialThreshold(double radialThreshold) {
        this.radialThreshold = radialThreshold;
    }

    public double getRadialThreshold() {
        return radialThreshold;
    }
}
