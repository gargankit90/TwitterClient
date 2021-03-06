package com.androidlearning.twitter.models;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "users")
public class User extends Model implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "user_id")
	private Integer userId;

	@Column(name = "name")
	private String name;

	@Column(name = "location")
	private String location;

	@Column(name = "user_image")
	private String userProfileImage;

	@Column(name = "screen_name")
	String screenName;
	
	@Column(name = "followers_count")
	String followersCount;

	@Column(name = "following")
	String following;

	@Column(name = "tagline")
	String tagLine;

	public String getFollowersCount() {
		return followersCount;
	}

	public String getFollowing() {
		return following;
	}

	public String getTagLine() {
		return tagLine;
	}

	public void setFollowersCount(String followersCount) {
		this.followersCount = followersCount;
	}

	public void setFollowing(String following) {
		this.following = following;
	}

	public void setTagLine(String tagLine) {
		this.tagLine = tagLine;
	}

	public User() {}

	public User(String name, String location) {
		this.name = name;
		this.location = location;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getProfileUserImage() {
		return userProfileImage;
	}

	public void setProfileUserImage(String userProfileImage) {
		this.userProfileImage = userProfileImage;
	}
	
	public static User parse(JSONObject json) {
		User user = new User();
		try {
			user.userId = json.getInt("id");
			user.name = json.getString("name");
			user.location = json.getString("location");
			user.userProfileImage = json.getString("profile_image_url");
			user.screenName = json.getString("screen_name");
			user.followersCount =json.getString("followers_count");
			user.following = json.getString("friends_count");
			user.tagLine = json.getString("description");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return user;
	}
}