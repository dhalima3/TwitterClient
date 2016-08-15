package com.codepath.apps.twitterclient.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.codepath.apps.twitterclient.app.TwitterApplication;
import com.codepath.apps.twitterclient.network.TwitterClient;
import com.codepath.apps.twitterclient.models.Tweet;
import com.codepath.apps.twitterclient.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;

import cz.msebera.android.httpclient.Header;

public class MentionsTimelineFragment extends TweetsListFragment {
    private TwitterClient client;
    private LinearLayoutManager linearLayoutManager;
    private User loggedInUser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        client = TwitterApplication.getRestClient();
        linearLayoutManager = new LinearLayoutManager(getActivity());
        populateTimeline(-1);
    }

    protected void populateTimeline(long maxId) {
        client.getMentionsTimeline(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                Log.d("DEBUG", json.toString());
                addAllAndReturnMaxId(Tweet.fromJsonArray(json));
                //Deserialize Json
                //Create models and add them to adapter
                //load the model data into listview
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable,
                                  JSONArray errorResponse) {
                Log.d("DEBUG", errorResponse.toString());
            }
        });
        //TODO put maxId in call
    }
}
