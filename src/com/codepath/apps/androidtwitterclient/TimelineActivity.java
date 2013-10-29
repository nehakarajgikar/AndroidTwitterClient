package com.codepath.apps.androidtwitterclient;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.codepath.apps.androidtwitterclient.fragments.HomeTimelineFragment;
import com.codepath.apps.androidtwitterclient.fragments.MentionsTimelineFragment;
import com.codepath.apps.androidtwitterclient.models.Tweet;

public class TimelineActivity extends FragmentActivity implements TabListener{
	public static final String TAG = "TWITTER";
	public static final int REQUEST_CODE = 100;
	private static final int REQUEST_CODE_FOR_PROFILE_VIEW = 101;
	// public long maxId;
	
	// public PullToRefreshListView lvTweets;
	public TweetAdapter tweetAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		setupNavigationTabs();
	}

	private void setupNavigationTabs() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);
		Tab tabHome = actionBar.newTab().setText("Home")
				.setTag("HomeTimelineFragment").setIcon(R.drawable.ic_home).setTabListener(this);
		Tab tabMentions = actionBar.newTab().setText("Mentions")
				.setTag("MentionsTimelineFragment").setIcon(R.drawable.ic_mentions).setTabListener(this);
		actionBar.addTab(tabHome);
		actionBar.addTab(tabMentions);
		actionBar.selectTab(tabHome);

	}
	

	
	public void onComposeAction(MenuItem mi) {
		Intent i = new Intent(getApplicationContext(), ComposeActivity.class);
		Log.d(TAG, "Going into Compose window");
		startActivityForResult(i, REQUEST_CODE);
	}

	public void onProfileView(MenuItem mi){
		
		Intent i = new Intent(getApplicationContext(), ProfileActivity.class);
		startActivityForResult(i, REQUEST_CODE_FOR_PROFILE_VIEW);
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				Tweet newTweet = (Tweet) data.getSerializableExtra("userTweet");
				Log.d(TAG,
						"Came back to home timeline, after tweeting, you better see it"
								+ newTweet);

				tweetAdapter.insert(newTweet, 0);

			}
			if (resultCode == RESULT_CANCELED) {
				Log.d(TAG, "Compose cancelled");
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.timeline, menu);
		return true;
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
		android.support.v4.app.FragmentTransaction fts = fragmentManager.beginTransaction();
		
		if(tab.getTag().equals("HomeTimelineFragment")){
			fts.replace(R.id.frame_container, new HomeTimelineFragment());
		}else{
			fts.replace(R.id.frame_container, new MentionsTimelineFragment());
			
		}
		fts.commit();
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

}
