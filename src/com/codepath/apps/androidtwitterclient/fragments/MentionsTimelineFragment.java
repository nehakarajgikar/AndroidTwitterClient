package com.codepath.apps.androidtwitterclient.fragments;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;

import com.codepath.apps.androidtwitterclient.TweetAdapter;
import com.codepath.apps.androidtwitterclient.TwitterClientApp;
import com.codepath.apps.androidtwitterclient.models.Tweet;
import com.codepath.apps.androidtwitterclient.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

public class MentionsTimelineFragment extends TweetsListFragment {
	long maxId;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ArrayList<Tweet> tweets = new ArrayList<Tweet>();
		adapter = new TweetAdapter(getActivity(), tweets);
		scrollListener = new TweetsEndlessScrollListener();
		onRefreshListener = new TweetsOnRefreshListener();
		// getUserCredentials();
		getTweets();

	}

	public void getUserCredentials() {
		Log.i(TAG, "in getUserCredentials");
		TwitterClientApp.getRestClient().getCredentials(
				new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(JSONObject jsonObject) {
						user = User.fromJSON(jsonObject);
						Log.d(TAG, "Getting user: " + user);
					}

				});
	}

	public void getMentionsTimeline() {
		TwitterClientApp.getRestClient().getMentionsTimeline(maxId,
				new JsonHttpResponseHandler() {
					@Override
					public void onStart() {
						super.onStart();
						Log.i(TAG, "in onStart for mentions timeline");
					}

					@Override
					public void onSuccess(JSONArray jsonArray) {
						super.onSuccess(jsonArray);
						Log.i(TAG, "mentions timeline: " + jsonArray.length());
						ArrayList<Tweet> tweetList = Tweet.fromJSON(jsonArray);
						getAdapter().clear();
						getAdapter().addAll(tweetList);
						Log.i(
								TAG,
								"mentions timeline: last tweet is: "
										+ tweetList.get(tweetList.size() - 1));
						maxId = Tweet.getMaxId(tweetList);
						lvTweets.onRefreshComplete();

					}
				});
	}

	@Override
	protected void getTweets() {
		Log.i(TAG, "In mentions getTweets");
		getMentionsTimeline();

	}

	@Override
	protected void setMaxIdToZero() {
		this.maxId = 0;

	}
}
