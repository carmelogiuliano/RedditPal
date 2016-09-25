package com.carmelogiuliano.redditpal.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Carmelo on 21/09/2016.
 */
public class Comment {
    private String body;
    @SerializedName("body_html")
    private String bodyHtml;
    private String author;
    private String score;
    @SerializedName("created_utc")
    private String createdUtc;

    public String getBodyHtml() {
        return bodyHtml;
    }

    public String getAuthor() {
        return author;
    }

    public String getScore() {
        return score;
    }

    public String getCreatedUtc() {
        return createdUtc;
    }
}
