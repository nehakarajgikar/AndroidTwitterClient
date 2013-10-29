package com.codepath.apps.androidtwitterclient;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.query.Select;
import com.codepath.apps.androidtwitterclient.models.User;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ProfileActivity extends FragmentActivity {
	private static final String TAG = "TWITTER";
	User user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(this);
		long userId = pref.getLong("userId", 0);
		Log.i(TAG, "Wthats userId; " + userId);
		this.user = new Select().from(User.class).where("userId = ?", userId)
				.executeSingle();
		if (this.user == null) {
			Toast.makeText(getApplicationContext(),
					"Whoops! Something's wrong! Try again later!", Toast.LENGTH_SHORT)
					.show();
		} else {
			Log.i(TAG, "what's user in profile activity: " + user);
			populateUser();
		}
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
		getActionBar().setTitle("@" + user.getScreenName());

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile, menu);
		return true;
	}

}
