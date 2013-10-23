package com.codepath.apps.androidtwitterclient;

import java.util.Date;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.androidtwitterclient.models.Tweet;
import com.codepath.apps.androidtwitterclient.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ComposeActivity extends Activity {
	public static final String TAG = "TWITTER";
	public User user;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose);
		user = (User) getIntent().getSerializableExtra("user");
		ImageView ivProfile = (ImageView) findViewById(R.id.ivComposeProfile);
		ImageLoader.getInstance()
				.displayImage(user.getProfileImageUrl(), ivProfile);
		TextView tvName = (TextView) findViewById(R.id.tvComposeName);
		tvName.setText(Html.fromHtml("<b><font color=#EE799F>" + user.getName()
				+ "</font></b>"));
		TextView screenName = (TextView) findViewById(R.id.tvComposeScreenName);
		screenName.setText(Html.fromHtml("<font color=#565051><small>" + "@"
				+ user.getScreenName() + "</small></font>"));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.compose, menu);
		return true;
	}

	public void onPostAction(MenuItem mi) {
		Intent i = new Intent();

		Toast.makeText(getApplicationContext(), "Tweeting..", Toast.LENGTH_LONG)
				.show();
		EditText tweet = (EditText) findViewById(R.id.etTweetText);
		if (tweet.getText() == null || tweet.getText().toString() == null
				|| tweet.getText().toString().equalsIgnoreCase("")) {
			Toast.makeText(getApplicationContext(), "Say something!",
					Toast.LENGTH_SHORT).show();
			return;
		}
		

		
		Log.d(TAG, "About to tweet: " + tweet.getText().toString());
		TwitterClientApp.getRestClient().postTweet(tweet.getText().toString(),
				new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(JSONObject jsonObject) {
						Toast.makeText(getBaseContext(), "Successfully tweeted",
								Toast.LENGTH_SHORT).show();
						Log.d(TAG,
								"Sucessfully tweeted, res object is: " + jsonObject.toString());
						// super.onSuccess(arg0);

					}
					
				});
		Tweet t = new Tweet(this.user,tweet.getText().toString(),new Date());
		i.putExtra("userTweet",t);
		setResult(RESULT_OK, i);
		finish();
	}

}
