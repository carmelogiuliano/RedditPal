package com.carmelogiuliano.redditpal.activity;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.carmelogiuliano.redditpal.Constants;
import com.carmelogiuliano.redditpal.R;
import com.carmelogiuliano.redditpal.model.Post;

/**
 * Loads a post's url in a WebView.
 */
public class WebFragment extends Fragment {
    private Post mPost;
    private WebView mWebView;
    private TextView msg;


    public WebFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPost = (Post) getActivity().getIntent().getSerializableExtra(Constants.INTENT_POST);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_web, container, false);
        TextView msg = (TextView) v.findViewById(R.id.fragment_web_msg);
        mWebView = (WebView) v.findViewById(R.id.fragment_web_webview);

        if(mPost.isSelf()) {
            msg.setVisibility(View.VISIBLE);
            mWebView.setVisibility(View.GONE);
        }
        else {
            WebSettings settings = mWebView.getSettings();
            settings.setJavaScriptEnabled(true);
            mWebView.setWebViewClient(new WebViewClient());
            mWebView.loadUrl(mPost.getUrl());
        }

        return v;
    }

}
