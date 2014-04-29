package com.example.shareburst;

import java.util.ArrayList;

import com.example.rest.ModifyUser;
import com.example.rest.User;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import android.os.Build;

public class NewGroupActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_group);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_group, menu);
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
	public static class PlaceholderFragment extends Fragment implements ModifyUser {

		Spinner userSpinner;
		ListView addedUsersListView;
		
		ArrayList<User> users;
		ArrayList<User> selectedUsers;
		UserAdapter userAdapter;
		UserAdapterSpinner userAdapterSpinner;
		
		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_new_group,
					container, false);
			
//			userSpinner = (Spinner) rootView.findViewById(R.id.userSpinner);
//			userSpinner.setEnabled(false);
//			userSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
//
//				@Override
//				public void onItemSelected(AdapterView<?> parent, View view,
//						int position, long id) {
//					// TODO Auto-generated method stub
//					selectedUsers.add(users.get(position));
//					userAdapter.notifyDataSetChanged();
//				}
//
//				@Override
//				public void onNothingSelected(AdapterView<?> parent) {
//					// TODO Auto-generated method stub
//					
//				}
//				
//			});
			
			addedUsersListView = (ListView) rootView.findViewById(R.id.addedUsersList);
			addedUsersListView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					if(addedUsersListView.getItemAtPosition(position) == null) {
						Intent intent = new Intent(getActivity(), UsersActivity.class);
						startActivityForResult(intent, 1);
					}
				}
				
			});
			
			new ListUser(getActivity(), this).execute();
			
			return rootView;
		}
		
		@Override
		public void onActivityResult(int requestCode, int resultCode,
				Intent data) {
			// TODO Auto-generated method stub
			super.onActivityResult(requestCode, resultCode, data);
			String userName = (String) data.getCharSequenceExtra("userName");
			String firstName = (String) data.getCharSequenceExtra("firstName");
			String lastName = (String) data.getCharSequenceExtra("lastName");
			User newUser = new User();
			newUser.setUserName(userName);
			newUser.setFirstName(firstName);
			newUser.setLastName(lastName);
			if(selectedUsers.contains(newUser)) {
				Toast.makeText(getActivity(), "User already added.", Toast.LENGTH_SHORT).show();
				return;
			}
			selectedUsers.add(selectedUsers.size()-1, newUser);
			userAdapter.notifyDataSetChanged();
		}

		@SuppressWarnings("unchecked")
		@Override
		public void modifyUserSuccess(ModifyUserMethods method, Object user) {
			
			// users spinner
//			users = (ArrayList<User>) user;
//			userAdapterSpinner = new UserAdapterSpinner(getActivity(), R.id.userSpinner, users);
//			userSpinner.setAdapter(userAdapterSpinner);
//			userSpinner.setEnabled(true);
			
			// selected users listview
			selectedUsers = new ArrayList<User>();
			selectedUsers.add(null);
			userAdapter = new UserAdapter(getActivity(), R.id.addedUsersList, selectedUsers);
			addedUsersListView.setAdapter(userAdapter);
			
		}

		@Override
		public void modifyUserFailure(ModifyUserMethods method, Object user) {
			// TODO Auto-generated method stub
			Toast.makeText(getActivity(), "Not Good.", Toast.LENGTH_SHORT).show();
		}
	}

}
