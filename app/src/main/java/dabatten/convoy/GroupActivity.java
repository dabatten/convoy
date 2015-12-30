package dabatten.convoy;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Objects;

public class GroupActivity extends Activity {

    private String groupId = null;
    private Group group;
    private ArrayList<ParseUser> members;
    private ArrayList<String> memberNames = new ArrayList<String>();
    private ArrayAdapter<String> arrayAdapter;
    private TextView groupKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        Intent i = getIntent();
        groupId = i.getStringExtra("groupId");
        Log.w("group id in extras: ", groupId);
        //get group
        ParseQuery query = ParseQuery.getQuery("Group");
        try{
            group = new Group(query.get(groupId));
            setTitle(group.getName());

            //set group key text
            groupKey = (TextView) findViewById(R.id.group_key2);
            groupKey.setText(group.getKey());


            //get members
            members = group.getMembers();
            for(int j = 0; j< members.size(); j++){
                memberNames.add(members.get(j).getUsername()); //todo change to first and last names
            }
            ListView membersList = (ListView) findViewById(R.id.group_members_list);
            arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.member_list_item, memberNames);
            membersList.setAdapter(arrayAdapter);

            membersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //todo start new location activity
                }
            });


        } catch (ParseException e){
            Log.e("parse_error", e.getMessage());
        }



    }

}
