package headrick.brandon.utilities;

import headrick.brandon.model.QuestNode;

import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.maps.model.LatLng;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHandler extends SQLiteOpenHelper {
 
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "questsManager";
 
    // QuestNodes table name
    private static final String TABLE_QUESTS = "quests";
    // Games table name
    private static final String TABLE_GAMES = "games";
 
    // QuestNodes Table Columns names
    private static final String QUEST_KEY_ID = "id";
    private static final String QUEST_KEY_TITLE = "title";
    private static final String QUEST_KEY_SCRIPT = "script";
    private static final String QUEST_KEY_ANSWER = "answer";
    private static final String QUEST_KEY_LAT = "latitude";
    private static final String QUEST_KEY_LONG = "longitude";
    
    //Games Table Column names
    private static final String GAME_KEY_ID = "id";
    private static final String GAME_KEY_TITLE = "title";
    
 
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_QUESTS + "("
                + QUEST_KEY_ID + " INTEGER PRIMARY KEY," + QUEST_KEY_TITLE + " TEXT,"
                + QUEST_KEY_SCRIPT + " TEXT," + QUEST_KEY_ANSWER + " TEXT,"
                + QUEST_KEY_LAT + " REAL," + QUEST_KEY_LONG + " REAL"
                + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTS);
 
        // Create tables again
        onCreate(db);
    }
    
 // Adding new quest
    public void addQuestNode(QuestNode quest) {
        SQLiteDatabase db = this.getWritableDatabase();
     
        ContentValues values = new ContentValues();
        values.put(QUEST_KEY_TITLE, quest.getTitle());
        values.put(QUEST_KEY_SCRIPT, quest.getScript()); 
        values.put(QUEST_KEY_ANSWER, quest.getAnswer()); 
        values.put(QUEST_KEY_LAT, quest.getLatitude());
        values.put(QUEST_KEY_LONG, quest.getLongitude());
     
        // Inserting Row
        db.insert(TABLE_QUESTS, null, values);
        db.close(); // Closing database connection
    }
     
    // Getting single quest
    public QuestNode getQuestNode(int id) {
    	
        SQLiteDatabase db = this.getReadableDatabase();
     
        Cursor cursor = db.query(TABLE_QUESTS, new String[] { QUEST_KEY_ID,
        		QUEST_KEY_TITLE, QUEST_KEY_SCRIPT, QUEST_KEY_ANSWER, QUEST_KEY_LAT, QUEST_KEY_LONG }, QUEST_KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
     
        //creates new LatLng object from the database entries for the QuestNode contructor
        LatLng point = new LatLng(Double.parseDouble(cursor.getString(4)), 
        		Double.parseDouble(cursor.getString(5)));
        
        QuestNode quest = new QuestNode(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), point, cursor.getString(3),
                cursor.getString(4));
        // return quest
        return quest;
    }
     
    // Getting All QuestNodes
    public List<QuestNode> getAllQuestNodes() {
        List<QuestNode> questList = new ArrayList<QuestNode>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_QUESTS;
     
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
     
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                QuestNode quest = new QuestNode();
                quest.setId(Integer.parseInt(cursor.getString(0)));
                quest.setTitle(cursor.getString(1));
                quest.setScript(cursor.getString(2));
                quest.setAnswer(cursor.getString(3));
                
                LatLng point = new LatLng(Double.parseDouble(cursor.getString(4)), 
                		Double.parseDouble(cursor.getString(5)));
                quest.setPoint(point);
                // Adding quest to list
                questList.add(quest);
            } while (cursor.moveToNext());
        }
     
        // return quest list
        return questList;
    }
     
    // Getting quests Count
    public int getQuestNodesCount() {
        String countQuery = "SELECT  * FROM " + TABLE_QUESTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
 
        // return count
        return cursor.getCount();
    }
    // Updating single quest
    public int updateQuestNode(QuestNode quest) {
        SQLiteDatabase db = this.getWritableDatabase();
     
        ContentValues values = new ContentValues();
        values.put(QUEST_KEY_TITLE, quest.getTitle());
        values.put(QUEST_KEY_SCRIPT, quest.getScript());
        values.put(QUEST_KEY_ANSWER, quest.getAnswer()); 
        values.put(QUEST_KEY_LAT, quest.getLatitude());
        values.put(QUEST_KEY_LONG, quest.getLongitude());
     
        // updating row
        return db.update(TABLE_QUESTS, values, QUEST_KEY_ID + " = ?",
                new String[] { String.valueOf(quest.getId()) });
    }
     
    // Deleting single quest
    public void deleteQuestNode(QuestNode quest) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_QUESTS, QUEST_KEY_ID + " = ?",
                new String[] { String.valueOf(quest.getId()) });
        db.close();
    }
    
    //delete all rows in the table
    public void clearTable(){
    	SQLiteDatabase db = this.getWritableDatabase();
    	String deleteSQL = "DELETE FROM " + TABLE_QUESTS;

    	db.execSQL(deleteSQL);
    }
}