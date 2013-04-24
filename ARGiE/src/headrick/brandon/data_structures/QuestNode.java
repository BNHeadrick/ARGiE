package headrick.brandon.data_structures;

import com.google.android.gms.maps.model.LatLng;

/**
 * Basic data structure that holds individual quest data.
 * @author Brandon Headrick
 *
 */
public class QuestNode {
	private QuestNode next, prev;
	private String title, script, answer;
	private double longitude, latitude;
	LatLng point;

	//scripts can either just be statements or questions that require answers.
	/*
	public QuestNode(String title, double latitude, double longitude, String script, String answer){
		this.title = title;
		this.latitude = latitude;
		this.longitude = longitude;
		this.script = script;
		this.answer = answer;
	}
	*/
	
	public QuestNode(String title, LatLng point, String script, String answer){
		this.title = title;
		this.point = point;
		this.script = script;
		this.answer = answer;
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

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(int longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(int latitude) {
		this.latitude = latitude;
	}

	public QuestNode getNext() {
		return next;
	}

	public void setNext(QuestNode next) {
		this.next = next;
	}

	public QuestNode getPrev() {
		return prev;
	}

	public void setPrev(QuestNode prev) {
		this.prev = prev;
	}
	
	public LatLng getPoint() {
		return point;
	}

	public void setPoint(LatLng point) {
		this.point = point;
	}
	
}
