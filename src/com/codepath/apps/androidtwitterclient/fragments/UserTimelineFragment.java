package com.codepath.apps.androidtwitterclient.fragments;

import java.util.ArrayList;

import org.json.JSONArray;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.codepath.apps.androidtwitterclient.TweetAdapter;
import com.codepath.apps.androidtwitterclient.TwitterClientApp;
import com.codepath.apps.androidtwitterclient.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class UserTimelineFragment extends TweetsListFragment {
	long maxId;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.i(TAG, "in onCreate in HomeTimelineFragment");
		super.onCreate(savedInstanceState);
		ArrayList<Tweet> tweets = new ArrayList<Tweet>();
		adapter = new TweetAdapter(getActivity(), tweets);
		scrollListener = new TweetsEndlessScrollListener();
		onRefreshListener = new TweetsOnRefreshListener();
		// getUserCredentials();
		getTweets();

	}

	@Override
	protected void getTweets() {
		getUserTimeline();
	}

	public void getUserTimeline() {
		Log.i(TAG, "Getting home timeline, with maxId: " + maxId);
		TwitterClientApp.getRestClient().getUserTimeline(maxId,
				new JsonHttpResponseHandler() {
					@Override
					public void onStart() {
						super.onStart();
						Log.i(TAG, "start user timeline request");
					}

					@Override
					public void onSuccess(JSONArray jsonArray) {
						Log.i(TAG, "Woohoo, got tweets!");
						ArrayList<Tweet> tweetList = Tweet.fromJSON(jsonArray);
						adapter.clear();
						adapter.addAll(tweetList);
						Log.d(TAG,
								"Got tweets on home timeline, first one, array size is: "
										+ jsonArray.length());

						maxId = Tweet.getMaxId(tweetList);
						Log.i(TAG, "max id is: " + maxId);

						lvTweets.onRefreshComplete();

					}

					@Override
					public void onFailure(Throwable e, JSONArray array) {
						Log.e(TAG, "whoops, there was a problem in home timeline getting."
								+ e.getMessage());
						Toast.makeText(getActivity(),
								"Some network problem, try again later!", Toast.LENGTH_SHORT)
								.show();
					}
				});

	}

	@Override
	protected void setMaxIdToZero() {
		this.maxId = 0;

	}

}
