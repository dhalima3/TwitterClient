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
import com.codepath.apps.twitterclient.TwitterApplication;
import com.codepath.apps.twitterclient.TwitterClient;
import com.codepath.apps.twitterclient.models.Tweet;
import com.codepath.apps.twitterclient.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class CreateTweetFragment extends DialogFragment {

    User currentUser;
    private ImageButton btnClose;
    private ImageView ivProfilePicture;
    private TextView tvName;
    private TextView tvUserName;
    private EditText etTweetBody;
    private Button btnCreateTweet;
    private TextView tvTweetCharacterCount;
    private TwitterClient client;

    public CreateTweetFragment() {
        //Required empty constructor
    }

    public interface CreateTweetFragmentListener {
        void onFinishCreateTweetFragment(Tweet tweet);
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
        client = TwitterApplication.getRestClient();
        return inflater.inflate(R.layout.fragment_compose_tweet, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        User user = (User) getArguments().getSerializable("user");
        ivProfilePicture = (ImageView) view.findViewById(R.id.ivProfilePicture);
        tvName = (TextView) view.findViewById(R.id.tvName);
        tvUserName = (TextView) view.findViewById(R.id.tvUserName);
        etTweetBody = (EditText) view.findViewById(R.id.etTweetBody);

        if (user.getProfileImageUrl() != null) {
            Picasso.with(getContext()).load(user.getProfileImageUrl()).into(ivProfilePicture);
        }
        tvName.setText(user.getName());
        tvUserName.setText(user.getScreenName());

        btnClose = (ImageButton) view.findViewById(R.id.btnClose);
        btnCreateTweet = (Button) view.findViewById(R.id.btnCreateTweet);
        btnCreateTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendTweet();
            }
        });
    }

    public void sendTweet() {
        String statusUpdate = etTweetBody.getText().toString();
        client.postStatusUpdate(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                CreateTweetFragmentListener listener = (CreateTweetFragmentListener) getActivity();
                listener.onFinishCreateTweetFragment(Tweet.fromJson(response));
                dismiss();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        }, statusUpdate);
    }

}
