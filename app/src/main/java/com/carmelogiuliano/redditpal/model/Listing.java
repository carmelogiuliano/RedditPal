package com.carmelogiuliano.redditpal.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Carmelo on 13/09/2016.
 */
public class Listing {
    private String after;
    private String before;
    @SerializedName("children")
    private List<Post> posts;

    public String getAfter() {
        return after;
    }

    public String getBefore() {
        return before;
    }

    public List<Post> getPosts() {
        return posts;
    }
}
