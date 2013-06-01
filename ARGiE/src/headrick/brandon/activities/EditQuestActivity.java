package headrick.brandon.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import headrick.brandon.R;
import headrick.brandon.gamedata.GameState;
import headrick.brandon.models.QuestNode;

/**
 * Programmatically created layout that shows the editable options available to the user for the previously
 * selected quest
 * @author Brandon Headrick
 * CURRENTLY UNUSED; USING PREBUILD LAYOUT
 *
 */
public class EditQuestActivity extends Activity implements View.OnClickListener,
        CompoundButton.OnCheckedChangeListener {

    private TextView answerTextView;
    private EditText radThresh, questTitle, questScript, questAnswer;
    private Button saveChanges, cancelChanges;
    private CheckBox answerRequired;

    private GameState gameState;
    private QuestNode activeQuest;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_quest_screen);
        initializeVars();



    }

    private void initializeVars() {
        // TODO Auto-generated method stub
        saveChanges = (Button) findViewById(R.id.bQuestSaveChanges);
        cancelChanges = (Button) findViewById(R.id.bQuestCancelChanges);

        answerRequired = (CheckBox) findViewById(R.id.cbQuestAnswer);

        answerTextView = (TextView) findViewById(R.id.tvQuestAnswer);

        questTitle = (EditText) findViewById(R.id.etQuestTitle);
        questScript = (EditText) findViewById(R.id.etQuestScript);
        questAnswer = (EditText) findViewById(R.id.etQuestAnswer);
        radThresh = (EditText) findViewById(R.id.etQuestRadius);

        saveChanges.setOnClickListener(this);
        cancelChanges.setOnClickListener(this);
        answerRequired.setOnCheckedChangeListener(this);

        gameState = GameState.getInstance();
        activeQuest = gameState.getActiveQuest();

        setupInterface();
    }

    private void setupInterface(){
        questTitle.setText(activeQuest.getTitle());
        questScript.setText(activeQuest.getScript());
        questAnswer.setText(activeQuest.getAnswer());
        radThresh.setText(activeQuest.getRadialThreshold()+"");

        if(activeQuest.isForceUserResponse()){
            answerTextView.setVisibility(View.VISIBLE);
            questAnswer.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bQuestSaveChanges:
                /*
                if(radThresh.getText().length()>0){

                    double radThreshValue = Double.parseDouble(radThresh.getText().toString());

                    for(QuestNode aQuest : gameState.getQuestNodes()){
                        aQuest.setRadialThreshold(radThreshValue);
                    }

                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(),
                            "Please Enter Valid Values For All Fields", Toast.LENGTH_LONG).show();
                }
                */

                //go through the active quest's properties and set them to the current values if
                //the new value is neither empty nor the same as the previous value
                if(!questTitle.getText().toString().matches("") &&
                        !questTitle.getText().toString().matches(activeQuest.getTitle())){
                    activeQuest.setTitle(questTitle.getText().toString());
                }
                
                if(!questScript.getText().toString().matches("") &&
                        questScript.getText().toString().matches(activeQuest.getScript())){
                    activeQuest.setScript(questScript.getText().toString());
                }
                if(!questAnswer.getText().toString().matches("") &&
                        questAnswer.getText().toString().matches(activeQuest.getAnswer())){
                    activeQuest.setAnswer(questAnswer.getText().toString());
                }
                if(!radThresh.getText().toString().matches("") &&
                        Double.parseDouble(radThresh.getText().toString())!=activeQuest.getRadialThreshold()){
                    activeQuest.setRadialThreshold(Double.parseDouble(radThresh.getText().toString()));
                }
                
                //activeQuest.setRadialThreshold(Double.parseDouble(radThresh.getText().toString()));
                finish();
                break;
            case R.id.bQuestCancelChanges:

                finish();
                break;
        }

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked){
            answerTextView.setVisibility(View.VISIBLE);
            questAnswer.setVisibility(View.VISIBLE);
            activeQuest.setForceUserResponse(true);
        }
        else{
            answerTextView.setVisibility(View.GONE);
            questAnswer.setVisibility(View.GONE);
            activeQuest.setForceUserResponse(false);
        }
    }
}
