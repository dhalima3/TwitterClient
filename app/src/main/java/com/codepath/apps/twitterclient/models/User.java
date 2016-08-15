package com.codepath.apps.twitterclient.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;

@Parcel
public class User {
    String name;
    long userId;
    String screenName;
    String profileImageUrl;
    String tagline;
    int followersCount;
    int followingsCount;

    public User() {

    }

    public static User fromJson(JSONObject json) {
        User user = new User();
        try {
            user.name = json.getString("name");
            user.userId = json.getLong("id");
            user.screenName = json.getString("screen_name");
            user.profileImageUrl = json.getString("profile_image_url");
            user.tagline = json.getString("description");
            user.followersCount = json.getInt("followers_count");
            user.followingsCount = json.getInt("friends_count");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return user;
    }

    public static ArrayList<User> fromJsonArray(JSONArray jsonArray) {
        ArrayList<User> users = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject userJson = jsonArray.getJSONObject(i);
                User user = User.fromJson(userJson);
                if (user != null) {
                    users.add(user);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }
        }
        return users;
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

    public String getTagline() {
        return tagline;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public int getFriendsCount() {
        return followingsCount;
    }
}
