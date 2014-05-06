package com.shareburst.user;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.data.UserName;
import com.example.rest.User;
import com.shareburst.R;

/**
 * An Adapter that takes Device objects and maps them to a ListView
 */
@SuppressLint("DefaultLocale")
public class UserAdapter extends ArrayAdapter<User> {

	private ArrayList<User> users;
    private Activity activity;
    private DeleteUser fragment;
 
    public UserAdapter(Activity activity, int textViewResourceId, ArrayList<User> users, DeleteUser fragment) {
        super(activity, textViewResourceId, users);
        this.users = users;
        this.activity = activity;
        this.fragment = fragment;
    }
 
    /**
     * An item in the ListView
     */
    public static class ViewHolder {
        public TextView item1;
		//public TextView item2;
    }
 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder holder;
        final User user = users.get(position);
        LayoutInflater vi =
                (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(fragment != null && user != null && !user.getUserName().equals(UserName.getUserName(activity))) {
        	v = vi.inflate(R.layout.listitemwithdelete, null);
        	ImageButton imgButton = (ImageButton) v.findViewById(R.id.imageButton1);
        	imgButton.setTag(position);
        	v.findViewById(R.id.imageButton1).setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					fragment.deleteUser((Integer)v.getTag());
				}
        		
        	});
        } else {
        	v = vi.inflate(android.R.layout.simple_list_item_1, null);
        }
        holder = new ViewHolder();
        if(fragment != null && user != null && !user.getUserName().equals(UserName.getUserName(getContext()))) {
        	holder.item1 = (TextView) v.findViewById(R.id.textView1);
        } else {
        	holder.item1 = (TextView) v.findViewById(android.R.id.text1);
        }
        //holder.item2 = (TextView) v.findViewById(android.R.id.text2);
        v.setTag(holder);

        if (user != null && user.getUserName() != null) {
        	// set item values
            holder.item1.setText(user.getName());
            //holder.item2.setText(user.getUserName());
        } else {
        	v = vi.inflate(R.layout.adduser, null);
        }
        return v;
    }
    
    @Override
    public int getCount() {
        return users.size();
    }
    
    @Override
    public User getItem(int position) {
        return users.get(position);
    }
    
    @SuppressLint("DefaultLocale")
	@Override
	public Filter getFilter() {
		// TODO Auto-generated method stub
		Filter filter = new Filter() {

			@SuppressLint("DefaultLocale")
			@Override
			protected FilterResults performFiltering(CharSequence constraint) {
				// TODO Auto-generated method stub
				FilterResults results = new FilterResults();
                ArrayList<User> FilteredArrayNames = new ArrayList<User>();

                // perform your search here using the searchConstraint String.

                constraint = constraint.toString().toLowerCase();
                if(UserName.getUsers() == null) {
                	return null;
                }
                for(User u : UserName.getUsers()) {
                	if(u != null && u.getUserName() != null) {
                		if(u.getUserName().toLowerCase().contains(constraint)) {
                			FilteredArrayNames.add(u);
                		} else if(u.getFirstName() != null && u.getFirstName().toLowerCase().contains(constraint)) {
                			FilteredArrayNames.add(u);
                		} else if(u.getLastName() != null && u.getLastName().toLowerCase().contains(constraint)) {
                			FilteredArrayNames.add(u);
                		} else if(u.getFirstName() != null && u .getLastName() != null && (u.getFirstName().toLowerCase() + " " + u.getLastName().toLowerCase()).contains(constraint)) {
                			FilteredArrayNames.add(u);
                		}
                	}
                }

                results.count = FilteredArrayNames.size();
                results.values = FilteredArrayNames;
                Log.e("VALUES", results.values.toString());

                return results;
			}

			@SuppressWarnings("unchecked")
			@Override
			protected void publishResults(CharSequence constraint,
					FilterResults results) {
				// TODO Auto-generated method stub
				users = (ArrayList<User>) results.values;
                notifyDataSetChanged();
			}
			
		};
		return filter;
	}

}
