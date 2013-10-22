package com.codepath.apps.androidtwitterclient.models;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Tweet {

	private User user;
	private JSONObject jsonObject;
	
	public User getUser(){
		return this.user;
	}
	
	public long getId(){
		long id = 0;
		try {
			id = jsonObject.getLong("id");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return id;
	}
	
	public String getBody(){
		String text = null;
		try{
			text = jsonObject.getString("text");
			
		}catch(JSONException e){
			e.printStackTrace();
		}
		return text;
	}
	
	public boolean isFavourited(){
		boolean isFavourited = false;
		try {
			isFavourited = jsonObject.getBoolean("favorited");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return isFavourited;
	}
	
	public boolean isRetweeted(){
		boolean isRetweeted = false;
		try {
			isRetweeted = jsonObject.getBoolean("retweeted");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return isRetweeted;
	}
	
	public static Tweet fromJSON(JSONObject jsonObject){
		Tweet tweet = new Tweet();
		
		try {
			tweet.jsonObject = jsonObject;
			tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		return tweet;
	}
	
	public static ArrayList<Tweet> fromJSON(JSONArray jsonArray){
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
			if(tweet!=null){
				tweets.add(tweet);
			}
				
		}
		return tweets;
	}
	
	
}
