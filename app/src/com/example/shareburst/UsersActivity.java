package com.example.shareburst;

import java.util.ArrayList;

import com.example.data.UserName;
import com.example.rest.User;
import com.example.rest.ModifyUser;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.users, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch (item.getItemId()) {
        case R.id.action_logout:
        	UserName.clearUserName(getApplicationContext());
    		Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
    		startActivity(intent);
    		finish();
        	return true;
        default:
            return super.onOptionsItemSelected(item);
		}
	}
	
	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment implements ModifyUser {

		UserAdapter adapter;
		ListView usersList;
		
		public PlaceholderFragment() {
		}
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_users,
					container, false);
			
			usersList = (ListView) rootView.findViewById(R.id.usersList);
			usersList.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					User u = (User) usersList.getItemAtPosition(position);
					Intent returnIntent = new Intent();
					returnIntent.putExtra("userName",u.getUserName());
					returnIntent.putExtra("firstName",u.getFirstName());
					returnIntent.putExtra("lastName",u.getLastName());
					getActivity().setResult(RESULT_OK,returnIntent);     
					getActivity().finish();
				}
				
			});
			new ListUser(getActivity(), this).execute();
			
			return rootView;
		}

		@SuppressWarnings("unchecked")
		@Override
		public void modifyUserSuccess(ModifyUserMethods method, Object user) {
			// TODO Auto-generated method stub
			adapter = new UserAdapter(getActivity(), R.id.usersList, (ArrayList<User>) user, false);
			usersList.setAdapter(adapter);
	    	usersList.setVisibility(View.VISIBLE);
		}

		@Override
		public void modifyUserFailure(ModifyUserMethods method, Object user) {
			// TODO Auto-generated method stub
			Toast.makeText(getActivity(), "NOT WORK", Toast.LENGTH_LONG).show();
		}
	}

}
