package dabatten.convoy;

import android.util.Log;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;

/**
 * Created by davisbatten on 12/28/15.
 */
public class Group {
    private String id;
    private String key;
    private String name;
    private String desc;
    private ArrayList<ParseUser> members;

    public Group(ParseObject parseGroup){
        id = parseGroup.getObjectId();
        key = parseGroup.getString("key");
        name = parseGroup.getString("name");
        desc = parseGroup.getString("desc");

        //get members
        members = new ArrayList<ParseUser>();
        ArrayList<ParseObject> parseArray = (ArrayList<ParseObject>) parseGroup.get("members");
        for(int i = 0; i < parseArray.size(); i++){
            try {
                ParseQuery query = ParseUser.getQuery();
                members.add((ParseUser) query.get(parseArray.get(i).getObjectId()));
            } catch (ParseException e){
                Log.e("parse_error", e.getMessage());
            }
        }
        //Log.w("first user", members.get(0).getUsername());
    }

    //public get methods
    public String getId(){return id;}
    public String getName(){return name;}
    public String getDesc(){return desc;}
    public String getKey(){return key;}
    public ArrayList<ParseUser> getMembers(){return members;}



}
