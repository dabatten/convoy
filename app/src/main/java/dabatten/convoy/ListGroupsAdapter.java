package dabatten.convoy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by davisbatten on 12/29/15.
 */
public class ListGroupsAdapter extends ArrayAdapter<Group> {

    private LayoutInflater layoutInflater;


    public ListGroupsAdapter(Context context, int resource, List<Group> groups) {
        super(context, resource, groups);
    }


    @Override
    public View getView(int position, View view, ViewGroup parent) {
        //todo

        if(view == null){
            layoutInflater = LayoutInflater.from(getContext());
            view = layoutInflater.inflate(R.layout.group_list_item, null);
        }

        Group g = getItem(position);

        //fill text elements
        if (g != null){
            TextView name = (TextView) view.findViewById(R.id.list_item_name);
            TextView desc = (TextView) view.findViewById(R.id.list_item_desc);

            name.setText(g.getName());
            desc.setText(g.getDesc());
        }
        return view;
    }

}
