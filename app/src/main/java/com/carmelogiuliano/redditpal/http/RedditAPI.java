package com.carmelogiuliano.redditpal.http;

import com.carmelogiuliano.redditpal.model.Comment;
import com.carmelogiuliano.redditpal.model.CommentList;
import com.carmelogiuliano.redditpal.model.Listing;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Carmelo on 13/09/2016.
 */
public interface RedditAPI {

    @GET("/r/{subreddit}/.json?raw_json=1")
    Call<Listing> getPosts(@Path("subreddit") String subreddit, @Query("after") String after);

    @GET("/{permalink}/.json?raw_json=1")
    Call<CommentList> getComments(@Path("permalink") String permalink);
}
