package com.codepath.apps.androidtwitterclient.fragments;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.codepath.apps.androidtwitterclient.EndlessScrollListener;
import com.codepath.apps.androidtwitterclient.TweetAdapter;
import com.codepath.apps.androidtwitterclient.TwitterClientApp;
import com.codepath.apps.androidtwitterclient.models.Tweet;
import com.codepath.apps.androidtwitterclient.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import eu.erikw.PullToRefreshListView.OnRefreshListener;

public class HomeTimelineFragment extends TweetsListFragment {
	TweetsEndlessScrollListener scrollListener;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.i(TAG, "in onCreate in HomeTimelineFragment");
		super.onCreate(savedInstanceState);
		scrollListener = new TweetsEndlessScrollListener();
		getUserCredentials();
		getHomeTimeline();

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

	public void getHomeTimeline() {
		Log.i(TAG, "Getting home timeline");
		TwitterClientApp.getRestClient().getHomeTimeline(maxId,
				new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(JSONArray jsonArray) {
						ArrayList<Tweet> tweetList = Tweet.fromJSON(jsonArray);
						if(adapter==null){
							Log.i(TAG, "adapter is null, redefining");
						adapter = new TweetAdapter(getActivity(), tweetList);
						lvTweets.setAdapter(adapter);
						
						}else{
							adapter.clear();
							lvTweets.setAdapter(adapter);
							lvTweets.setOnScrollListener(scrollListener);
							adapter.addAll(tweetList);
							Log.i(TAG,"adapter is not null, not redefining, adapter count is: "+adapter.getCount());
							
						}
						lvTweets.setOnRefreshListener(new TweetsOnRefreshListener());
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

	protected class TweetsEndlessScrollListener extends EndlessScrollListener {
		@Override
		public void onLoadMore(int page, int totalItemsCount) {
			Log.i(TAG, "in On load more: what's maxid: " + maxId);
			Log.i(TAG, "in On load more: what's page: " + page);
			Log.i(TAG, "in On load more: what's totalItemscount: " + totalItemsCount);
			getHomeTimeline();
		}
	}

	protected class TweetsOnRefreshListener implements OnRefreshListener {

		@Override
		public void onRefresh() {
			Log.i(TAG, "in onRefresh in TweetsOnRefreshListener");
			adapter.clear();
			maxId = 0;
			getHomeTimeline();

		}

	}
}
