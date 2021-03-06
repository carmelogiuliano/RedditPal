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

import retrofit2.http.Path;

/**
 * Deserializes reddit posts.
 */
public class PostDeserializer implements JsonDeserializer<Listing> {
    @Override
    public Listing deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonArray children = json.getAsJsonObject().getAsJsonObject("data").getAsJsonArray("children");
        JsonPrimitive after;
        try {
            after = json.getAsJsonObject().getAsJsonObject("data").getAsJsonPrimitive("after");
        } catch (ClassCastException e) {
            after = null;
        }

        JsonArray posts = new JsonArray();
        for (int i = 0; i < children.size(); i++) {
            JsonObject post = children.get(i).getAsJsonObject().getAsJsonObject("data");

            boolean nsfw = post.get("over_18").getAsBoolean();
            if(nsfw) {
                children.getAsJsonArray().remove(i);
                continue;
            }

            JsonObject preview = post.getAsJsonObject("preview");
            if(preview != null) {
                JsonObject imagesArray = preview.getAsJsonArray("images").get(0).getAsJsonObject();
                JsonElement previews = imagesArray.getAsJsonArray("resolutions");
                JsonPrimitive imageSourceUrl = imagesArray.getAsJsonObject("source").getAsJsonPrimitive("url");

                post.add("imagePreviews", previews);
                post.add("imageSourceUrl", imageSourceUrl);
            }

            posts.add(post);
        }

        JsonObject listing = new JsonObject();
        listing.add("after", after);
        listing.add("posts", posts);

        return new Gson().fromJson(listing, Listing.class);
    }
}
