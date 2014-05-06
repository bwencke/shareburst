package com.shareburst.group;

import java.util.ArrayList;

import com.example.data.UserName;
import com.example.rest.Assignments;
import com.example.rest.Group;
import com.example.rest.ModifyGroup;
import com.example.rest.ModifyUser;
import com.example.rest.User;
import com.shareburst.R;
import com.shareburst.main.LoginActivity;
import com.shareburst.preference.PreferenceActivity;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v13.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class GroupActivity extends Activity implements ActionBar.TabListener, ModifyUser, ModifyGroup {

	int pos;
	Group group;
	boolean proceed;
	
	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a {@link FragmentPagerAdapter}
	 * derivative, which will keep every loaded fragment in memory. If this
	 * becomes too memory intensive, it may be best to switch to a
	 * {@link android.support.v13.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group);

		pos = getIntent().getIntExtra("pos", 0);
		group = UserName.getGroups().get(pos);
				
		if(UserName.getUsers() == null) {
			proceed = false;
			new ListUser(GroupActivity.this, this).execute();
		} else {
			proceed = true;
		}
		if(proceed) {
			setup();
		}
	}
	
	public void setup() {
		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the activity.
		mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});

		// For each of the sections in the app, add a tab to the action bar.
		loadTabs();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.group, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch (item.getItemId()) {
	    case R.id.action_edit:
	    	Intent intent = new Intent(getApplicationContext(), NewGroupActivity.class);
	    	intent.putExtra("groupID", group.getGroupID());
	    	intent.putExtra("returnToGroupActivity", true);
	    	startActivity(intent);
	    	finish();
	    	return true;
	    case R.id.action_discard:
	    	new DeleteGroup(GroupActivity.this, this, group.getGroupID()).execute();
	    	return true;
        case R.id.action_set_preferences:
        	Intent prefsIntent = new Intent(getApplicationContext(), PreferenceActivity.class);
        	startActivityForResult(prefsIntent, RESULT_OK);
            return true;
        case R.id.action_logout:
        	UserName.clearUserName(getApplicationContext());
    		Intent i = new Intent(getApplicationContext(), LoginActivity.class);
    		startActivity(i);
    		finish();
            return true;
        default:
            return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a PlaceholderFragment (defined as a static inner class
			// below).
			return PlaceholderFragment.newInstance(group.getAssignments().get(position));
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return group.getAssignments().size();
		}

		@Override
		public CharSequence getPageTitle(int position) {
			String userName = group.getAssignments().get(position).getUserName();
			if(userName != null) {
				if(userName.equals(UserName.getUserName(getApplicationContext()))) {
					return "Me";
					//return ((UserName.getUser().getFirstName() == null) ? "" : UserName.getUser().getFirstName()) + " " +
		            //		((UserName.getUser().getLastName() == null) ? "" : UserName.getUser().getLastName());
				}
				for(User u : UserName.getUsers()) {
					if(u != null && u.getUserName() != null && u.getUserName().equals(userName)) {
						return u.getName();
					}
				}
			}
			return "[error]";
		}
	}

	public void loadTabs() {
		if(proceed) {
			for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
				// Create a tab with text corresponding to the page title defined by
				// the adapter. Also specify this Activity object, which implements
				// the TabListener interface, as the callback (listener) for when
				// this tab is selected.
				ActionBar actionBar = getActionBar();
				actionBar.addTab(actionBar.newTab()
						.setText(mSectionsPagerAdapter.getPageTitle(i))
						.setTabListener(this));
			}
		}
	}
	
	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String USERNAME = "username";
		private static final String ARG_RED = "num_red";
		private static final String ARG_YELLOW = "num_yellow";
		private static final String ARG_PINK = "num_pink";
		private static final String ARG_ORANGE = "num_orange";
		
		private double red;
		private double yellow;
		private double pink;
		private double orange;
		private String username;

		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static PlaceholderFragment newInstance(Assignments a) {
			PlaceholderFragment fragment = new PlaceholderFragment();
			Bundle args = new Bundle();
			args.putString(USERNAME, a.getUserName());
			args.putInt(ARG_RED, a.getRed());
			args.putInt(ARG_YELLOW, a.getYellow());
			args.putInt(ARG_PINK, a.getPink());
			args.putInt(ARG_ORANGE, a.getOrange());
			fragment.setArguments(args);
			return fragment;
		}

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_group,
					container, false);
			
			red = (double) getArguments().getInt(ARG_RED);
			yellow = (double) getArguments().getInt(ARG_YELLOW);
			pink = (double) getArguments().getInt(ARG_PINK);
			orange = (double) getArguments().getInt(ARG_ORANGE);
			double total = red + yellow + pink + orange;
			double multiplier = 350.0;
			
			int redpx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (int) (multiplier*red/total), getResources().getDisplayMetrics());
			int yellowpx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (int) (multiplier*yellow/total), getResources().getDisplayMetrics());
			int orangepx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (int) (multiplier*orange/total), getResources().getDisplayMetrics());
			int pinkpx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (int) (multiplier*pink/total), getResources().getDisplayMetrics());
			
			
			View redBlock = (View) rootView.findViewById(R.id.red_block);
			redBlock.getLayoutParams().height = redpx;   //(int) (multiplier*red/total);
			
			View yellowBlock = (View) rootView.findViewById(R.id.yellow_block);
			yellowBlock.getLayoutParams().height = yellowpx;   //(int) (multiplier*yellow/total);
			
			View orangeBlock = (View) rootView.findViewById(R.id.orange_block);
			orangeBlock.getLayoutParams().height = orangepx;   //(int) (multiplier*orange/total);
			
			View pinkBlock = (View) rootView.findViewById(R.id.pink_block);
			pinkBlock.getLayoutParams().height = pinkpx;   //(int) (multiplier*pink/total);
			
			TextView redText = (TextView) rootView.findViewById(R.id.red_num);
			redText.setText((int) red + "");
			
			TextView yellowText = (TextView) rootView.findViewById(R.id.yellow_num);
			yellowText.setText((int) yellow + "");
			
			TextView orangeText = (TextView) rootView.findViewById(R.id.orange_num);
			orangeText.setText((int) orange + "");
			
			TextView pinkText = (TextView) rootView.findViewById(R.id.pink_num);
			pinkText.setText((int) pink + "");
			
			username = getArguments().getString(USERNAME);
			/*TextView textView = (TextView) rootView
					.findViewById(R.id.section_label);
			red = getArguments().getInt(ARG_RED);
			yellow = getArguments().getInt(ARG_YELLOW);
			pink = getArguments().getInt(ARG_PINK);
			orange = getArguments().getInt(ARG_ORANGE);
			textView.setText("Red: " + red + "\nYellow: " + yellow + "\nPink: " + pink + "\nOrange: " + orange);*/
			
			TextView name = (TextView) rootView
					.findViewById(R.id.name);
			if(UserName.getUserName(getActivity()).equals(username)) {
				name.setText("Me");
			} else {
				for(User u : UserName.getUsers()) {
					if(u != null && u.getUserName() != null && u.getUserName().equals(username)) {
						name.setText(u.getName());
					}
				}
			}
			return rootView;
		}
	}

	@Override
	public void modifyUserSuccess(ModifyUserMethods method, Object user) {
		UserName.getUserName(getApplicationContext());
		@SuppressWarnings("unchecked")
		ArrayList<User> listOfUsers =  (ArrayList<User>) user;
		UserName.setUsers(listOfUsers);
		proceed = true;
		setup();
	}

	@Override
	public void modifyUserFailure(ModifyUserMethods method, Object user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void modifyGroupSuccess(ModifyGroupMethods method, Object group) {
		// TODO Auto-generated method stub
		switch(method) {
		case DELETE:
			UserName.getGroups().remove(this.group);
			finish();
			break;
		case LIST:
			break;
		case PUT:
			break;
		default:
			break;
		}
	}

	@Override
	public void modifyGroupFailure(ModifyGroupMethods method, Object group) {
		// TODO Auto-generated method stub
		
	}

}
