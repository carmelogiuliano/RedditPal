package com.carmelogiuliano.redditpal.http;

import com.carmelogiuliano.redditpal.model.Listing;
import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Created by Carmelo on 13/09/2016.
 */
public class RedditDeserializer implements JsonDeserializer<Listing> {
    @Override
    public Listing deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonElement listing = json.getAsJsonObject().get("data");
        return new Gson().fromJson(listing, Listing.class);
    }
}
