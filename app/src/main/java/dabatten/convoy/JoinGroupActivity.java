package dabatten.convoy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class JoinGroupActivity extends Activity {

    private Button joinBtn;
    private EditText key_field;
    private String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_group);

        joinBtn = (Button) findViewById(R.id.join_button);
        key_field = (EditText) findViewById(R.id.group_key_field);

        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                key = key_field.getText().toString();

                //for testing
                //key = "c289b32f-d905-4c99-a10b-2cf1b03454f9";

                ParseQuery query = ParseQuery.getQuery("Group").whereEqualTo("key", key);
                query.findInBackground(new FindCallback() {
                    @Override
                    public void done(List objects, ParseException e) {
                        if(e == null && objects.size() < 2) {
                            joinGroup(objects.get(0));
                        }
                        else {
                            Log.e("parse_error", e.getMessage());
                        }
                    }

                    @Override
                    public void done(Object o, Throwable throwable) {
                        if(throwable == null) {
                            joinGroup(((ArrayList<ParseObject>)o).get(0));
                        }
                        else {
                            Log.e("error", throwable.getMessage());
                        }
                    }

                });


            }
        });

    }

    private void joinGroup(Object object){

        ParseUser user = ParseUser.getCurrentUser();

        ParseObject group = (ParseObject) object;
        final String groupId = group.getObjectId();

        group.add("members", user);
        group.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                //start group activity for the joined group
                Intent intent = new Intent(getApplicationContext(), GroupActivity.class);
                intent.putExtra("groupId", groupId);
                startActivity(intent);
            }
        });
    }
}
