package com.codepath.apps.twitterclient;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.codepath.apps.twitterclient.adapters.TweetsAdapter;
import com.codepath.apps.twitterclient.fragments.CreateTweetFragment;
import com.codepath.apps.twitterclient.fragments.CreateTweetFragment.CreateTweetFragmentListener;
import com.codepath.apps.twitterclient.models.Tweet;
import com.codepath.apps.twitterclient.models.User;
import com.codepath.apps.twitterclient.utils.EndlessRecyclerViewScrollListener;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class TimelineActivity extends AppCompatActivity implements CreateTweetFragmentListener {

    private TwitterClient client;
    private ArrayList<Tweet> tweets;
    private User loggedInUser;
    private TweetsAdapter tweetsArrayAdapter;
    private long maxId;
    private SwipeRefreshLayout swipeTimelineContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        RecyclerView rvTweets = (RecyclerView) findViewById(R.id.rvTweets);

        configureSwipeTimelineContainer();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarTimeline);
        setSupportActionBar(toolbar);

        tweets = new ArrayList<>();
        tweetsArrayAdapter = new TweetsAdapter(this, tweets);
        rvTweets.setAdapter(tweetsArrayAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvTweets.setLayoutManager(linearLayoutManager);
        rvTweets.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                populateTimeline(maxId);
            }
        });

        client = TwitterApplication.getRestClient();
        populateTimeline(-1);
        getLoggedInUser();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_timeline, menu);
        return true;
    }

    @Override
    public void onFinishCreateTweetFragment(Tweet tweet) {
        tweets.add(0, tweet);
        tweetsArrayAdapter.notifyItemChanged(0);
    }

    public void onComposeAction(MenuItem item) {
        FragmentManager fm = getSupportFragmentManager();
        CreateTweetFragment createTweetFragment = CreateTweetFragment.newInstance(loggedInUser);
        createTweetFragment.show(fm, "fragment_compose_tweet");
    }

    private void populateTimeline(long maxId) {
        client.getHomeTimeline(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                Log.d("DEBUG", json.toString());
                //Deserialize Json
                //Create models and add them to adapter
                //load the model data into listview
                int previousSize = tweets.size();
                tweets.addAll(Tweet.fromJsonArray(json));
                tweetsArrayAdapter.notifyItemRangeInserted(previousSize, tweets.size());
                TimelineActivity.this.maxId = tweets.get(tweets.size()-1).getTweetId();
                Log.d("DEBUG", tweetsArrayAdapter.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable,
                                  JSONArray errorResponse) {
                Log.d("DEBUG", errorResponse.toString());
            }
        }, this.maxId);
    }

    private void getLoggedInUser() {
        client.getLoggedInUser(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                loggedInUser = User.fromJson(response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable,
                                  JSONObject errorResponse) {
                Log.d("DEBUG", errorResponse.toString());
            }
        });
    }

    public void onClose(View view) {
    }

    private void configureSwipeTimelineContainer() {
        swipeTimelineContainer = (SwipeRefreshLayout) findViewById(R.id.swipeTimelineContainer);
        swipeTimelineContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchTimelineAsync(-1);
            }
        });
        swipeTimelineContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    private void fetchTimelineAsync(int maxId) {
        client.getHomeTimeline(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                tweetsArrayAdapter.clear();
                tweetsArrayAdapter.addAll(Tweet.fromJsonArray(response));
                swipeTimelineContainer.setRefreshing(false);
            }

            public void onFailure(Throwable e) {
                Log.d("DEBUG", "Fetch timeline error: " + e.toString());
            }
        }, maxId);
    }
}
