package dabatten.convoy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private ArrayList<Group> groups = new ArrayList<Group>();
    private ListGroupsAdapter groupsAdapter;
    private ListView groupsListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //populate list of groups the current user is a member of
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Group")
                .whereEqualTo("members", ParseUser.getCurrentUser());
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {
                    for (int i = 0; i < list.size(); i++) {
                        groups.add(new Group(list.get(i)));
                    }
                    //build list view
                    groupsListView = (ListView) findViewById(R.id.groupsListView);
                    groupsAdapter = new ListGroupsAdapter(getApplicationContext(), R.layout.group_list_item, groups);
                    groupsListView.setAdapter(groupsAdapter);

                    groupsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            //get item id and start new group activity for the group with that id
                            Group selected = (Group) parent.getItemAtPosition(position);
                            Intent intent = new Intent(getApplicationContext(), GroupActivity.class);
                            intent.putExtra("groupId", selected.getId());
                            startActivity(intent);
                        }
                    });
                } else {
                    Log.e("parse error", e.getMessage());
                }
            }
        });

    }

    //set up options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    //handle option menu items selected
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.new_group_option:
                Intent new_group_intent = new Intent(getApplicationContext(), NewGroupActivity.class);
                startActivity(new_group_intent);
                return true;
            case R.id.join_group_option:
                Intent join_group_intent = new Intent(getApplicationContext(), JoinGroupActivity.class);
                startActivity(join_group_intent);
                return true;
            case R.id.logout_option:
                ParseUser.logOut();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
