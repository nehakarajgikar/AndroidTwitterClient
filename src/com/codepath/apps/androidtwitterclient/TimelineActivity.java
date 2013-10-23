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
public User user;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		
		TwitterClientApp.getRestClient().getCredentials(
				new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(JSONObject jsonObject) {
						user = User.fromJSON(jsonObject);
						Log.d(TAG,"Getting user: "+user);
					}

				});
	
		TwitterClientApp.getRestClient().getHomeTimeline(new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(JSONArray jsonArray) {
				Log.d(TAG, "Got tweets on home timeline");
				ArrayList<Tweet> tweetList = Tweet.fromJSON(jsonArray);
				Log.d(TAG, jsonArray.toString());
				ListView lvTweets = (ListView) findViewById(R.id.lvTweets);
				TweetAdapter tweetAdapter = new TweetAdapter(getBaseContext(), tweetList);
				lvTweets.setAdapter(tweetAdapter);
			}
		});
	}

	public void onComposeAction(MenuItem mi) {
		Intent i = new Intent(getApplicationContext(), ComposeActivity.class);
		if (user != null) {
			Log.i(TAG, "user is: "+user.toString());
			i.putExtra("name", user.getName());
			i.putExtra("screenName", user.getScreenName());
			i.putExtra("imageUrl", user.getProfileImageUrl());
		}
		Log.d(TAG,"Going into Compose window");
		startActivityForResult(i, REQUEST_CODE);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				Log.d(TAG, "Came back to home timeline, after tweeting");
				// Toast.makeText(getApplicationContext(),
				// "returned from settings activity", Toast.LENGTH_SHORT).show();
//				settings = (Settings) data.getSerializableExtra("settings");
//				Log.d(TAG, "In on activity result, settings is: " + settings);
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
