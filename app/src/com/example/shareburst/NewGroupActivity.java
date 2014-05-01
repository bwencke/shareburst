package com.example.shareburst;

import java.util.ArrayList;

import com.example.data.UserName;
import com.example.rest.Assignments;
import com.example.rest.Group;
import com.example.rest.ModifyGroup;
import com.example.rest.ModifyUser;
import com.example.rest.User;
import com.example.rest.ModifyUser.ListUser;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnGenericMotionListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
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

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment implements ModifyGroup, ModifyUser, DeleteUser {

		int groupID;
		Group g;
		
		EditText groupName;
		EditText numPackets;
		ListView addedUsersListView;
		
		ArrayList<User> users;
		ArrayList<User> selectedUsers;
		UserAdapter userAdapter;
		UserAdapterSpinner userAdapterSpinner;
		
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
			View rootView = inflater.inflate(R.layout.fragment_new_group,
					container, false);
			
			groupID = getActivity().getIntent().getIntExtra("groupID", 0);
			
			groupName = (EditText) rootView.findViewById(R.id.groupName);
			numPackets = (EditText) rootView.findViewById(R.id.numPackets);
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
			
			g = null;
			if(groupID > 0) {
				for(Group g : UserName.getGroups()) {
					if(g.getGroupID() == groupID) {
						this.g = g;
						break;
					}
				}
				if(g != null) {
					groupName.setText(g.getGroupName());
					groupName.setSelection(groupName.getText().length());
					numPackets.setText(g.getnPackets()+"");
				}
			}
			
			if(UserName.getUsers() == null) {
				new ListUser(getActivity(), this).execute();
			} else {
				setupView();
			}
			
			return rootView;
		}
		
		@Override
		public void onCreateOptionsMenu (Menu menu, MenuInflater inflater) {
			inflater.inflate(R.menu.new_group, menu);
			super.onCreateOptionsMenu(menu, inflater);
		}
		
		public void setupView() {
			User you = new User();
			you.setUserName(UserName.getUserName(getActivity()));
			you.setFirstName("Me");
			
			selectedUsers = new ArrayList<User>();
			selectedUsers.add(you);
			if(g != null) {
				for(Assignments a : g.getAssignments()) {
					for(User u : UserName.getUsers()) {
						if(!u.getUserName().equals(UserName.getUserName(getActivity())) && u.getUserName().equals(a.getUserName())) {
							selectedUsers.add(u);
						}
					}
				}
			}
			selectedUsers.add(null);
			userAdapter = new UserAdapter(getActivity(), R.id.addedUsersList, selectedUsers, this);
			addedUsersListView.setAdapter(userAdapter);
		}
		
		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
		    // Handle item selection
		    switch (item.getItemId()) {
		    case android.R.id.home:
		        getActivity().finish();
		        return true;
		    case R.id.action_accept:
		    	String name = groupName.getText().toString();
		    	int packets;
		    	try {
		    		packets = Integer.parseInt(numPackets.getText().toString());
		    		if(packets < 1) {
		    			throw new Exception("'" + packets + "' is not a natural number");
		    		}
		    	} catch(Exception e) {
		    		Toast.makeText(getActivity(), "Number of packets invalid. It must be an natural number.", Toast.LENGTH_LONG).show();
		    		return false;
		    	}
		    	if(name == null) {
		    		Toast.makeText(getActivity(), "Seriously? Enter a group name next time.", Toast.LENGTH_LONG).show();
		    		return false;
		    	}
		    	Group g = new Group();
		    	if(this.g != null) {
		    		g.setGroupID(this.g.getGroupID());
		    	}
		    	g.setGroupName(name);
		    	g.setnPackets(packets);
		    	ArrayList<Assignments> assignments = new ArrayList<Assignments>();
		    	for(User u : selectedUsers) {
		    		if(u != null && u.getUserName() != null) {
		    			Assignments a = new Assignments();
		    			a.setUserName(u.getUserName());
		    			assignments.add(a);
		    		}
		    	}
		    	g.setAssignments(assignments);
		    	new PutGroup(getActivity(), this, g).execute();
		    	return true;
		    case R.id.action_logout:
		    	UserName.clearUserName(getActivity());
	    		Intent intent = new Intent(getActivity(), LoginActivity.class);
	    		startActivity(intent);
	    		getActivity().finish();
				return true;
		    default:
		        return super.onOptionsItemSelected(item);
		    }
		}
		
		@Override
		public void onActivityResult(int requestCode, int resultCode,
				Intent data) {
			// TODO Auto-generated method stub
			super.onActivityResult(requestCode, resultCode, data);
			if(resultCode == RESULT_OK) {
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
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public void modifyUserSuccess(ModifyUserMethods method, Object user) {
			UserName.setUsers((ArrayList<User>) user);
			setupView();
		}

		@Override
		public void modifyUserFailure(ModifyUserMethods method, Object user) {
			// TODO Auto-generated method stub
			Toast.makeText(getActivity(), "Not Good.", Toast.LENGTH_SHORT).show();
		}

		@Override
		public void deleteUser(int position) {
			// TODO Auto-generated method stub
			selectedUsers.remove(position);
			userAdapter.notifyDataSetChanged();
		}

		@Override
		public void modifyGroupSuccess(ModifyGroupMethods method, Object group) {
			// TODO Auto-generated method stub
			UserName.getGroups().add((Group) group);
			getActivity().finish();
		}

		@Override
		public void modifyGroupFailure(ModifyGroupMethods method, Object group) {
			// TODO Auto-generated method stub
			Toast.makeText(getActivity(), "Oops! Creation failed. Maybe it just isn't meant to be.", Toast.LENGTH_SHORT).show();
			Toast.makeText(getActivity(), "I suggest you try a healthier snack.", Toast.LENGTH_SHORT).show();
		}
	}

}
