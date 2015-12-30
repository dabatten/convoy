package dabatten.convoy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.UUID;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class NewGroupActivity extends Activity {

    private Button saveButton;
    private EditText groupNameField;
    private EditText groupDescField;
    private String groupKey;
    private TextView keyText;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_group);

        //get UI elements
        groupNameField = (EditText) findViewById(R.id.group_name_field);
        groupDescField = (EditText) findViewById(R.id.group_desc_field);
        saveButton = (Button) findViewById(R.id.new_group_button);

        //TODO generate group key? or perhaps change how users join groups
        groupKey = UUID.randomUUID().toString();
        keyText = (TextView) findViewById(R.id.group_key);
        keyText.setText(groupKey);

        //set button listener
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String groupName = groupNameField.getText().toString();
                String groupDesc = groupDescField.getText().toString();

                ParseObject group = new ParseObject("Group");
                group.put("name", groupName);
                group.put("key", groupKey);
                group.put("desc", groupDesc);

                //add current user to group
                ArrayList<ParseUser> members = new ArrayList<ParseUser>();
                members.add(ParseUser.getCurrentUser());
                group.put("members", members);

                group.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            //successful group creation
                            //TODO next Activity
                            Intent groupActivity = new Intent(getApplicationContext(), GroupActivity.class);
                            groupActivity.putExtra("groupId", getSavedGroupId(groupKey));
                            startActivity(groupActivity);
                        } else {
                            Log.e("parse_error", e.getMessage());
                        }
                    }
                });

            }
        });



    }

    private String getSavedGroupId(String groupKey){
        id = null;
        ParseQuery query = ParseQuery.getQuery("Group").whereMatches("key", groupKey);
        try {
            ParseObject group = query.getFirst();
            id = group.getObjectId();
            Log.w("groupId", id);
        } catch (Exception e){
            Log.e("error", e.getMessage());
        }
        return id;
    }
}
