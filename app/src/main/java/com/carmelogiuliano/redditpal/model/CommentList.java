package com.carmelogiuliano.redditpal.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Carmelo on 21/09/2016.
 */
public class CommentList {
    @SerializedName("children")
    private List<Comment> comments;

    public List<Comment> getComments() {
        return comments;
    }
}
