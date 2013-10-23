package com.codepath.apps.androidtwitterclient.models;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

public class User implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7366615661536349559L;
	protected JSONObject jsonObject;
	private String name;
	private long id;
	private String screenName;
	private String profileImageUrl;
	private String profileBackgroundImageUrl;
	private int numTweets;
	private int followersCount;
	private int friendsCount;
	
	public String getName() {
//		String name = null;
		try {
			name = jsonObject.getString("name");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return name;
	}

	public long getId() {
//		long id = 0;
		try {
			id = jsonObject.getLong("id");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return id;
	}

	public String getScreenName() {
//		String screenName = null;
		try {
			screenName = jsonObject.getString("screen_name");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return screenName;
	}

	public String getProfileImageUrl() {
//		String imageUrl = null;
		try {
			profileImageUrl = jsonObject.getString("profile_image_url");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return profileImageUrl;

	}

	public String getProfileBackgroundImageUrl() {
		String imageUrl = null;
		try {
			profileBackgroundImageUrl = jsonObject.getString("profile_background_image_url");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return profileBackgroundImageUrl;
	}

	public int getNumTweets() {
//		int numTweets = 0;
		try {
			numTweets = jsonObject.getInt("statuses_count");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return numTweets;
	}

	public int getFollowersCount() {
//		int followersCount = 0;
		try {
			followersCount = jsonObject.getInt("followers_count");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return followersCount;
	}

	public int getFriendsCount() {
//		int friendsCount = 0;
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
	
	public String toString(){
		return this.getName()+"  "+this.getScreenName();
	}
}
