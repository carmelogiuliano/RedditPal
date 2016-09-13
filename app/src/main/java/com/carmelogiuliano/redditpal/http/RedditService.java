package com.carmelogiuliano.redditpal.http;

import com.carmelogiuliano.redditpal.model.Listing;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Carmelo on 13/09/2016.
 */
public class RedditService {
    public Retrofit mRetrofit;
    public RedditAPI mRedditAPI;

    public RedditService() {
        Gson gson = new GsonBuilder()
            .registerTypeAdapter(Listing.class, new RedditDeserializer())
            .create();

        mRetrofit = new Retrofit.Builder()
            .baseUrl("https://www.reddit.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

        mRedditAPI = mRetrofit.create(RedditAPI.class);
    }
}
