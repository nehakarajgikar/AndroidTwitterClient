package com.codepath.apps.androidtwitterclient;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.codepath.apps.androidtwitterclient.models.Tweet;
import com.codepath.apps.androidtwitterclient.models.User;

public class TimelineActivity extends FragmentActivity {
	public static final String TAG = "TWITTER";
	public static final int REQUEST_CODE = 100;
	// public long maxId;
	public User user;
	// public PullToRefreshListView lvTweets;
	public TweetAdapter tweetAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
//		setupNavigationTabs();
	}

	private void setupNavigationTabs() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);
		Tab tabHome = actionBar.newTab().setText("Home").setTag(
				"HomeTimelineFragment").setIcon(R.drawable.ic_home);
		Tab tabMentions = actionBar.newTab().setText("Mentions").setTag("MentionsTimelineFragment").setIcon(R.drawable.ic_mentions);
		actionBar.addTab(tabHome);
		actionBar.addTab(tabMentions);
		actionBar.selectTab(tabHome);

	}

	public void onComposeAction(MenuItem mi) {
		Intent i = new Intent(getApplicationContext(), ComposeActivity.class);
		if (user != null) {
			Log.i(TAG, "user is: " + user.toString());
			i.putExtra("user", user);
		}
		Log.d(TAG, "Going into Compose window");
		startActivityForResult(i, REQUEST_CODE);
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

}
