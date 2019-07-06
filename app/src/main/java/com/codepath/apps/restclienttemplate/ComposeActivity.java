package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

import static com.loopj.android.http.AsyncHttpClient.log;

public class ComposeActivity extends AppCompatActivity {

    @BindView(R.id.tweetPostText) EditText tweetPostText;
    @BindView(R.id.postButton) Button postButton;
    @BindView(R.id.charCount) TextView charCount;
    TwitterClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        ButterKnife.bind(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            String newText = extras.getString("user") + " ";
            tweetPostText.setText(newText);
        }
        client= TwitterApp.getRestClient(this);

        //this is how you set an onClickListener without Butterknife
       /* postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text= tweetPostText.getText().toString();
                postMyTweet(text);
                }
                */

            tweetPostText.addTextChangedListener(new TextWatcher() {
                int startLength = tweetPostText.length();
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) { }
                @Override
                public void afterTextChanged(Editable s)
                {
                    String newText = String.valueOf (280 - tweetPostText.length() + startLength);
                    log.i("here", newText);
                    charCount.setText(newText);
                }
            });
        }

    //Butterknife method
    @OnClick(R.id.postButton)
    public void onClick() {
        String text = tweetPostText.getText().toString();
       // showProgressBar();
        postMyTweet(text);
       // hideProgressBar();
    }

    public void postMyTweet(String message) {
        client.sendTweet(message, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Tweet tweet = Tweet.fromJSON(response);
                    Intent data = new Intent();
                    // Pass relevant data back as a result
                    data.putExtra("tweet", Parcels.wrap(tweet));
                    // Activity finished ok, return the data
                    setResult(RESULT_OK, data); // set result code and bundle data for response
                    finish(); // closes the activity, pass data to parent
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /*
    // Instance of the progress action-view
    MenuItem miActionProgressItem;
    @Override
    public boolean onPrepareOptionsMenu(Menu menu, String text) {
        // Store instance of the menu item containing progress
        miActionProgressItem = menu.findItem(R.id.miActionProgress);
        postMyTweet(text);
        // Return to finish
        return super.onPrepareOptionsMenu(menu);
    }

    public void showProgressBar() {
        // Show progress item
        miActionProgressItem.setVisible(true);
    }

    public void hideProgressBar() {
        // Hide progress item
        miActionProgressItem.setVisible(false);
    }
    */
}

