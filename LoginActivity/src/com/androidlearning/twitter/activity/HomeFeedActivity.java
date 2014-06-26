package com.androidlearning.twitter.activity;

import java.util.ArrayList;

import org.json.JSONArray;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.androidlearning.twitter.EndlessScrollListener;
import com.androidlearning.twitter.MyTwitterApp;
import com.androidlearning.twitter.R;
import com.androidlearning.twitter.adapter.TweetAdapter;
import com.androidlearning.twitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class HomeFeedActivity extends Activity {
	private String  TWEET_COUNT = "20";
	public ArrayList<Tweet> tweets = new ArrayList<Tweet>();
	public TweetAdapter tweetAdapter;
	public ListView lvTweets;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_feed);

		lvTweets = (ListView) findViewById(R.id.lvTweets);
		tweetAdapter = new TweetAdapter(getApplicationContext(), tweets);
		lvTweets.setAdapter(tweetAdapter);
		
		RequestParams params = new RequestParams();
		params.put("count", TWEET_COUNT);
		getMoreTweets(params);
		setupOnScroll();
	}

	private void setupOnScroll() {
		lvTweets.setOnScrollListener(new EndlessScrollListener() {

			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				RequestParams params = new RequestParams();
				params.put("count", TWEET_COUNT);
				Tweet t = tweetAdapter.getItem(tweetAdapter.getCount() - 1);
				params.put("max_id", t.getId());
				getMoreTweets(params);
			}
		});
	}

	private void getMoreTweets(RequestParams parameters) {
		MyTwitterApp.getRestClient().getTweets(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray jsonTweets) {
				tweetAdapter.addAll(Tweet.parseJsonArray(jsonTweets));
			}
		}, parameters);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.home_feed, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case R.id.action_compose:
			startComposeActivity();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void startComposeActivity() {
		Intent i = new Intent(this, ComposeActivity.class);
		startActivityForResult(i, 1);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		tweetAdapter.insert((Tweet) data.getSerializableExtra("tweet"),0);
	}
}