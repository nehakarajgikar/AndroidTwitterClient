package com.codepath.apps.androidtwitterclient.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import com.codepath.apps.androidtwitterclient.EndlessScrollListener;
import com.codepath.apps.androidtwitterclient.R;
import com.codepath.apps.androidtwitterclient.TweetAdapter;
import com.codepath.apps.androidtwitterclient.models.User;

import eu.erikw.PullToRefreshListView;
import eu.erikw.PullToRefreshListView.OnRefreshListener;

public abstract class TweetsListFragment extends Fragment {
	TweetAdapter adapter = null;
	long maxId = 0;
	User user = null;
	public static String TAG = "TWITTER";
	public PullToRefreshListView lvTweets;
	TweetsEndlessScrollListener scrollListener;
	TweetsOnRefreshListener onRefreshListener;

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
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
//			Log.i(TAG,"Wowee, scrolling!!");
			super.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
		}
		
		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
//			Log.i(TAG,"Damn, scroll state changed");
			super.onScrollStateChanged(view, scrollState);
		}
		@Override
		public void onLoadMore(int page, int totalItemsCount) {
			Log.i(TAG, "in On load more: what's maxid: " + maxId);
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
			maxId = 0;
			getTweets();

		}

	}

}
