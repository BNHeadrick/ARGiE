package headrick.brandon.gamedata;

import java.util.LinkedList;

import com.google.android.gms.maps.model.LatLng;

import headrick.brandon.models.QuestNode;

/**
 * Singleton class that holds the state of the game and provides access to 
 * the quest information
 */
public class GameState {
    public static char questAlphaLabel = Constants.INITIAL_ALPHA_LABEL_VAL;   //temporarily just for debugging; remove later.
    public static int questNumLabel = Constants.INITIAL_NUM_LABEL_VAL;        //temporarily just for debugging; remove later.

	private static GameState instance = null;
    //the active quest is whatever is the most relevant quest at any point in time; this might
    //be the current quest the user is trying to solve while playing or the current quest that is
    //being edited.
    private static QuestNode activeQuest;
	private static LinkedList<QuestNode> questNodes  = new LinkedList<QuestNode>();
	
	private GameState(){
		//restrict instantiation
	}
	
	/**
	 * returns the singleton object (instance) for the GameState class
	 * @return the GameState singleton object
	 */
	public static GameState getInstance(){
		if(instance == null){
			instance = new GameState();
		}
		return instance;
	}
	
	/**
	 * creates and appends a QuestNode object to the end of the QuestNode LinkedList
	 * @param title the title of the quest object
	 * @param point the location of the quest object to be used on a map
	 * @param script the text used to prompt the user when they arrive at a quest location
	 * @param answer the required text response when a user is locked from proceeding from one quest to another
	 */
	public void addQuest(String title, LatLng point, String script, String answer, double radialThreshold){
		questNodes.add(new QuestNode(title, point, script, answer, radialThreshold));
	}
	
	public void addQuest(QuestNode newQuest) {
		// TODO Auto-generated method stub
		questNodes.add(newQuest);
	}
	
	public QuestNode getRoot(){
		return questNodes.getFirst();
	}
	
	public QuestNode getTail(){
		return questNodes.getLast();
	}
	
	public LinkedList<QuestNode> getQuestNodes(){
		return questNodes;
	}
	
	public boolean isEmpty(){
		return questNodes.size() == 0;
	}
	
	public void removeAllQuests(){
		questNodes.clear();
	}

    public void setActiveQuest(QuestNode questNode){
        activeQuest = questNode;
    }

    public QuestNode getActiveQuest(){
        return activeQuest;
    }
	
}
