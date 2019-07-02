package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder> {
    private List<Tweet> mTweets;
    Context context;
    //pass in tweets array to constructor
    public TweetAdapter(List<Tweet> tweets){
        mTweets = tweets;
    }
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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //get the data according to the position
        Tweet tweet = mTweets.get(position);
        //populate the views according to this
        holder.tvUsername.setText(tweet.user.name);
        holder.tvBody.setText(tweet.body);
        holder.timestamp.setText(getRelativeTimeAgo(tweet.createdAt));
        holder.userHandle.setText(tweet.user.screenName);

        Glide.with(context)
                .load(tweet.user.profileImageUrl)
              //  .placeholder(R.drawable.placeholder)
                //.error(R.drawable.imagenotfound)
                .into(holder.ivProfileImage);


    }

    public int getItemCount(){
       return mTweets.size();
    }


    //create ViewHolder class

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivProfileImage;
        public TextView tvUsername;
        public TextView tvBody;
        public TextView timestamp;
        public TextView userHandle;

        public ViewHolder(View itemView){
            super(itemView);
            //perform findViewById lookups
            ivProfileImage = itemView.findViewById(R.id.ivProfileImage);
            tvUsername = itemView.findViewById(R.id.tvUserName);
            tvBody = itemView.findViewById(R.id.tvBody);
            timestamp = itemView.findViewById(R.id.timestamp);
            userHandle = itemView.findViewById(R.id.handle);


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
}
