package com.codepath.apps.twitterclient.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.codepath.apps.twitterclient.models.Tweet;

import java.util.List;

public class TweetsArrayAdapter extends ArrayAdapter<Tweet> {

    public TweetsArrayAdapter(Context context, List<Tweet> tweets) {
        //TODO replace with custom template
        super(context, android.R.layout.simple_list_item_1, tweets);
    }
}
