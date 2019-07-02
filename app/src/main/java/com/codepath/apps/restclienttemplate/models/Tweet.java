package com.codepath.apps.restclienttemplate.models;

import android.text.format.DateUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class Tweet {
    //list out the attributes
    public String body;
    public long uid; //database id for the tweet
    public User user;
    public String createdAt;

    //deserialize the JSON data
    public static Tweet fromJSON(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();

        //extract the values from JSON
        tweet.body = jsonObject.getString("text");
        tweet.uid = jsonObject.getLong("id");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));
        return tweet;

    }
}
