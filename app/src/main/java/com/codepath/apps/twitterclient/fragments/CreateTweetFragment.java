package com.codepath.apps.twitterclient.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.twitterclient.R;
import com.codepath.apps.twitterclient.models.User;
import com.squareup.picasso.Picasso;

public class CreateTweetFragment extends DialogFragment {

    User currentUser;
    private ImageButton btnClose;
    private ImageView ivProfilePicture;
    private TextView tvName;
    private TextView tvUserName;
    private EditText etTweetBody;
    private Button btnCreateTweet;
    private TextView tvTweetCharacterCount;

    public CreateTweetFragment() {
        //Required empty constructor
    }

    public static CreateTweetFragment newInstance(User user) {
        CreateTweetFragment fragment = new CreateTweetFragment();
        Bundle args = new Bundle();
        args.putSerializable("user", user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_compose_tweet, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        User user = (User) getArguments().getSerializable("user");
        ivProfilePicture = (ImageView) view.findViewById(R.id.ivProfilePicture);
        tvName = (TextView) view.findViewById(R.id.tvName);
        tvUserName = (TextView) view.findViewById(R.id.tvUserName);

        if (user.getProfileImageUrl() != null) {
            Picasso.with(getContext()).load(user.getProfileImageUrl()).into(ivProfilePicture);
        }
        tvName.setText(user.getName());
        tvUserName.setText(user.getScreenName());

        btnClose = (ImageButton) view.findViewById(R.id.btnClose);
    }
}
