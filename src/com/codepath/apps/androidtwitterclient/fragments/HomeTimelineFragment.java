package com.codepath.apps.androidtwitterclient.fragments;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.codepath.apps.androidtwitterclient.TweetAdapter;
import com.codepath.apps.androidtwitterclient.TwitterClientApp;
import com.codepath.apps.androidtwitterclient.models.Tweet;
import com.codepath.apps.androidtwitterclient.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

public class HomeTimelineFragment extends TweetsListFragment {
	long maxId;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.i(TAG, "in onCreate in HomeTimelineFragment");
		super.onCreate(savedInstanceState);
		ArrayList<Tweet> tweets = new ArrayList<Tweet>();
		adapter = new TweetAdapter(getActivity(), tweets);
		scrollListener = new TweetsEndlessScrollListener();
		onRefreshListener = new TweetsOnRefreshListener();
		SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(getActivity());
		long userId = pref.getLong("userId", 0);
		Log.i(TAG, "Wthats userId; " + userId);
		if (userId == 0) {
			//don't call user credentials for no reason
			getUserCredentials();
		}
		getTweets();

	}

	@Override
	protected void getTweets() {
		getHomeTimeline();

	}

	public void getUserCredentials() {
		Log.i(TAG, "in getUserCredentials");
		TwitterClientApp.getRestClient().getCredentials(
				new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(JSONObject jsonObject) {
						user = User.fromJSON(jsonObject);
						user.save();
						SharedPreferences pref = PreferenceManager
								.getDefaultSharedPreferences(getActivity());
						Editor edit = pref.edit();
						edit.putLong("userId", user.getUserId());
						edit.commit();
						Log.d(TAG, "Getting user: " + user);
					}

				});
	}

	public void getHomeTimeline() {
		Log.i(TAG, "Getting home timeline, with maxId: " + maxId);
		TwitterClientApp.getRestClient().getHomeTimeline(maxId,
				new JsonHttpResponseHandler() {
					@Override
					public void onStart() {
						super.onStart();
						Log.i(TAG, "start home timeline request");
					}

					@Override
					public void onSuccess(JSONArray jsonArray) {
						Log.i(TAG, "Woohoo, got tweets!");
						ArrayList<Tweet> tweetList = Tweet.fromJSON(jsonArray);
						adapter.clear();
						adapter.addAll(tweetList);
						Log.i(TAG,
								"adapter is not null, not redefining, adapter count is: "
										+ adapter.getCount());

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
