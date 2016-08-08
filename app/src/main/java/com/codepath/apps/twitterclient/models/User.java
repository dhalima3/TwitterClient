package com.codepath.apps.twitterclient.models;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

@Parcel
public class User {
    String name;
    long userId;
    String screenName;
    String profileImageUrl;

    public User() {

    }

    public static User fromJson(JSONObject json) {
        User user = new User();
        try {
            user.name = json.getString("name");
            user.userId = json.getLong("id");
            user.screenName = json.getString("screen_name");
            user.profileImageUrl = json.getString("profile_image_url");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return user;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public String getScreenName() {
        return screenName;
    }

    public long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }
}
