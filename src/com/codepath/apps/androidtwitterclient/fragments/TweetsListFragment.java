package com.codepath.apps.androidtwitterclient.fragments;

import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.RelativeLayout.LayoutParams;

import com.codepath.apps.androidtwitterclient.EndlessScrollListener;
import com.codepath.apps.androidtwitterclient.R;
import com.codepath.apps.androidtwitterclient.TweetAdapter;
import com.codepath.apps.androidtwitterclient.TwitterClientApp;
import com.codepath.apps.androidtwitterclient.models.Tweet;
import com.codepath.apps.androidtwitterclient.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import eu.erikw.PullToRefreshListView;
import eu.erikw.PullToRefreshListView.OnRefreshListener;

public abstract class TweetsListFragment extends Fragment {
	TweetAdapter adapter = null;
	
	User user = null;
	long userId = 0;
	public static String TAG = "TWITTER";
	public PullToRefreshListView lvTweets;
	TweetsEndlessScrollListener scrollListener;
	TweetsOnRefreshListener onRefreshListener;
	TweetOnLongClickListener onTweetLongClickListener;

	public TweetAdapter getAdapter() {
		return this.adapter;
	}

	public PullToRefreshListView getListView() {
		return this.lvTweets;
	}

	public User getUser() {
		return this.user;
	}

	protected abstract void getTweets();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.i(TAG, "in onCreateView in TweetsListFragment");
		View view = inflater.inflate(R.layout.fragment_tweets_list, container,
				false);
		lvTweets = (PullToRefreshListView) view.findViewById(R.id.lvTweets);
		lvTweets.setAdapter(adapter);
		Log.i(TAG, "is scroll listener set? "+scrollListener);
		lvTweets.setOnScrollListener(scrollListener);
		lvTweets.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> tweetAdapter, View v, int position,
					long id) {
				Builder builder = new AlertDialog.Builder(getActivity());
				builder.setMessage("More options");
				LinearLayout linearLayout = new LinearLayout(getActivity());
				LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,
						LayoutParams.WRAP_CONTENT);
				linearLayout.setLayoutParams(params);

				Button btnRetweet = new Button(getActivity());
				Tweet tweet = (Tweet) v.findViewById(R.id.ivProfile).getTag();
				v.setTag(tweet);
//				btnRetweet.setTag(tweet);
				btnRetweet.setLayoutParams(params);
				btnRetweet.setVisibility(Button.VISIBLE);
				btnRetweet.setText("Retweet");
				btnRetweet.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Tweet tweet = (Tweet) v.getTag();
						Log.i(TAG,"what's tweet: "+tweet);
						TwitterClientApp.getRestClient().retweet(tweet.getId(),
								new JsonHttpResponseHandler() {

									@Override
									public void onSuccess(JSONObject jsonObject) {
										// TODO Auto-generated method stub
										Toast.makeText(getActivity(), "retweeted", Toast.LENGTH_SHORT).show();
										Log.i(TAG, jsonObject.toString());
									}
								});

					}
				});

				Button btnFavorite = new Button(getActivity());
				btnFavorite.setLayoutParams(params);
				btnFavorite.setVisibility(Button.VISIBLE);
				btnFavorite.setText("Favourite");

				Button btnReplyTo = new Button(getActivity());
				btnReplyTo.setLayoutParams(params);
				btnReplyTo.setVisibility(Button.VISIBLE);
				btnReplyTo.setText("Reply to");

				linearLayout.addView(btnRetweet);
				linearLayout.addView(btnFavorite);
				linearLayout.addView(btnReplyTo);

				AlertDialog dialog = builder.create();
				dialog.setView(linearLayout);
				dialog.show();
				return false;
			}
		});
		
		lvTweets.setOnRefreshListener(onRefreshListener);
		
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		Log.i(TAG, "in onActivityCreated in TweetsListFragment");
		super.onActivityCreated(savedInstanceState);

	}

	protected class TweetsEndlessScrollListener extends EndlessScrollListener {

		

		@Override
		public void onLoadMore(int page, int totalItemsCount) {
//			Log.i(TAG, "in On load more: what's maxid: " + maxId);
			Log.i(TAG, "in On load more: what's page: " + page);
			Log.i(TAG, "in On load more: what's totalItemscount: " + totalItemsCount);
			getTweets();
		}

	}

	protected class TweetsOnRefreshListener implements OnRefreshListener {

		@Override
		public void onRefresh() {
			Log.i(TAG, "in onRefresh in TweetsOnRefreshListener");
			adapter.clear();
			setMaxIdToZero();
			getTweets();

		}

	}
	
	protected class TweetOnLongClickListener implements OnItemLongClickListener{

		@Override
		public boolean onItemLongClick(AdapterView<?> tweetAdapter, View v, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			return false;
		}
		
	}
	protected abstract void setMaxIdToZero();
//	protected abstract void setUserId();
}
