package com.androidlearning.twitter.activity;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.androidlearning.twitter.MyTwitterApp;
import com.androidlearning.twitter.R;
import com.androidlearning.twitter.models.Tweet;
import com.androidlearning.twitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ComposeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose);
		SharedPreferences prefs = this.getSharedPreferences("user_info",
				Context.MODE_PRIVATE);
		ImageView userImage = (ImageView) findViewById(R.id.userProfileImage);
		TextView userScreenName = (TextView) findViewById(R.id.etScreenName);

		Integer userId = prefs.getInt("user_id", 0);
		User user = new Select().from(User.class).where("user_id = ?" , userId).executeSingle();
		ImageLoader.getInstance().displayImage(user.getProfileUserImage(), userImage);

		userScreenName.setText(" @" + user.getScreenName());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.compose, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_post:
			postTweet();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void postTweet() {
		RequestParams params = new RequestParams();
		EditText etTweet = (EditText) findViewById(R.id.etTweet);
		params.put("status", etTweet.getText().toString());
		MyTwitterApp.getRestClient().postTweet(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject recentTweet) {
				Tweet tweet = Tweet.parseJsonObject(recentTweet);
				Intent i = new Intent();
				i.putExtra("tweet", tweet);
				setResult(RESULT_OK, i);
				Log.d("DEBUG", "Tweet Posted");
				finish();
			}

			@Override
			public void onFailure(Throwable arg0, JSONObject arg1) {
				Log.d("DEBUG", arg0.toString());
				Log.d("DEBUG", arg1.toString());
			}
		}, params);
	}
}