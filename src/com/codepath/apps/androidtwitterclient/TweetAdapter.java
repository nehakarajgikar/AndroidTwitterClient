/**
 * 
 */
package com.codepath.apps.androidtwitterclient;

import java.util.List;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.androidtwitterclient.models.Tweet;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * @author neha
 * 
 */
public class TweetAdapter extends ArrayAdapter {

	public TweetAdapter(Context context, List<Tweet> tweets) {
		super(context, 0, tweets);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null) {
			LayoutInflater inflator = (LayoutInflater) getContext().getSystemService(
					Context.LAYOUT_INFLATER_SERVICE);
			view = inflator.inflate(R.layout.tweet_item, null);
		}

		Tweet tweet = (Tweet) getItem(position);
		ImageView imageView = (ImageView) view.findViewById(R.id.ivProfile);
		ImageLoader.getInstance().displayImage(
				tweet.getUser().getProfileImageUrl(), imageView);

		TextView name = (TextView) view.findViewById(R.id.tvName);
		String formattedName = "<b>" + tweet.getUser().getName() + "</b>"
				+ "<small><font color='#FF00AA'>@" + tweet.getUser().getScreenName()
				+ "</font></small>";
		name.setText(Html.fromHtml(formattedName));
		TextView body = (TextView) view.findViewById(R.id.tvBody);
		String formattedBody = "<font color=#565051>" + tweet.getBody() +"</font>";
		body.setText(Html.fromHtml(formattedBody));
		return view;

	}
}
