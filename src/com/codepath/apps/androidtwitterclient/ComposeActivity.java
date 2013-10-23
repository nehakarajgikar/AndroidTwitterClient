package com.codepath.apps.androidtwitterclient;

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

import com.codepath.apps.androidtwitterclient.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ComposeActivity extends Activity {
	public static final String TAG = "TWITTER";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose);

		ImageView ivProfile = (ImageView) findViewById(R.id.ivComposeProfile);

		ImageLoader.getInstance().displayImage(
				getIntent().getStringExtra("imageUrl"), ivProfile);
		TextView tvName = (TextView) findViewById(R.id.tvComposeName);
		tvName.setText(Html.fromHtml("<b><font color=#EE799F>"
				+ getIntent().getStringExtra("name") + "</font></b>"));
		TextView screenName = (TextView) findViewById(R.id.tvComposeScreenName);
		screenName.setText(Html.fromHtml("<font color=#565051><small>"
				+ "@"+getIntent().getStringExtra("screenName") + "</small></font>"));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.compose, menu);
		return true;
	}

	public void onPostAction(MenuItem mi) {
		Intent i = new Intent(getApplicationContext(), ComposeActivity.class);

		Toast.makeText(getApplicationContext(), "Tweeting..", Toast.LENGTH_LONG)
				.show();
		EditText tweet = (EditText) findViewById(R.id.etTweetText);
		if(tweet.getText() == null || tweet.getText().toString() == null || tweet.getText().toString().equalsIgnoreCase("")){
			Toast.makeText(getApplicationContext(), "Say something!", Toast.LENGTH_SHORT).show();
			return;
		}
		Log.d(TAG,"About to tweet: "+tweet.getText().toString());
		TwitterClientApp.getRestClient().postTweet(tweet.getText().toString(), new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(JSONObject jsonObject) {
				Toast.makeText(getBaseContext(), "Successfully tweeted", Toast.LENGTH_SHORT).show();
				Log.d(TAG,"Sucessfully tweeted, res object is: "+jsonObject.toString());
//				super.onSuccess(arg0);
				
			}
		});
		setResult(RESULT_OK);
		finish();
	}

}
