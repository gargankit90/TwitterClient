package com.androidlearning.twitter;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidlearning.twitter.fragments.UserTimeLineFragment;
import com.androidlearning.twitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ProfileActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		Intent i = getIntent();
		String screenName = i.getStringExtra("screen_name");
		loadProfileInfo(screenName);
//		FrameLayout userTimeLineContainer = (FrameLayout) findViewById(R.id.userTimeLineContainer);
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		// Replace the container with the new fragment
		ft.replace(R.id.userTimeLineContainer,UserTimeLineFragment.newInstance(screenName));
		// or ft.add(R.id.your_placeholder, new FooFragment());
		// Execute the changes specified
		ft.commit();
	}

	private void loadProfileInfo(String screenName) {
		MyTwitterApp.getRestClient().getUserInfoByScreenName(screenName, new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(JSONObject json) {
				Log.d("user Timeline response",json+"");
				User u = User.parse(json);
				getActionBar().setTitle("@"+u.getScreenName());
				populateProfileHeader(u);
			}


		});
	}
	
	private void populateProfileHeader(User user) {
		getActionBar().setTitle("@" + user.getScreenName());

		ImageView imgView = (ImageView) findViewById(R.id.ivProfileImage);
		TextView userName = (TextView) findViewById(R.id.tvUserName);
		TextView following = (TextView) findViewById(R.id.tvUserFollowing);
		TextView follower = (TextView) findViewById(R.id.tvUserFollower);
		TextView tagline = (TextView) findViewById(R.id.tvUserTagline);

		ImageLoader.getInstance().displayImage(user.getProfileUserImage(),
				imgView);
		userName.setText(user.getName());

		following.setText(user.getFollowing() + " Following");
		follower.setText(user.getFollowersCount() + " Follower");
		tagline.setText(user.getTagLine());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile, menu);
		return true;
	}



}
