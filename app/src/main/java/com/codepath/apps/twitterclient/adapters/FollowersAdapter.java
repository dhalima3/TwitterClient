package com.codepath.apps.twitterclient.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.twitterclient.R;
import com.codepath.apps.twitterclient.models.User;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FollowersAdapter extends RecyclerView.Adapter<FollowersAdapter.ViewHolder> {
    private List<User> mFollowers;
    private Context mContext;

    public FollowersAdapter(Context context, List<User> followers) {
        mFollowers = followers;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View followerView = inflater.inflate(R.layout.item_follower, parent, false);

        ViewHolder viewHolder = new ViewHolder(followerView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        User follower = mFollowers.get(position);

        TextView tvName = viewHolder.tvName;
        tvName.setText(follower.getName());

        TextView tvUserName = viewHolder.tvUserName;
        tvUserName.setText(follower.getScreenName());

        ImageView ivProfileImage = viewHolder.ivProfileImage;
        Picasso.with(getContext()).load(follower.getProfileImageUrl()).into(ivProfileImage);
    }

    @Override
    public int getItemCount() {
        return mFollowers.size();
    }

    public Context getContext() {
        return mContext;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;
        public TextView tvUserName;
        public ImageView ivProfileImage;

        public ViewHolder(View itemView) {
            super(itemView);

            tvName = (TextView) itemView.findViewById(R.id.tvFullName);
            tvUserName = (TextView) itemView.findViewById(R.id.tvUserName);
            ivProfileImage = (ImageView) itemView.findViewById(R.id.ivProfileImage);
        }
    }
}
