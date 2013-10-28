package com.codepath.apps.androidtwitterclient;

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
//	public long maxId;
	public User user;
//	public PullToRefreshListView lvTweets;
	public TweetAdapter tweetAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
//		lvTweets = (eu.erikw.PullToRefreshListView) findViewById(R.id.lvTweets);
		
//		lvTweets.setOnRefreshListener(new OnRefreshListener() {
//			
//			@Override
//			public void onRefresh() {
//				tweetAdapter.clear();
//				maxId = 0;
//				getHomeTimeline();
//			}
//		});
//		lvTweets.setOnScrollListener(new TweetsEndlessScrollListener());
//		TwitterClientApp.getRestClient().getCredentials(
//				new JsonHttpResponseHandler() {
//					@Override
//					public void onSuccess(JSONObject jsonObject) {
//						user = User.fromJSON(jsonObject);
//						Log.d(TAG, "Getting user: " + user);
//					}
//
//				});
//		getHomeTimeline();

	}

//	private void getHomeTimeline() {
//		TwitterClientApp.getRestClient().getHomeTimeline(maxId,
//				new JsonHttpResponseHandler() {
//					@Override
//					public void onSuccess(JSONArray jsonArray) {
//						ArrayList<Tweet> tweetList = Tweet.fromJSON(jsonArray);
//						if(tweetAdapter == null){
//							tweetAdapter = new TweetAdapter(getBaseContext(), tweetList);
//							lvTweets.setAdapter(tweetAdapter);
//						}else{
//							tweetAdapter.clear();
//							tweetAdapter.addAll(tweetList);
//							lvTweets.setAdapter(tweetAdapter);
//						}
//						Log.d(TAG,
//								"Got tweets on home timeline, first one, array size is: "
//										+ jsonArray.length());
//						
//						maxId = Tweet.getMaxId(tweetList);
//						
//						
////						lvTweets.setAdapter(tweetAdapter);
//						
//						lvTweets.onRefreshComplete();
//
//					}
//					
//					@Override
//					public void onFailure(Throwable e, JSONArray array) {
//						Log.e(TAG, "whoops, there was a problem in home timeline getting." + e.getMessage());
//						Toast.makeText(getBaseContext(), "Some network problem, try again later!", Toast.LENGTH_SHORT ).show();
//					}
//				});
//
//	}

//	private class TweetsEndlessScrollListener extends EndlessScrollListener {
//		@Override
//		public void onLoadMore(int page, int totalItemsCount) {
//			Log.i(TAG, "in On load more: what's maxid: " + maxId);
//			Log.i(TAG, "in On load more: what's page: " + page);
//			Log.i(TAG, "in On load more: what's totalItemscount: " + totalItemsCount);
//			getHomeTimeline();
//		}
//	}

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
