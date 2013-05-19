package headrick.brandon.utilities;

import java.util.List;

import headrick.brandon.gamedata.GameState;
import headrick.brandon.model.QuestNode;

import android.content.Context;
import android.util.Log;

public class DBReadWrite{
	DatabaseHandler db;
	
	public DBReadWrite(Context context){
		db = new DatabaseHandler(context);
	}
	
	public void writeQuestData(){
		
		db.clearTable();
		
		for(QuestNode node : GameState.getInstance().getQuestNodes()){
			db.addQuestNode(new QuestNode(node.getTitle(), node.getPoint(), node.getScript(), node.getAnswer()));
		}
        // Inserting quests
        Log.d("Insert: ", "Inserting .."); 
         
        
        // Reading all quests
        Log.d("Reading: ", "Reading all quests.."); 
        List<QuestNode> questNodes = db.getAllQuestNodes();       
         
        for (QuestNode qn : questNodes) {
            String log = "Id: "+qn.getId()+" ,title: " + qn.getTitle() + " ,script: " + qn.getScript()
            		+ " ,latitude: " + qn.getLatitude() + " ,longitude: " + qn.getLongitude();
            // Writing quests to log
            Log.d("db output: ", "quests are: " + log);
        }
	}
	
}
