package com.codepath.apps.twitterclient.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.apps.twitterclient.R;
import com.codepath.apps.twitterclient.adapters.TweetsAdapter;
import com.codepath.apps.twitterclient.models.Tweet;
import com.codepath.apps.twitterclient.utils.EndlessRecyclerViewScrollListener;

import java.util.ArrayList;
import java.util.List;

public abstract class TweetsListFragment extends Fragment {

    protected ArrayList<Tweet> tweets;
    protected TweetsAdapter tweetsArrayAdapter;
    protected RecyclerView rvTweets;
    protected SwipeRefreshLayout swipeTimelineContainer;
    private LinearLayoutManager linearLayoutManager;

    abstract protected void populateTimeline();

    //inflation logic
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tweets_list, parent, false);
        rvTweets = (RecyclerView) view.findViewById(R.id.rvTweets);
        rvTweets.setAdapter(tweetsArrayAdapter);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        rvTweets.setLayoutManager(linearLayoutManager);
        rvTweets.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                populateTimeline();
            }
        });

        configureSwipeTimelineContainer(view);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tweets = new ArrayList<>();
        tweetsArrayAdapter = new TweetsAdapter(getActivity(), tweets);
    }

    public long addAllAndReturnMaxId(List<Tweet> tweets) {
        int previousSize = tweets.size();
        tweetsArrayAdapter.addAll(tweets);
        tweetsArrayAdapter.notifyItemRangeInserted(previousSize, tweets.size());
        Log.d("DEBUG", tweetsArrayAdapter.toString());
        long maxId = tweets.get(tweets.size()-1).getTweetId();
        return maxId;
    }

    private void configureSwipeTimelineContainer(View view) {
        swipeTimelineContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeTimelineContainer);
        swipeTimelineContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                tweetsArrayAdapter.clear();
                populateTimeline();
                swipeTimelineContainer.setRefreshing(false);
            }
        });
        swipeTimelineContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }
}
