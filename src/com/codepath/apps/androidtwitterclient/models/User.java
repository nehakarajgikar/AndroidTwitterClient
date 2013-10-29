package com.codepath.apps.androidtwitterclient.models;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "Users")
public class User extends Model implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7366615661536349559L;
	
	@Column(name = "Name")
	public String name;
	
	@Column(name = "userId")
	public long userId;
	
	@Column(name = "ScreenName")
	public String screenName;
	
	@Column(name = "ProfileImageUrl")
	public String profileImageUrl;
	
	@Column(name = "ProfileBackgroundImageUrl")
	public String profileBackgroundImageUrl;
	
	@Column(name = "NumberOfTweets")
	public int numTweets;
	
	@Column(name = "FollowersCount")
	public int followersCount;
	
	@Column(name = "FriendsCount")
	public int friendsCount;
	
	@Column(name = "Tagline")
	public String tagline;


	public User(){
		super();
	}
	public String getName() {
		return this.name;
	}

	public long getUserId() {

		return this.userId;
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

	public String getTagline() {
		return this.tagline;
	}

	// public int getFollowingCount(){
	// return this.followingCount;
	// }

	public static User fromJSON(JSONObject jsonObject) {
		User user = new User();
		try {
			user.userId = jsonObject.getLong("id");
			user.name = jsonObject.getString("name");
			user.screenName = jsonObject.getString("screen_name");
			user.followersCount = jsonObject.getInt("followers_count");
			// user.followingCount = jsonObject.getInt("following");
			user.friendsCount = jsonObject.getInt("friends_count");
			user.numTweets = jsonObject.getInt("statuses_count");
			user.profileImageUrl = jsonObject.getString("profile_image_url");
			user.profileBackgroundImageUrl = jsonObject
					.getString("profile_background_image_url");
			user.tagline = jsonObject.getString("description");
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
