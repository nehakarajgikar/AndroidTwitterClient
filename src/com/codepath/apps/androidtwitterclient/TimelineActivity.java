package com.codepath.apps.androidtwitterclient;

import java.util.ArrayList;

import org.json.JSONArray;

import com.codepath.apps.androidtwitterclient.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;

public class TimelineActivity extends Activity {
public static final String TWITTER = "TWITTER";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		TwitterClientApp.getRestClient().getHomeTimeline(new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(JSONArray jsonArray) {
				ArrayList<Tweet> tweetList = Tweet.fromJSON(jsonArray);
				Log.d(TWITTER, jsonArray.toString());
//				super.onSuccess(arg0, arg1);
				ListView lvTweets = (ListView) findViewById(R.id.lvTweets);
				TweetAdapter tweetAdapter = new TweetAdapter(getBaseContext(), tweetList);
				lvTweets.setAdapter(tweetAdapter);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.timeline, menu);
		return true;
	}

}
