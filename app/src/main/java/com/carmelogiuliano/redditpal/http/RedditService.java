package com.carmelogiuliano.redditpal.http;

import com.carmelogiuliano.redditpal.model.Comment;
import com.carmelogiuliano.redditpal.model.CommentList;
import com.carmelogiuliano.redditpal.model.Listing;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Carmelo on 13/09/2016.
 */
public class RedditService {
    private Retrofit mRetrofit;
    private RedditAPI mRedditAPI;
    private static RedditService mInstance;

    private RedditService() {
        Gson gson = new GsonBuilder()
            .registerTypeAdapter(Listing.class, new RedditDeserializer())
            .create();

        mRetrofit = new Retrofit.Builder()
            .baseUrl("https://www.reddit.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

        mRedditAPI = mRetrofit.create(RedditAPI.class);
    }

    public static RedditService getInstance() {
        if(mInstance != null) {
            return mInstance;
        }

        mInstance = new RedditService();
        return mInstance;
    }

    public Call<Listing> getPosts(String subreddit, String after) {
        return mRedditAPI.getPosts(subreddit, after);
    }

    public Call<CommentList> getComments(String permalink) {
        return mRedditAPI.getComments(permalink);
    }
}
