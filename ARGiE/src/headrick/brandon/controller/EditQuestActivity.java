package headrick.brandon.controller;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
/**
 * Programmatically created layout that shows the editable options available to the user for the previously
 * selected quest
 * @author Brandon Headrick
 *
 */
public class EditQuestActivity extends Activity {
    static int id = 0;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Creating a new RelativeLayout
        RelativeLayout relativeLayout = new RelativeLayout(this);

        // Defining the RelativeLayout layout parameters.
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);

        // Defining the layout parameters
        RelativeLayout.LayoutParams elementParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        //lp.addRule(RelativeLayout.CENTER_IN_PARENT);
        //lp.addRule(RelativeLayout.);

        TextView tv = new TextView(this);
        tv.setText("Testing Long String");
        tv.setId(findNextValidId());
        
        EditText et = new EditText(this);
        et.setHint("Enter Something");
        et.setId(findNextValidId());



        
        // Setting the parameters for the input fields.
        tv.setLayoutParams(elementParams);
        et.setLayoutParams(elementParams);

        // Adding the TextView to the RelativeLayout as a child
        relativeLayout.addView(tv);
        relativeLayout.addView(et);

        // Setting the RelativeLayout as our content view
        setContentView(relativeLayout, layoutParams);
    }

    // Returns a valid id that isn't in use
    public int findNextValidId(){
        View v = findViewById(id);
        while (v != null){
            v = findViewById(++id);
        }
        return id++;
    }

}
