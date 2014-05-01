package com.example.shareburst;

import java.util.ArrayList;

import com.example.data.UserName;
import com.example.rest.Group;
import com.example.rest.ModifyGroup;
import com.example.rest.ModifyUser.ListUser;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;
import android.os.Build;

public class GroupsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_groups);
		
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment implements ModifyGroup {

		GroupAdapter adapter;
		ListView groupsList;
		
		public PlaceholderFragment() {
		}
		
		@Override
		public void onCreate(Bundle bundle) {
			super.onCreate(bundle);
			setHasOptionsMenu(true);
		}
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_groups,
					container, false);
			
			groupsList = (ListView) rootView.findViewById(R.id.groupsList);
			groupsList.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(getActivity(), GroupActivity.class);
					intent.putExtra("pos", position);
					startActivity(intent);
				}
				
			});
			if(UserName.getGroups() == null) {
				new ListGroup(getActivity(), this, UserName.getUserName(getActivity())).execute();
			} else {
				refreshView();
			}
			
			return rootView;
		}
		
		public void refreshView() {
			adapter = new GroupAdapter(getActivity(), R.id.groupsList, UserName.getGroups());
			groupsList.setAdapter(adapter);
	    	groupsList.setVisibility(View.VISIBLE);
		}
		
		@Override
		public void onResume() {
			super.onResume();
			if(groupsList != null && UserName.getGroups() != null) {
				refreshView();
			}
		}
		
		@Override
		public void onCreateOptionsMenu (Menu menu, MenuInflater inflater) {
			inflater.inflate(R.menu.groups, menu);
			super.onCreateOptionsMenu(menu, inflater);
		}
		
		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
		    // Handle item selection
		    switch (item.getItemId()) {
		    case R.id.action_new:
	        	Intent intent = new Intent(getActivity(), NewGroupActivity.class);
	        	startActivity(intent);
	        	return true;
		    case R.id.action_refresh:
		    	new ListGroup(getActivity(), this, UserName.getUserName(getActivity())).execute();
		    	return true;
	        case R.id.action_edit_account:
	        	Toast.makeText(getActivity(), "edit account", Toast.LENGTH_LONG).show();
	            return true;
	        case R.id.action_logout:
	        	UserName.clearUserName(getActivity());
	    		Intent i = new Intent(getActivity(), LoginActivity.class);
	    		startActivity(i);
	    		getActivity().finish();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
		    }
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public void modifyGroupSuccess(ModifyGroupMethods method, Object group) {
			// TODO Auto-generated method stub
			UserName.setGroups((ArrayList<Group>) group);
			refreshView();
		}

		@Override
		public void modifyGroupFailure(ModifyGroupMethods method, Object group) {
			// TODO Auto-generated method stub
			Toast.makeText(getActivity(), "NOT WORK", Toast.LENGTH_LONG).show();
		}
	}

}
