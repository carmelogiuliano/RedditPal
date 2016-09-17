package com.carmelogiuliano.redditpal.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carmelo on 13/09/2016.
 */
public class Listing {
    private String after;
    private List<Post> posts;

    public String getAfter() {
        return after;
    }

    public List<Post> getPosts() {
        return posts;
    }
}
