package com.androidlearning.twitter.fragments;


import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.activeandroid.util.Log;
import com.androidlearning.twitter.R;
import com.androidlearning.twitter.adapter.TweetAdapter;
import com.androidlearning.twitter.models.Tweet;

public class TweetsListFragment extends Fragment {
	public ArrayList<Tweet> tweets;
	public TweetAdapter tweetAdapter;
	public ListView lvTweets;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.fragment_tweets_list, container,false);
		lvTweets = (ListView) v.findViewById(R.id.lvTweets);
		lvTweets.setAdapter(tweetAdapter);
		lvTweets.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Log.d("Item clicked position",position+"");
				if(view.getId() == R.id.user_image){
					Log.d("Image view clicked","yipeee");
				}
			}
		});
		return v;
	}
		
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		tweets= new ArrayList<Tweet>();
		tweetAdapter = new TweetAdapter(getActivity(), tweets);

	}
	
	public void addAll(ArrayList<Tweet> tweet){
		tweetAdapter.addAll(tweet);
	}

}
