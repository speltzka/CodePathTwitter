package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

import static com.loopj.android.http.AsyncHttpClient.log;

public class ComposeActivity extends AppCompatActivity {

    EditText tweetPostText;
    Button postTweet;
    TwitterClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        client= TwitterApp.getRestClient(this);

        tweetPostText= (findViewById(R.id.tweetPostText));
        postTweet=findViewById(R.id.postButton);

        postTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                log.i("click2", "clickedIt");
                String text= tweetPostText.getText().toString();
                postMyTweet(text);
            }
        });
    }

    public void postMyTweet(String message) {
        client.sendTweet(message, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Tweet tweet = Tweet.fromJSON(response);
                    Intent data = new Intent();
                    // Pass relevant data back as a result
                    data.putExtra("tweet", (Parcelable) tweet);
                    // Activity finished ok, return the data
                    setResult(RESULT_OK, data); // set result code and bundle data for response
                    finish(); // closes the activity, pass data to parent
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

