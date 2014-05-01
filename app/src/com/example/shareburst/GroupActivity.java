package com.example.shareburst;

import java.util.ArrayList;
import java.util.Locale;

import com.example.data.UserName;
import com.example.rest.Assignments;
import com.example.rest.Group;
import com.example.rest.ModifyUser;
import com.example.rest.User;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v13.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class GroupActivity extends Activity implements ActionBar.TabListener, ModifyUser {

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
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
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
			Locale l = Locale.getDefault();
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
		private static final String ARG_RED = "num_red";
		private static final String ARG_YELLOW = "num_yellow";
		private static final String ARG_PINK = "num_pink";
		private static final String ARG_ORANGE = "num_orange";
		
		private int red;
		private int yellow;
		private int pink;
		private int orange;

		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static PlaceholderFragment newInstance(Assignments a) {
			PlaceholderFragment fragment = new PlaceholderFragment();
			Bundle args = new Bundle();
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
			TextView textView = (TextView) rootView
					.findViewById(R.id.section_label);
			red = getArguments().getInt(ARG_RED);
			yellow = getArguments().getInt(ARG_YELLOW);
			pink = getArguments().getInt(ARG_PINK);
			orange = getArguments().getInt(ARG_ORANGE);
			textView.setText("Red: " + red + "\nYellow: " + yellow + "\nPink: " + pink + "\nOrange: " + orange);
			return rootView;
		}
	}

	@Override
	public void modifyUserSuccess(ModifyUserMethods method, Object user) {
		// TODO Auto-generated method stub
		String userName = UserName.getUserName(getApplicationContext());
		ArrayList<User> listOfUsers =  (ArrayList<User>) user;
		for(int i = 0; i < listOfUsers.size(); i++) {
			User u = listOfUsers.get(i);
			if(u.getUserName() == null || u.getUserName().equals(userName)) {
				listOfUsers.remove(i);
				break;
			}
		}
		UserName.setUsers(listOfUsers);
		proceed = true;
		loadTabs();
	}

	@Override
	public void modifyUserFailure(ModifyUserMethods method, Object user) {
		// TODO Auto-generated method stub
		
	}

}