package com.shareburst.user;

import java.util.ArrayList;

import com.example.data.UserName;
import com.example.rest.User;
import com.example.rest.ModifyUser;
import com.shareburst.R;
import com.shareburst.main.LoginActivity;
import com.shareburst.preference.PreferenceActivity;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class UsersActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_users);
		
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}
	
	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment implements ModifyUser {

		UserAdapter adapter;
		ListView usersList;
		EditText inputSearch;
		
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
			View rootView = inflater.inflate(R.layout.fragment_users,
					container, false);
			
			inputSearch = (EditText) rootView.findViewById(R.id.inputSearch);
			inputSearch.addTextChangedListener(new TextWatcher() {

				@Override
				public void beforeTextChanged(CharSequence s, int start,
						int count, int after) {
					// TODO Auto-generated method stub
				}

				@Override
				public void onTextChanged(CharSequence s, int start,
						int before, int count) {
					// TODO Auto-generated method stub
					adapter.getFilter().filter(s);
				}

				@Override
				public void afterTextChanged(Editable s) {
					// TODO Auto-generated method stub
					
				}
				
			});
			
			usersList = (ListView) rootView.findViewById(R.id.usersList);
			usersList.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					User u = (User) adapter.getItem(position);
					Intent returnIntent = new Intent();
					returnIntent.putExtra("userName",u.getUserName());
					returnIntent.putExtra("firstName",u.getFirstName());
					returnIntent.putExtra("lastName",u.getLastName());
					getActivity().setResult(RESULT_OK,returnIntent);     
					getActivity().finish();
				}
				
			});
			if(UserName.getUsers() == null) {
				new ListUser(getActivity(), this).execute();
			} else {
				refreshView();
			}
			
			return rootView;
		}

		@Override
		public void onCreateOptionsMenu (Menu menu, MenuInflater inflater) {
			inflater.inflate(R.menu.users, menu);
			super.onCreateOptionsMenu(menu, inflater);
		}
		
		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
		    // Handle item selection
		    switch (item.getItemId()) {
		    case R.id.action_refresh:
		    	new ListUser(getActivity(), this).execute();
		    	return true;
		    case R.id.action_set_preferences:
		    	Intent prefsIntent = new Intent(getActivity(), PreferenceActivity.class);
	        	startActivity(prefsIntent);
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
		
		public void refreshView() {
			adapter = new UserAdapter(getActivity(), R.id.usersList, UserName.getUsers(), null);
			usersList.setAdapter(adapter);
	    	usersList.setVisibility(View.VISIBLE);
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public void modifyUserSuccess(ModifyUserMethods method, Object user) {
			// TODO Auto-generated method stub
			UserName.getUserName(getActivity());
			ArrayList<User> listOfUsers =  (ArrayList<User>) user;
			UserName.setUsers(listOfUsers);
			refreshView();
		}

		@Override
		public void modifyUserFailure(ModifyUserMethods method, Object user) {
			// TODO Auto-generated method stub
			Toast.makeText(getActivity(), "NOT WORK", Toast.LENGTH_LONG).show();
		}
	}

}
