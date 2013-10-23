package com.codepath.apps.androidtwitterclient.models;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7366615661536349559L;
	private String name;
	private long id;
	private String screenName;
	private String profileImageUrl;
	private String profileBackgroundImageUrl;
	private int numTweets;
	private int followersCount;
	private int friendsCount;

	public String getName() {
		return this.name;
	}

	public long getId() {

		return this.id;
	}

	public String getScreenName() {

		return this.screenName;
	}

	public String getProfileImageUrl() {
		return this.profileImageUrl;

	}

	public String getProfileBackgroundImageUrl() {

		return this.profileBackgroundImageUrl;
	}

	public int getNumTweets() {
		return this.numTweets;
	}

	public int getFollowersCount() {
		return this.followersCount;
	}

	public int getFriendsCount() {
		return this.friendsCount;

	}

	public static User fromJSON(JSONObject jsonObject) {
		User user = new User();
		try {
			user.id = jsonObject.getLong("id");
			user.name = jsonObject.getString("name");
			user.screenName = jsonObject.getString("screen_name");
			user.followersCount = jsonObject.getInt("followers_count");
			user.friendsCount = jsonObject.getInt("friends_count");
			user.numTweets = jsonObject.getInt("statuses_count");
			user.profileImageUrl = jsonObject.getString("profile_image_url");
			user.profileBackgroundImageUrl = jsonObject
					.getString("profile_background_image_url");
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		return user;
	}

	public String toString() {
		return this.getName() + "  " + this.getScreenName();
	}
}
