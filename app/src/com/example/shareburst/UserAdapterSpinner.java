package com.example.shareburst;

import java.util.ArrayList;

import com.example.rest.User;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * An Adapter that takes User objects and maps them to a ListView
 */
public class UserAdapterSpinner extends ArrayAdapter<User> {
	private ArrayList<User> entries;
    private Context context;
 
    public UserAdapterSpinner(Context context, int textViewResourceId, ArrayList<User> entries) {
        super(context, textViewResourceId, entries);
        this.entries = entries;
        this.context = context;
    }
 
    /**
     * An item in the ListView
     */
    public static class ViewHolder{
        public TextView item1;
    }
 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    	TextView label = new TextView(context);
    	label.setPadding(20, 20, 20, 20);
    	label.setTextColor(Color.BLACK);
    	final User user = entries.get(position);
        label.setText(user.getFirstName() + " " + user.getLastName());
        return label;
    }
    
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
    	TextView label = new TextView(context);
    	label.setPadding(30, 30, 30, 30);
    	label.setTextColor(Color.BLACK);
    	final User user = entries.get(position);
        label.setText(user.getFirstName() + " " + user.getLastName());
        return label;
    }

}
