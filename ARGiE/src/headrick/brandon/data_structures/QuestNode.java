package headrick.brandon.data_structures;

/**
 * Basic data structure that holds individual quest data.
 * @author Brandon Headrick
 *
 */
public class QuestNode {
	private String title, script, answer;
	private int longitude, latitude;

	//scripts can either just be statements or questions that require answers.
	public QuestNode(String title, int latitude, int longitude, String script, String answer){
		this.title = title;
		this.latitude = latitude;
		this.longitude = longitude;
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

	public int getLongitude() {
		return longitude;
	}

	public void setLongitude(int longitude) {
		this.longitude = longitude;
	}

	public int getLatitude() {
		return latitude;
	}

	public void setLatitude(int latitude) {
		this.latitude = latitude;
	}
	
}
