package com.example.shareburst;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.data.UserName;
import com.example.rest.Assignments;
import com.example.rest.Group;

/**
 * An Adapter that takes Device objects and maps them to a ListView
 */
public class GroupAdapter extends ArrayAdapter<Group> {
	
	private ArrayList<Group> groups;
    private Activity activity;
 
    public GroupAdapter(Activity activity, int textViewResourceId, ArrayList<Group> groups) {
        super(activity, textViewResourceId, groups);
        this.groups = groups;
        this.activity = activity;
    }
 
    /**
     * An item in the ListView
     */
    public static class ViewHolder {
        public TextView item1;
		public TextView item2;
    }
 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder holder;
        if (v == null) {
            LayoutInflater vi =
                (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(android.R.layout.simple_list_item_2, null);
            holder = new ViewHolder();
            holder.item1 = (TextView) v.findViewById(android.R.id.text1);
            holder.item2 = (TextView) v.findViewById(android.R.id.text2);
            v.setTag(holder);
        }
        else
            holder=(ViewHolder)v.getTag();
 
        final Group group = groups.get(position); // get Device
        if (group != null) {
        	// set item values
            holder.item1.setText(group.getGroupName());
            String users = "";
            int i = 0;
            for(Assignments a : group.getAssignments()) {
            	if(a == null || a.getUserName() == null || a.getUserName().equals(UserName.getUserName(activity))) {
            		continue;
            	}
            	if(i > 3) {
            		users += ", ...";
            		break;
            	}
            	if(i > 0) {
            		users += ", ";
            	}
            	users += a.getUserName();
            	i++;
            }
            holder.item2.setText(users);
        }
        return v;
    }

}
