package com.androidlearning.twitter.fragments;

import org.json.JSONArray;

import android.os.Bundle;
import android.view.View;

import com.androidlearning.twitter.EndlessScrollListener;
import com.androidlearning.twitter.MyTwitterApp;
import com.androidlearning.twitter.TwitterRestClient;
import com.androidlearning.twitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class MentionTimeLineFragment extends TweetsListFragment {
	private String TWEET_COUNT = "20";
	private TwitterRestClient client;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		client = MyTwitterApp.getRestClient();
		
	}

	

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		RequestParams params = new RequestParams();
		params.put("count", TWEET_COUNT);
		getMoreTweets(params);
		setupOnScroll();
		super.onViewCreated(view, savedInstanceState);
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
		client.getMentionTimeLine(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray jsonTweets) {
				addAll(Tweet.parseJsonArray(jsonTweets));
			}
		}, parameters);
	}
}
