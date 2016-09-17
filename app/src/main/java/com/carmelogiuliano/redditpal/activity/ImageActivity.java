package com.carmelogiuliano.redditpal.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.carmelogiuliano.redditpal.R;

public class ImageActivity extends AppCompatActivity {
    private String mUrl;
    private ImageView mImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_image);

        mImg = (ImageView) findViewById(R.id.activity_image_img);
        mUrl = getIntent().getStringExtra("IMG_URL");
        Glide.with(this).load(mUrl).dontAnimate().into(mImg);

    }
}
