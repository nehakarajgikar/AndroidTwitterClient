package com.codepath.apps.androidtwitterclient;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
	public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change
																																							// this
	public static final String REST_URL = "https://api.twitter.com/1.1"; // Change
																																				// this,
																																				// base
																																				// API
																																				// URL
	public static final String REST_CONSUMER_KEY = "2ehHGscfNZa17LSlE6EBA"; // Change
																																					// this
	public static final String REST_CONSUMER_SECRET = "daqFYOWA1TZh0KobxPnqXQVggL3J0xxVDV8NiTL8Cw"; // Change
																																																	// this
	public static final String REST_CALLBACK_URL = "oauth://ntwitterapp"; // Change
																																				// this
																																				// (here
																																				// and
																																				// in
																																				// manifest)

	public TwitterClient(Context context) {
		super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY,
				REST_CONSUMER_SECRET, REST_CALLBACK_URL);
	}

	public void getHomeTimeline(long maxId, AsyncHttpResponseHandler handler) {
		RequestParams params = null;
		if (maxId != 0) {
			params = new RequestParams();
			maxId = maxId - 1;
			params.put("max_id", "" + maxId);
		}
		String url = getApiUrl("statuses/home_timeline.json");
		client.get(url, params, handler);
	}

	public void getUserTimeline(long maxId, long userId,
			AsyncHttpResponseHandler handler) {
		RequestParams params = new RequestParams();
		if (maxId != 0) {
			// params =
			maxId = maxId - 1;
			params.put("max_id", "" + maxId);
		}
		params.put("user_id", "" + userId);
		String url = getApiUrl("statuses/user_timeline.json");
		client.get(url, params, handler);
	}

	public void getCredentials(AsyncHttpResponseHandler handler) {
		String url = getApiUrl("account/verify_credentials.json");
		client.get(url, null, handler);
	}

	public void postTweet(String tweet, AsyncHttpResponseHandler handler) {
		String url = getApiUrl("statuses/update.json");
		RequestParams requestParams = new RequestParams();
		requestParams.put("status", tweet);
		client.post(url, requestParams, handler);
	}

	public void getMentionsTimeline(long maxId, AsyncHttpResponseHandler handler) {
		String url = getApiUrl("statuses/mentions_timeline.json");
		RequestParams params = null;
		if (maxId != 0) {
			params = new RequestParams();
			maxId = maxId - 1;
			params.put("max_id", "" + maxId);
		}
		client.get(url, params, handler);

	}

	// CHANGE THIS
	// DEFINE METHODS for different API endpoints here
	public void getInterestingnessList(AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("?nojsoncallback=1&method=flickr.interestingness.getList");
		// Can specify query string params directly or through RequestParams.
		RequestParams params = new RequestParams();
		params.put("format", "json");
		client.get(apiUrl, params, handler);
	}

	public void retweet(long id, AsyncHttpResponseHandler handler) {
		String url = getApiUrl("statuses/retweet/:id.json");
		RequestParams requestParams = new RequestParams();
		requestParams.put("id", ""+id);
		client.post(url, requestParams, handler);
		
	}



	/*
	 * 1. Define the endpoint URL with getApiUrl and pass a relative path to the
	 * endpoint i.e getApiUrl("statuses/home_timeline.json"); 2. Define the
	 * parameters to pass to the request (query or body) i.e RequestParams params
	 * = new RequestParams("foo", "bar"); 3. Define the request method and make a
	 * call to the client i.e client.get(apiUrl, params, handler); i.e
	 * client.post(apiUrl, params, handler);
	 */
}