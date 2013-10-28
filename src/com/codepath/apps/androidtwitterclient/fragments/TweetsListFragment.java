package com.codepath.apps.androidtwitterclient.fragments;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.apps.androidtwitterclient.R;
import com.codepath.apps.androidtwitterclient.TweetAdapter;
import com.codepath.apps.androidtwitterclient.models.Tweet;
import com.codepath.apps.androidtwitterclient.models.User;

import eu.erikw.PullToRefreshListView;

public class TweetsListFragment extends Fragment {
	TweetAdapter adapter = null;
	long maxId = 0;
	User user = null;
	public static String TAG = "TWITTER";
	public PullToRefreshListView lvTweets;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.i(TAG, "in onCreateView in TweetsListFragment");
		return inflater.inflate(R.layout.fragment_tweets_list, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		Log.i(TAG, "in onActivityCreated in TweetsListFragment");
		super.onActivityCreated(savedInstanceState);
		ArrayList<Tweet> tweets = new ArrayList<Tweet>();
		adapter = new TweetAdapter(getActivity(), tweets);
		lvTweets = (PullToRefreshListView) getActivity()
				.findViewById(R.id.lvTweets);

		lvTweets.setAdapter(adapter);

	}
	

	public TweetAdapter getAdapter() {
		return this.adapter;
	}
	
	public User getUser(){
		return this.user;
	}
}
