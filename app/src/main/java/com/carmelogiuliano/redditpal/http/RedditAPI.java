package com.carmelogiuliano.redditpal.http;

import com.carmelogiuliano.redditpal.model.Listing;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Carmelo on 13/09/2016.
 */
public interface RedditAPI {


    @GET("/r/{subreddit}/.json")
    Call<Listing> getPosts(@Path("subreddit") String subreddit);

}
