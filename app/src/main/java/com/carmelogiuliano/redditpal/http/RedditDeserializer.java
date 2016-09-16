package com.carmelogiuliano.redditpal.http;

import com.carmelogiuliano.redditpal.model.Listing;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;

import java.lang.reflect.Type;

/**
 * Created by Carmelo on 13/09/2016.
 */
public class RedditDeserializer implements JsonDeserializer<Listing> {
    @Override
    public Listing deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonArray children = json.getAsJsonObject().getAsJsonObject("data").getAsJsonArray("children");
        JsonPrimitive after = json.getAsJsonObject().getAsJsonObject("data").getAsJsonPrimitive("after");

        JsonArray posts = new JsonArray();
        for (int i = 0; i < children.size(); i++) {
            JsonObject post = children.get(i).getAsJsonObject().getAsJsonObject("data");

            JsonObject preview = post.getAsJsonObject("preview");
            if(preview != null) {
                JsonElement imagePreviews = preview.getAsJsonArray("images").get(0)
                        .getAsJsonObject().getAsJsonArray("resolutions");

                post.add("imagePreviews", imagePreviews);
            }

            posts.add(post);
        }

        JsonObject listing = new JsonObject();
        listing.add("after", after);
        listing.add("posts", posts);

        return new Gson().fromJson(listing, Listing.class);
    }
}
