package headrick.brandon.utilities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class DBTest extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
         
        DatabaseHandler db = new DatabaseHandler(this);
         
        /**
         * CRUD Operations
         * */
        // Inserting quests
        Log.d("Insert: ", "Inserting .."); 
        
        //db.clearTable();
        
        /*
        LatLng point = new LatLng(1.11, 2.22);
        
        db.addQuestNode(new QuestNode("title1", point, "script1", "answer1"));        
        db.addQuestNode(new QuestNode("title2", point, "script2", "answer2"));
        db.addQuestNode(new QuestNode("title3", point, "script3", "answer3"));
        db.addQuestNode(new QuestNode("title4", point, "script4", "answer4"));
        db.addQuestNode(new QuestNode("title5", point, "script5", "answer5"));
         
        // Reading all quests
        Log.d("Reading: ", "Reading all quests.."); 
        List<QuestNode> questNodes = db.getAllQuestNodes();       
         
        for (QuestNode qn : questNodes) {
            String log = "Id: "+qn.getId()+" ,title: " + qn.getTitle() + " ,script: " + qn.getScript()
            		+ " ,latitude: " + qn.getLatitude() + " ,longitude: " + qn.getLongitude();
                // Writing quests to log
        Log.d("db output: ", log);
        }
        */
    }
}