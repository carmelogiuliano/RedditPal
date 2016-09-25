package com.carmelogiuliano.redditpal.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.carmelogiuliano.redditpal.R;
import com.carmelogiuliano.redditpal.http.RedditService;
import com.carmelogiuliano.redditpal.model.Comment;
import com.carmelogiuliano.redditpal.model.CommentList;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestActivity extends AppCompatActivity implements Callback<CommentList> {
    private RedditService mClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        mClient = RedditService.getInstance();
        Call<CommentList> call = mClient.getComments("r/gnome/comments/53i8ev/backslide_automatic_backgroundimage_wallpaper/");

        call.enqueue(this);

    }


    @Override
    public void onResponse(Call<CommentList> call, Response<CommentList> response) {
        CommentList cml = response.body();
    }

    @Override
    public void onFailure(Call<CommentList> call, Throwable t) {
        return;
    }
}
