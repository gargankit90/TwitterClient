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

public class UserTimeLineFragment extends TweetsListFragment {

	private String TWEET_COUNT = "20";
	private TwitterRestClient client;
	private String screenName;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		client = MyTwitterApp.getRestClient();
		screenName = getArguments().getString("screen_name");	
	}

	

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		RequestParams params = new RequestParams();
		params.put("count", TWEET_COUNT);
		getMoreTweets(params,screenName);
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
				getMoreTweets(params,screenName);
			}
		});
	}

	private void getMoreTweets(RequestParams parameters,String screenName) {
		client.getUserTimeLine(screenName,new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray jsonTweets) {
				addAll(Tweet.parseJsonArray(jsonTweets));
			}
		},parameters);
	}
	
    public static UserTimeLineFragment newInstance(String screenName) {
        UserTimeLineFragment userTimeLinefragment = new UserTimeLineFragment();
        Bundle args = new Bundle();
        args.putString("screen_name", screenName);
        userTimeLinefragment.setArguments(args);
        return userTimeLinefragment;
    }
}
