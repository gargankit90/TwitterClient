package com.androidlearning.twitter.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidlearning.twitter.ProfileActivity;
import com.androidlearning.twitter.R;
import com.androidlearning.twitter.models.Tweet;
import com.nostra13.universalimageloader.core.ImageLoader;

public class TweetAdapter extends ArrayAdapter<Tweet> {

	public TweetAdapter(Context context, List<Tweet> tweets) {
		super(context, R.layout.tweet_item, tweets);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Tweet tweet = getItem(position);
		final String screenName = tweet.getUser().getScreenName();
		View view = convertView;
		if (view == null) {
			LayoutInflater inflator = LayoutInflater.from(getContext());
			view = inflator.inflate(R.layout.tweet_item, null);
		}
		ImageView userProfileImage = (ImageView) view
				.findViewById(R.id.user_image);
		ImageLoader.getInstance().displayImage(
				tweet.getUser().getProfileUserImage(), userProfileImage);
		userProfileImage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getContext(),ProfileActivity.class);
				i.putExtra("screen_name", screenName);
				getContext().startActivity(i);
			}
		});

		TextView userName = (TextView) view.findViewById(R.id.tvName);
		TextView userTweet = (TextView) view.findViewById(R.id.tvBody);
		TextView tweetTimestamp = (TextView) view.findViewById(R.id.tvTimestamp);

//		String formattedName = "<b>" + tweet.getUser().getName() + "</b>"
//				+ "<small><font color ='#777777>@" + tweet.getUser().getScreenName()
//				+ "</small></font>";

		String formattedName = "<b>" + tweet.getUser().getName() + "</b>" + " @" + tweet.getUser().getScreenName();
		
		userName.setText(Html.fromHtml(formattedName));
		userTweet.setText(Html.fromHtml(tweet.getTweet()));
		tweetTimestamp.setText(tweet.getRelativeDate());
		return view;
	}
}
