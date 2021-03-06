package com.codepath.apps.twitterclient.fragments;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;

import com.codepath.apps.twitterclient.R;
import com.codepath.apps.twitterclient.app.TwitterApplication;
import com.codepath.apps.twitterclient.models.Tweet;
import com.codepath.apps.twitterclient.network.TwitterClient;
import com.codepath.apps.twitterclient.utils.InternetCheckUtil;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;

import cz.msebera.android.httpclient.Header;

public class HomeTimelineFragment extends TweetsListFragment {

    private TwitterClient client;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        client = TwitterApplication.getRestClient();
        populateTimeline(-1);
    }

    protected void populateTimeline(long maxId) {
        if (!InternetCheckUtil.isOnline()) {
            Snackbar.make(rvTweets, R.string.internet_unavailable_message,
                    Snackbar.LENGTH_LONG).show();
            return;
        }

        client.getHomeTimeline(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                Log.d("DEBUG", json.toString());
                //Deserialize Json
                //Create models and add them to adapter
                //load the model data into listview
                TweetsListFragment.maxId = addAllAndReturnMaxId(Tweet.fromJsonArray(json));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable,
                                  JSONArray errorResponse) {
                Log.d("DEBUG", errorResponse.toString());
            }
        }, maxId);
    }
}
