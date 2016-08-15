package com.codepath.apps.twitterclient.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.codepath.apps.twitterclient.R;
import com.codepath.apps.twitterclient.adapters.FollowersAdapter;
import com.codepath.apps.twitterclient.app.TwitterApplication;
import com.codepath.apps.twitterclient.models.User;
import com.codepath.apps.twitterclient.network.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class FollowersActivity extends AppCompatActivity {

    List<User> users;
    TwitterClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followers);

        users = new ArrayList<>();
        client = TwitterApplication.getRestClient();

        RecyclerView rvFollowers = (RecyclerView) findViewById(R.id.rvFollowers);
        final FollowersAdapter adapter = new FollowersAdapter(this, users);
        rvFollowers.setAdapter(adapter);
        rvFollowers.setLayoutManager(new LinearLayoutManager(this));

        String screenName = getIntent().getStringExtra("screen_name");
        String type = getIntent().getStringExtra("type");
        if (type.equals("users")) {
            getFollowers(adapter, screenName);
        } else if (type.equals("followings")) {
            getFriends(adapter, screenName);
        }
    }

    private void getFollowers(final FollowersAdapter adapter, String screenName) {
        client.getFollowers(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("DEBUG", response.toString());
                try {
                    users.addAll(User.fromJsonArray((JSONArray) response.get("users")));
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG", errorResponse.toString());
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        }, screenName);
    }

    private void getFriends(final FollowersAdapter adapter, String screenName) {
        client.getFriends(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    users.addAll(User.fromJsonArray((JSONArray) response.get("users")));
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, screenName);
    }
}
