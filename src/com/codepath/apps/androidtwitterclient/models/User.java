package com.codepath.apps.androidtwitterclient.models;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
	protected JSONObject jsonObject;

	public String getName() {
		String name = null;
		try {
			name = jsonObject.getString("name");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return name;
	}

	public long getId() {
		long id = 0;
		try {
			id = jsonObject.getLong("id");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return id;
	}

	public String getScreenName() {
		String screenName = null;
		try {
			screenName = jsonObject.getString("screen_name");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return screenName;
	}

	public String getProfileImageUrl() {
		String imageUrl = null;
		try {
			imageUrl = jsonObject.getString("profile_image_url");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return imageUrl;

	}

	public String getProfileBackgroundImageUrl() {
		String imageUrl = null;
		try {
			imageUrl = jsonObject.getString("profile_background_image_url");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return imageUrl;
	}

	public int getNumTweets() {
		int numTweets = 0;
		try {
			numTweets = jsonObject.getInt("statuses_count");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return numTweets;
	}

	public int getFollowersCount() {
		int followersCount = 0;
		try {
			followersCount = jsonObject.getInt("followers_count");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return followersCount;
	}

	public int getFriendsCount() {
		int friendsCount = 0;
		try {
			friendsCount = jsonObject.getInt("friends_count");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return friendsCount;

	}

	public static User fromJSON(JSONObject jsonObject) {
		User user = new User();
		try {
			user.jsonObject = jsonObject;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}
}
