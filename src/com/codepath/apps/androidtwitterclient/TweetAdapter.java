/**
 * 
 */
package com.codepath.apps.androidtwitterclient;

import java.util.List;

import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.androidtwitterclient.models.Tweet;
import com.codepath.apps.androidtwitterclient.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * @author neha
 * 
 */
public class TweetAdapter extends ArrayAdapter<Tweet> {
	Context c;
	public final String TAG = "TWITTER";

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
		imageView.setTag(tweet);
		ImageLoader.getInstance().displayImage(
				tweet.getUser().getProfileImageUrl(), imageView);
		imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(getContext(), "Toasting!", Toast.LENGTH_SHORT).show();
				Intent i = new Intent(c, ProfileActivity.class);
				Tweet tweet = (Tweet) v.findViewById(R.id.ivProfile).getTag();
				User user = tweet.getUser();
				i.putExtra("profileId", user);
				c.startActivity(i);

			}
		});
//		view.setOnLongClickListener(new OnLongClickListener() {
//
//			@Override
//			public boolean onLongClick(View v) {
//
//				Builder builder = new AlertDialog.Builder(c);
//				builder.setMessage("More options");
//				LinearLayout linearLayout = new LinearLayout(c);
//				LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,
//						LayoutParams.WRAP_CONTENT);
//				linearLayout.setLayoutParams(params);
//
//				Button btnRetweet = new Button(c);
//				Tweet tweet = (Tweet) v.findViewById(R.id.ivProfile).getTag();
//				v.setTag(tweet);
////				btnRetweet.setTag(tweet);
//				btnRetweet.setLayoutParams(params);
//				btnRetweet.setVisibility(Button.VISIBLE);
//				btnRetweet.setText("Retweet");
//				btnRetweet.setOnClickListener(new OnClickListener() {
//
//					@Override
//					public void onClick(View v) {
//						Tweet tweet = (Tweet) v.getTag();
//						Log.i(TAG,"what's tweet: "+tweet);
//						TwitterClientApp.getRestClient().retweet(tweet.getId(),
//								new JsonHttpResponseHandler() {
//
//									@Override
//									public void onSuccess(JSONObject jsonObject) {
//										// TODO Auto-generated method stub
//										Toast.makeText(c, "retweeted", Toast.LENGTH_SHORT).show();
//										Log.i(TAG, jsonObject.toString());
//									}
//								});
//
//					}
//				});
//
//				Button btnFavorite = new Button(c);
//				btnFavorite.setLayoutParams(params);
//				btnFavorite.setVisibility(Button.VISIBLE);
//				btnFavorite.setText("Favourite");
//
//				Button btnReplyTo = new Button(c);
//				btnReplyTo.setLayoutParams(params);
//				btnReplyTo.setVisibility(Button.VISIBLE);
//				btnReplyTo.setText("Reply to");
//
//				linearLayout.addView(btnRetweet);
//				linearLayout.addView(btnFavorite);
//				linearLayout.addView(btnReplyTo);
//
//				AlertDialog dialog = builder.create();
//				dialog.setView(linearLayout);
//				dialog.show();
//
//				// LayoutInflater inflator = (LayoutInflater) getContext()
//				// .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//				// v = inflator.inflate(R.layout.tweet_options, null);
//
//				return false;
//			}
//		});

		TextView name = (TextView) view.findViewById(R.id.tvName);
		String formattedName = "<b>" + tweet.getUser().getName() + "    </b>"
				+ "<small><font color='#FF00AA'>@" + tweet.getUser().getScreenName()
				+ "</font></small>";
		name.setText(Html.fromHtml(formattedName));
		TextView body = (TextView) view.findViewById(R.id.tvBody);
		String formattedBody = "<font color=#565051>" + tweet.getBody() + "</font>";
		body.setText(Html.fromHtml(formattedBody));
		String time = "<font color = #837E7C><small>"
				+ DateUtils.getRelativeDateTimeString(getContext(), tweet
						.getTimestamp().getTime(), DateUtils.SECOND_IN_MILLIS,
						DateUtils.WEEK_IN_MILLIS, 0) + "</small></font>";
		TextView timestamp = (TextView) view.findViewById(R.id.tvTimestamp);
		timestamp.setText(Html.fromHtml(time));
		return view;

	}
}
