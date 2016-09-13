package com.carmelogiuliano.redditpal.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Carmelo on 14/09/2016.
 */
public class PostData {
    private String domain;
    private String subreddit;
    private String author;
    //private String media;
    private String score;
    @SerializedName("over_18")
    private boolean isNSFW;
    @SerializedName("thumbnail")
    private String thumbnailUrl;
    @SerializedName("subreddit_id")
    private String subredditId;
    @SerializedName("post_hint")
    private String postHint;
    @SerializedName("is_self")
    private boolean isSelf;
    private String permalink;
    private long created;
    private String url;
    private String title;
    @SerializedName("created_utc")
    private long createdUtc;
    @SerializedName("num_comments")
    private int numComments;
    private boolean visited;

    public String getDomain() {
        return domain;
    }

    public String getSubreddit() {
        return subreddit;
    }

    public String getAuthor() {
        return author;
    }

    /*public String getMedia() {
        return media;
    }*/

    public String getScore() {
        return score;
    }

    public boolean isNSFW() {
        return isNSFW;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public String getSubredditId() {
        return subredditId;
    }

    public String getPostHint() {
        return postHint;
    }

    public boolean isSelf() {
        return isSelf;
    }

    public String getPermalink() {
        return permalink;
    }

    public long getCreated() {
        return created;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public long getCreatedUtc() {
        return createdUtc;
    }

    public int getNumComments() {
        return numComments;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }
}
