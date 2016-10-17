package com.carmelogiuliano.redditpal.http;

import com.carmelogiuliano.redditpal.model.CommentList;
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
 * Deserializes post comments.
 */
public class CommentDeserializer implements JsonDeserializer<CommentList> {

    @Override
    public CommentList deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject data = json.getAsJsonArray().get(1).getAsJsonObject();
        JsonArray children = data.getAsJsonObject("data").getAsJsonArray("children");

        JsonArray comments = new JsonArray();
        for (int i = 0; i < children.size(); i++) {
            String kind = children.get(i).getAsJsonObject().get("kind").getAsString();
            if(kind.equals("t1")) {
                JsonObject comment = children.get(i).getAsJsonObject().getAsJsonObject("data");
                comments.add(comment);
            }
        }

        JsonObject commentList = new JsonObject();
        commentList.add("children", comments);

        return new Gson().fromJson(commentList, CommentList.class);
    }
}
