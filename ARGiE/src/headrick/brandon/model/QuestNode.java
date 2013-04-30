package headrick.brandon.model;

import com.google.android.gms.maps.model.LatLng;

/**
 * Basic data structure that holds individual quest data.
 * @author Brandon Headrick
 *
 */
public class QuestNode {
	private QuestNode next, prev;
	private String title, script, answer;
	private LatLng point;
	private int id;	//only used for database
	
	public QuestNode(){
		
	}
	
	public QuestNode(String title, LatLng point, String script, String answer){
		this.title = title;
		this.point = point;
		this.script = script;
		this.answer = answer;
	}
	
	//constructor to be used when pulling from a database
	public QuestNode(int id, String title, LatLng point, String script, String answer){
		this.id = id;
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
	
	
}
