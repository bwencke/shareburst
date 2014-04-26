package com.example.shareburst;

import java.util.ArrayList;

import com.example.data.UserName;
import com.example.rest.Group;
import com.example.rest.ModifyGroup;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.groups, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
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
					Toast.makeText(getActivity(), "Fuck you, " + UserName.getUserName(getActivity()) + ".", Toast.LENGTH_SHORT).show();
				}
				
			});
			new ListGroup(getActivity(), this, UserName.getUserName(getActivity())).execute();
			
			return rootView;
		}

		@Override
		public void modifyGroupSuccess(ModifyGroupMethods method, Object group) {
			// TODO Auto-generated method stub
			adapter = new GroupAdapter(getActivity(), R.id.groupsList, (ArrayList<Group>) group);
			groupsList.setAdapter(adapter);
	    	groupsList.setVisibility(View.VISIBLE);
		}

		@Override
		public void modifyGroupFailure(ModifyGroupMethods method, Object group) {
			// TODO Auto-generated method stub
			Toast.makeText(getActivity(), "NOT WORK", Toast.LENGTH_LONG).show();
		}
	}

}
