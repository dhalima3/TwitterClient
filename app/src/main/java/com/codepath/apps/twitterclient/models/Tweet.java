package com.codepath.apps.twitterclient.models;

import org.json.JSONException;
import org.json.JSONObject;

// Parse the JSON + store the data, encapsulate static logic or display logic
public class Tweet {
    private String body;
    private long tweetId;
    private User user;
    private String createdAt;

    public static Tweet fromJson(JSONObject jsonObject) {
        Tweet tweet = new Tweet();
        try {
            tweet.body = jsonObject.getString("text");
            tweet.tweetId = jsonObject.getLong("id");
            tweet.createdAt = jsonObject.getString("created_at");
            tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return tweet;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public long getTweetId() {
        return tweetId;
    }

    public String getBody() {
        return body;
    }

    public User getUser() {
        return user;
    }
}
