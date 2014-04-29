package com.example.shareburst;

import com.example.data.UserName;
import com.example.rest.ModifyUser;
import com.example.rest.User;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.os.Build;

public class HomeActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_logout) {
			logout();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void logout() {
		UserName.clearUserName(getApplicationContext());
		Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
		startActivity(intent);
		finish();
	}
	
	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment implements ModifyUser {

		TextView msg;
		Button logout;
		Button preferencesButton;
		Button groupsButton;
		User user;
		
		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_home, container,
					false);
			
			msg = (TextView) rootView.findViewById(R.id.textView1);
			new GetUser(getActivity(), this, UserName.getUserName(getActivity())).execute();
			
			final TextView fact = (TextView) rootView.findViewById(R.id.starburstFact);
			Resources res = getResources();
			final String[] facts = res.getStringArray(R.array.starburstFacts);
			final int index = (int) (Math.random() * facts.length);
			fact.setText(facts[index]);
			fact.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					int newIndex = 0;
					do {
						newIndex = (int) (Math.random() * facts.length);
					} while(newIndex == index);
					fact.setText(facts[newIndex]);
				}
				
			});
			
			preferencesButton = (Button) rootView.findViewById(R.id.prefs_button);
			preferencesButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(getActivity(), PreferenceActivity.class);
					startActivity(intent);
				}
				
			});
			
			groupsButton = (Button) rootView.findViewById(R.id.group_button);
			groupsButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(getActivity(), GroupsActivity.class);
					startActivity(intent);
				}
				
			});
						
			return rootView;
		}
		
		public void updateView() {
			String firstName = (UserName.getUser().getFirstName() != null) ? UserName.getUser().getFirstName() : UserName.getUser().getUserName();
			msg.setText("Hello, " + firstName + "!");
		}

		@Override
		public void modifyUserSuccess(ModifyUserMethods method, Object user) {
			// TODO Auto-generated method stub
			UserName.setUser((User) user);
			updateView();
		}

		@Override
		public void modifyUserFailure(ModifyUserMethods method, Object user) {
			// TODO Auto-generated method stub
			UserName.setUserName(getActivity(), null);
			Intent intent = new Intent(getActivity(), LoginActivity.class);
			startActivity(intent);
			getActivity().finish();
		}
	}

}
