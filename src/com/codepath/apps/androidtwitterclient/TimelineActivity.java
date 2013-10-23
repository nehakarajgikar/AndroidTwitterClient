package com.codepath.apps.androidtwitterclient;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.codepath.apps.androidtwitterclient.models.Tweet;
import com.codepath.apps.androidtwitterclient.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

public class TimelineActivity extends Activity {
	public static final String TAG = "TWITTER";
	public static final int REQUEST_CODE = 100;
	public long maxId;
	public User user;
	public ListView lvTweets;
	public TweetAdapter tweetAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		lvTweets = (ListView) findViewById(R.id.lvTweets);
		lvTweets.setOnScrollListener(new TweetsEndlessScrollListener());
		TwitterClientApp.getRestClient().getCredentials(
				new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(JSONObject jsonObject) {
						user = User.fromJSON(jsonObject);
						Log.d(TAG, "Getting user: " + user);
					}

				});
		getHomeTimeline();

	}

	private void getHomeTimeline() {
		TwitterClientApp.getRestClient().getHomeTimeline(maxId,
				new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(JSONArray jsonArray) {

						Log.d(TAG,
								"Got tweets on home timeline, first one, array size is: "
										+ jsonArray.length());
						ArrayList<Tweet> tweetList = Tweet.fromJSON(jsonArray);
						maxId = Tweet.getMaxId(tweetList);
						Log.d(TAG, jsonArray.toString());
						lvTweets = (ListView) findViewById(R.id.lvTweets);
						tweetAdapter = new TweetAdapter(getBaseContext(), tweetList);
						for (int i = 0; i < tweetAdapter.getCount(); i++) {
							Log.d(TAG,tweetAdapter.getItem(i).toString());
						}
						lvTweets.setAdapter(tweetAdapter);

					}
				});

	}

	private class TweetsEndlessScrollListener extends EndlessScrollListener {
		@Override
		public void onLoadMore(int page, int totalItemsCount) {
			Log.i(TAG, "in On load more: what's maxid: " + maxId);
			Log.i(TAG, "in On load more: what's page: " + page);
			Log.i(TAG, "in On load more: what's totalItemscount: " + totalItemsCount);
			getHomeTimeline();
		}
	}

	public void onComposeAction(MenuItem mi) {
		Intent i = new Intent(getApplicationContext(), ComposeActivity.class);
		if (user != null) {
			Log.i(TAG, "user is: " + user.toString());
			// i.putExtra("name", user.getName());
			// i.putExtra("screenName", user.getScreenName());
			// i.putExtra("imageUrl", user.getProfileImageUrl());
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
			if (resultCode == RESULT_CANCELED){
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
