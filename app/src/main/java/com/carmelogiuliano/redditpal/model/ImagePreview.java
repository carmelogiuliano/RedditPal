package com.carmelogiuliano.redditpal.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Carmelo on 16/09/2016.
 */
public class ImagePreview implements Serializable {
    private String url;
    private int width;
    private int height;

    public String getUrl() {
        return url;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
