package com.codepath.apps.twitterclient.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.twitterclient.R;
import com.codepath.apps.twitterclient.models.Tweet;
import com.codepath.apps.twitterclient.models.User;
import com.codepath.apps.twitterclient.utils.ParseRelativeDate;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.ViewHolder> {

    private List<Tweet> tweets;
    private Context context;

    public TweetsAdapter(Context context, List<Tweet> tweets) {
        this.tweets = tweets;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View tweetView = inflater.inflate(R.layout.item_tweet, parent, false);

        return new ViewHolder(tweetView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Tweet tweet = tweets.get(position);
        User user = tweet.getUser();

        TextView tvUserName = holder.tvUserName;
        tvUserName.setText(user.getScreenName());

        TextView tvBody = holder.tvBody;
        tvBody.setText(tweet.getBody());

        TextView tvDate = holder.tvDate;
        tvDate.setText(ParseRelativeDate.getRelativeTimeAgo(tweet.getCreatedAt()));

        ImageView ivProfileImage = holder.ivProfileImage;
        ivProfileImage.setImageResource(android.R.color.transparent);
        Picasso.with(getContext()).load(user.getProfileImageUrl()).into(ivProfileImage);
    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }

    public Context getContext() {
        return context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvUserName;
        public TextView tvBody;
        public TextView tvDate;
        public ImageView ivProfileImage;

        public ViewHolder(View itemView) {
            super(itemView);

            tvUserName = (TextView) itemView.findViewById(R.id.tvUserName);
            tvBody = (TextView) itemView.findViewById(R.id.tvBody);
            tvDate = (TextView) itemView.findViewById(R.id.tvDate);
            ivProfileImage = (ImageView) itemView.findViewById(R.id.ivProfileImage);
        }
    }

}
