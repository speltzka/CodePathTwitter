package com.codepath.apps.restclienttemplate.models;

import android.arch.persistence.room.Entity;
import android.media.Image;
import android.text.format.DateUtils;

import com.codepath.apps.restclienttemplate.TwitterClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;


@Parcel
@Entity
public class Tweet {
    //list out the attributes
    public String body;
    public long uid; //database id for the tweet
    public User user;
    public String createdAt;
    public String pictureUrl;
    public com.codepath.apps.restclienttemplate.models.Entity entity;
    public boolean hasEntities;

    //deserialize the JSON data
    public static Tweet fromJSON(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();

        //extract the values from JSON
        tweet.body = jsonObject.getString("text");
        tweet.uid = jsonObject.getLong("id");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));
       // tweet.pictureUrl = jsonObject.getString("url");

        JSONObject entityObject = jsonObject.getJSONObject("entities");
        if (entityObject.has("media")){
            JSONArray mediaEndpoint = entityObject.getJSONArray("media");
            if (mediaEndpoint !=null && mediaEndpoint.length() != 0){
                tweet.hasEntities=true;
                tweet.entity = com.codepath.apps.restclienttemplate.models.Entity.fromJSON(jsonObject.getJSONObject("entities"));
            }
        }

        return tweet;

    }
}
