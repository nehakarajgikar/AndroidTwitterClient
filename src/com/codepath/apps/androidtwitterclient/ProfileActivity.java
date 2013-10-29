package com.codepath.apps.androidtwitterclient;

import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.androidtwitterclient.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ProfileActivity extends FragmentActivity {
	private static final String TAG = "TWITTER";
	User user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		this.user = (User) getIntent().getSerializableExtra("user");
		Log.i(TAG, "what's user in profile activity: " + user);
		getActionBar().setTitle("@" + user.getScreenName());
		populateUser();
	}

	private void populateUser() {
		TextView tvName = (TextView) findViewById(R.id.tvName);
		tvName.setText(user.getName());
		TextView tvTagline = (TextView) findViewById(R.id.tvTagline);
		tvTagline.setText(user.getTagline());
		TextView tvFollowers = (TextView) findViewById(R.id.tvFollowers);
		tvFollowers.setText(user.getFollowersCount() + " followers");
		TextView tvFollowing = (TextView) findViewById(R.id.tvFollowing);
		tvFollowing.setText("" + user.getFriendsCount() + " following");
		ImageView ivProfile = (ImageView) findViewById(R.id.ivProfileImage);
		ImageLoader.getInstance()
				.displayImage(user.getProfileImageUrl(), ivProfile);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile, menu);
		return true;
	}

}
