package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;


import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder> {
    private List<Tweet> mTweets;
    Context context;
    //pass in tweets array to constructor
    public TweetAdapter(List<Tweet> tweets){
        mTweets = tweets;
    }
    Tweet tweet;
    //for each row, inflate the layout and cache references into ViewHolder
    public ViewHolder onCreateViewHolder(ViewGroup parent, int ViewType){
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View tweetview = inflater.inflate(R.layout.item_tweet, parent, false);
        ViewHolder viewHolder = new ViewHolder(tweetview);


        return viewHolder;
    }

    //bind the values based on the position of the element

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        //get the data according to the position
        tweet = mTweets.get(position);
        //populate the views according to this
        holder.tvUsername.setText(tweet.user.name);
        holder.tvBody.setText(tweet.body);
        holder.timestamp.setText(getRelativeTimeAgo(tweet.createdAt));
        holder.userHandle.setText(tweet.user.screenName);

        holder.tvReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String personToReplyTo = holder.userHandle.getText().toString();
                Intent i = new Intent(context, ComposeActivity.class);
                i.putExtra("user", personToReplyTo);
                context.startActivity(i);
            }
        });


        Glide.with(context)
                .load(tweet.user.profileImageUrl)
                .into(holder.ivProfileImage);

        if (tweet.hasEntities){
            String entityUrl = tweet.entity.loadUrl;
            holder.ivPicture.setVisibility(View.VISIBLE);
            Glide.with(context)
                    .load(entityUrl)
                    .into(holder.ivPicture);
        }


    }


    public int getItemCount(){
       return mTweets.size();
    }


    //create ViewHolder class

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivProfileImage) public ImageView ivProfileImage;
        @BindView(R.id.tvUserName) public TextView tvUsername;
        @BindView(R.id.tvBody) public TextView tvBody;
        @BindView(R.id.timestamp)public TextView timestamp;
        @BindView(R.id.handle)public TextView userHandle;
        @BindView(R.id.tvReply) public TextView tvReply;
        @BindView(R.id.ivPicture) public ImageView ivPicture;


        public ViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }



    public String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }

    // Clean all elements of the recycler
    public void clear() {
        mTweets.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Tweet> list) {
        mTweets.addAll(list);
        notifyDataSetChanged();
    }

    /*
    public void reply(View view) {
       // Intent i = new Intent(TimelineActivity.this, ComposeActivity.class);
       // i.putExtra("Person", "sampleperson");
       // startActivityForResult(i, REQUEST_CODE);
        tweet.user.name;
    }

    public void retweet(View view) {
       // Intent i = new Intent(TimelineActivity.this, ComposeActivity.class);
       // startActivityForResult(i, REQUEST_CODE);
    }
    */
}
