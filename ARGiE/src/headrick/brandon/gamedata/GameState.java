package headrick.brandon.gamedata;

import com.google.android.gms.maps.model.LatLng;

import headrick.brandon.model.QuestNode;

/*
 * Singleton class that holds the state of the game.  
 * Each game is a set of quest nodes which form a linked list.  
 * For now, since quests do not branch (at least in simple quests for the first release), this is adequate.
 */
public class GameState {
	private static GameState instance = null;
	private QuestNode root, tail;
	
	private GameState(){
		//restrict instantiation
	}
	
	public static GameState getInstance(){
		if(instance == null){
			instance = new GameState();
		}
		return instance;
	}
	
	//it simply adds a quest to the end of the quest linked list.
	public void addQuest(String title, LatLng point, String script, String answer){
		if(root == null){
			root = new QuestNode(title, point, script, answer);
			
			tail = root;
		}
		else{
			tail.setNext(new QuestNode(title, point, script, answer));
			tail = tail.getNext();
		}
	}
	
	public QuestNode getRoot(){
		return root;
	}
	
	public QuestNode getTail(){
		return tail;
	}
}
