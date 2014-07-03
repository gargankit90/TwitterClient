package com.androidlearning.twitter;

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
public class TwitterRestClient extends OAuthBaseClient {
    public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
    public static final String REST_URL = "https://api.twitter.com/1.1"; // Change this, base API URL
    public static final String REST_CONSUMER_KEY = "YUITHBvHEXzLOBZeIDsxVpJdc";       // Change this
    public static final String REST_CONSUMER_SECRET = "d7ez5Z7vOuejO3ddxNYpSBuLpRaBv7CKIwSWUGF0qoBEuKH3Pc"; // Change this
    public static final String REST_CALLBACK_URL = "oauth://cprest"; // Change this (here and in manifest)
    
    public TwitterRestClient(Context context) {
        super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
    }
    
    public void getTweets(AsyncHttpResponseHandler handler, RequestParams params) {
        String apiUrl = getApiUrl("statuses/home_timeline.json");
        client.get(apiUrl, params, handler);
    }
    
    public void getMyInfo(AsyncHttpResponseHandler handler) {
    	String apiUrl = getApiUrl("account/verify_credentials.json");
    	client.get(apiUrl, handler);
    }
    
    public void postTweet(AsyncHttpResponseHandler handler, RequestParams params) {
    	String apiUrl = getApiUrl("statuses/update.json");
    	client.post(apiUrl, params, handler);
    }

	public void getMentionTimeLine(AsyncHttpResponseHandler handler, RequestParams params) {
		// TODO Auto-generated method stub
        String apiUrl = getApiUrl("statuses/mentions_timeline.json");
        client.get(apiUrl, params, handler);
	}
    
	public void getUserTimeLine(String screenName,AsyncHttpResponseHandler handler, RequestParams params) {
		params.put("screen_name", screenName);
        String apiUrl = getApiUrl("statuses/user_timeline.json");
        client.get(apiUrl,params, handler);
	}
	
	public void getUserInfoByScreenName(String screenName, AsyncHttpResponseHandler handler) {
		RequestParams params = new RequestParams();
		params.put("screen_name", screenName);
		String apiUrl = getApiUrl("users/show.json");
		client.get(apiUrl, params, handler);
	}
    
    /* 1. Define the endpoint URL with getApiUrl and pass a relative path to the endpoint
     * 	  i.e getApiUrl("statuses/home_timeline.json");
     * 2. Define the parameters to pass to the request (query or body)
     *    i.e RequestParams params = new RequestParams("foo", "bar");
     * 3. Define the request method and make a call to the client
     *    i.e client.get(apiUrl, params, handler);
     *    i.e client.post(apiUrl, params, handler);
     */
}