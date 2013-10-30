/**
 * 
 */
package com.codepath.apps.androidtwitterclient;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.androidtwitterclient.models.Tweet;
import com.codepath.apps.androidtwitterclient.models.User;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * @author neha
 * 
 */
public class TweetAdapter extends ArrayAdapter<Tweet> {
	Context c;
	public TweetAdapter(Context context, List<Tweet> tweets) {
		
		super(context, 0, tweets);
		c = context;
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
		imageView.setTag(tweet.getUser());
		ImageLoader.getInstance().displayImage(
				tweet.getUser().getProfileImageUrl(), imageView);
		imageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(getContext(), "Toasting!", Toast.LENGTH_SHORT).show();
				Intent i = new Intent(c,ProfileActivity.class);
				User user = (User) v.findViewById(R.id.ivProfile).getTag();
				i.putExtra("profileId", user);
				c.startActivity(i);
				
				
			}
		});

		TextView name = (TextView) view.findViewById(R.id.tvName);
		String formattedName = "<b>" + tweet.getUser().getName() + "    </b>"
				+ "<small><font color='#FF00AA'>@" + tweet.getUser().getScreenName()
				+ "</font></small>";
		name.setText(Html.fromHtml(formattedName));
		TextView body = (TextView) view.findViewById(R.id.tvBody);
		String formattedBody = "<font color=#565051>" + tweet.getBody() + "</font>";
		body.setText(Html.fromHtml(formattedBody));
		String time = "<font color = #837E7C><small>" +DateUtils.getRelativeDateTimeString(getContext(), tweet
				.getTimestamp().getTime(), DateUtils.SECOND_IN_MILLIS,
				DateUtils.WEEK_IN_MILLIS, 0) +"</small></font>";
		TextView timestamp = (TextView) view.findViewById(R.id.tvTimestamp);
		timestamp.setText(Html.fromHtml(time));
		return view;

	}
}
