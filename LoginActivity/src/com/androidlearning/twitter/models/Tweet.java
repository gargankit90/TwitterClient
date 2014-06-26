package com.androidlearning.twitter.models;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.text.format.DateUtils;

public class Tweet implements Serializable{

	private static final long serialVersionUID = 1L;

	String tweet;
	String date;
	String id;
	User user;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTweet() {
		return tweet;
	}

	public void setTweet(String tweet) {
		this.tweet = tweet;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getRelativeDate(){
		String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
		SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
		sf.setLenient(true);
	 
		String relativeDate = "";
		try {
			long dateMillis = sf.parse(this.date).getTime();
			relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
					System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	 
		return relativeDate;
		
	}
	
	public static Tweet parseJsonObject(JSONObject json) {
		Tweet tweet = new Tweet();
		try {
			tweet.tweet = json.getString("text");
			tweet.date = json.getString("created_at");
			tweet.user = User.parse(json.getJSONObject("user"));
			tweet.id = json.getString("id_str");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return tweet;
	}

	public static ArrayList<Tweet> parseJsonArray(JSONArray jsonArray) {
		ArrayList<Tweet> tweets = new ArrayList<Tweet>();

		try {
			for (int i = 0; i < jsonArray.length(); i++) {
				tweets.add(Tweet.parseJsonObject(jsonArray.getJSONObject(i)));
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return tweets;
	}
}