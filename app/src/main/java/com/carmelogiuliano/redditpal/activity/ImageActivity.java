package com.carmelogiuliano.redditpal.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.carmelogiuliano.redditpal.Constants;
import com.carmelogiuliano.redditpal.R;

import uk.co.senab.photoview.PhotoViewAttacher;

public class ImageActivity extends AppCompatActivity {
    private String mUrl;
    private ImageView mImg;
    private ProgressBar mProgressBar;
    private PhotoViewAttacher mAttacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_image);

        mProgressBar = (ProgressBar) findViewById(R.id.activity_image_progressBar);
        mImg = (ImageView) findViewById(R.id.activity_image_img);
        mUrl = getIntent().getStringExtra(Constants.INTENT_IMAGE_URL);
        Glide.with(this)
                .load(mUrl)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        Toast.makeText(ImageActivity.this, Constants.NETWORK_ERROR_MSG, Toast.LENGTH_LONG).show();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        mProgressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(mImg);

        mAttacher = new PhotoViewAttacher(mImg);
        mAttacher.setZoomable(true);
        mAttacher.setScaleType(ImageView.ScaleType.FIT_CENTER);
    }
}
