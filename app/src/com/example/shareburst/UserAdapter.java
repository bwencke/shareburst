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

import com.example.rest.Assignments;
import com.example.rest.User;

/**
 * An Adapter that takes Device objects and maps them to a ListView
 */
public class UserAdapter extends ArrayAdapter<User> {
	
	private ArrayList<User> users;
    private Activity activity;
 
    public UserAdapter(Activity activity, int textViewResourceId, ArrayList<User> users) {
        super(activity, textViewResourceId, users);
        this.users = users;
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
        LayoutInflater vi =
                (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = vi.inflate(android.R.layout.simple_list_item_2, null);
        holder = new ViewHolder();
        holder.item1 = (TextView) v.findViewById(android.R.id.text1);
        holder.item2 = (TextView) v.findViewById(android.R.id.text2);
        v.setTag(holder);
 
        final User user = users.get(position);
        if (user != null && user.getUserName() != null) {
        	// set item values
            holder.item1.setText(
            		((user.getFirstName() == null) ? "" : user.getFirstName()) + 
            		((user.getLastName() == null) ? "" : user.getLastName()));
            holder.item2.setText(user.getUserName());
        } else {
        	v = vi.inflate(R.layout.adduser, null);
        }
        return v;
    }

}
