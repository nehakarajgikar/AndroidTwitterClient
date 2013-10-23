package com.codepath.apps.androidtwitterclient.models;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class Tweet implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2327578244944233945L;
	private static final String TAG = "TWITTER";
	private User user;
	private long id;
	private String body;
	private boolean isFavourited;
	private boolean isRetweeted;
	private Date timestamp;

	public Tweet(){}
	
	public Tweet(User user, String body, Date timestamp){
		this.user = user;
		this.body = body;
		this.timestamp = timestamp;
	}
	
	public User getUser() {
		return this.user;
	}

	public long getId() {

		return this.id;
	}

	public Date getTimestamp() {
		return this.timestamp;
	}

	public String getBody() {
		return this.body;
	}

	public boolean isFavourited() {

		return this.isFavourited;
	}

	public boolean isRetweeted() {

		return this.isRetweeted;
	}

	public static Tweet fromJSON(JSONObject jsonObject) {
		Tweet tweet = new Tweet();

		try {
			tweet.id = jsonObject.getLong("id");
			tweet.body = jsonObject.getString("text");
			tweet.isFavourited = jsonObject.getBoolean("favorited");
			tweet.isRetweeted = jsonObject.getBoolean("retweeted");
			SimpleDateFormat format = new SimpleDateFormat(
					"EEE MMM dd HH:mm:ss Z yyyy", Locale.ENGLISH);

			tweet.timestamp = format.parse(jsonObject.getString("created_at"));
			tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		} catch (ParseException e) {
			Log.e(TAG, "Parsing problem for timestamp");
			e.printStackTrace();
		}
		return tweet;
	}

	public static ArrayList<Tweet> fromJSON(JSONArray jsonArray) {
		ArrayList<Tweet> tweets = new ArrayList<Tweet>();
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject tweetJson = null;
			try {
				tweetJson = jsonArray.getJSONObject(i);
			} catch (JSONException e) {
				e.printStackTrace();
				continue;
			}
			Tweet tweet = Tweet.fromJSON(tweetJson);
			if (tweet != null) {
				tweets.add(tweet);
			}

		}
		return tweets;
	}

	public static long getMaxId(ArrayList<Tweet> tweets) {
		Long least = Long.MAX_VALUE;
		for (int i = 0; i < tweets.size(); i++) {
			if (tweets.get(i).getId() < least) {
				least = tweets.get(i).getId();
			}

		}
		return least;
	}

	public String toString(){
		return this.user.getName() +" tweeted: "+this.body;
	}
}
