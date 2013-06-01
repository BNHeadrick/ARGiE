package headrick.brandon.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import headrick.brandon.R;
import headrick.brandon.gamedata.Constants;
import headrick.brandon.gamedata.GameSettingsState;
import headrick.brandon.gamedata.GameState;
import headrick.brandon.models.QuestNode;

/**
 * Created by Brandon Headrick on 5/26/13.
 */
public class GameOptionsActivity extends Activity implements View.OnClickListener {
    private EditText radThresh;
    private Button saveChanges, cancelChanges;

    private GameSettingsState settingsState;
    private GameState gameState;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_options_screen);

        initializeVars();

    }

    private void initializeVars() {
        // TODO Auto-generated method stub
        radThresh = (EditText) findViewById(R.id.etGameRadius);
        radThresh.setText(Constants.DEFAULT_RAD_METERS + "");
        saveChanges = (Button) findViewById(R.id.bGameSaveChanges);
        cancelChanges = (Button) findViewById(R.id.bGameCancelChanges);

        saveChanges.setOnClickListener(this);
        cancelChanges.setOnClickListener(this);

        settingsState = GameSettingsState.getInstance();
        gameState = GameState.getInstance();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bGameSaveChanges:
                if(radThresh.getText().length()>0){

                    double radThreshValue = Double.parseDouble(radThresh.getText().toString());
                    settingsState.setGlobalRadiusThreshold(radThreshValue);

                    for(QuestNode aQuest : gameState.getQuestNodes()){
                        aQuest.setRadialThreshold(radThreshValue);
                    }

                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(),
                            "Please Enter Valid Values For All Fields", Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.bGameCancelChanges:

                finish();
                break;
        }
    }
}
