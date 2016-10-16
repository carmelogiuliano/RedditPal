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
 * Created by Carmelo on 21/09/2016.
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








    /*@Override
    public PostComments deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        mCommentList = new JsonArray();

        JsonObject data = json.getAsJsonArray().get(1).getAsJsonObject();
        JsonArray comments = data.getAsJsonObject("data").getAsJsonArray("children");

        validateReplies(comments);


        JsonObject commentList = new JsonObject();
        commentList.add("children", comments);
        JsonObject postComments = new JsonObject();
        postComments.add("data", commentList);
        return new Gson().fromJson(postComments, PostComments.class);
    }

    private void validateReplies(JsonArray comments) {
        for (int i = 0; i < comments.size(); i++) {
            JsonElement replies = comments.get(i)
                    .getAsJsonObject()
                    .getAsJsonObject("data")
                    .get("replies");

            if(!replies.isJsonObject()) {
                comments.get(i)
                        .getAsJsonObject()
                        .getAsJsonObject("data")
                        .remove("replies");

                return;
            }

            validateReplies(replies.getAsJsonObject().getAsJsonObject("data").getAsJsonArray("children"));
        }
    }*/

    /*@Override
    public CommentList deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        mCommentList = new JsonArray();

        JsonObject data = json.getAsJsonArray().get(1).getAsJsonObject();
        JsonArray comments = data.getAsJsonObject("data").getAsJsonArray("children");

        getCommentData(comments, mTier);

        JsonObject commentList = new JsonObject();
        commentList.add("comments", mCommentList);
        return new Gson().fromJson(commentList, CommentList.class);
    }

    private void getCommentData(JsonArray comments, int tier) {
        for (int i = 0; i < comments.size(); i++) {
            JsonObject commentData = comments.get(i).getAsJsonObject().getAsJsonObject("data");
            commentData.addProperty("tier", tier);
            mCommentList.add(commentData);
            addReplies(commentData, tier + 1);
        }
    }

    private void addReplies(JsonObject parent, int tier) {
        //JsonObject repliesObj = parent.getAsJsonObject("replies");
        if(parent.get("replies") != null) {
            JsonArray replies = parent.getAsJsonObject("replies").getAsJsonObject("data").getAsJsonArray("children");
            getCommentData(replies, tier);
        }
    }*/
}
