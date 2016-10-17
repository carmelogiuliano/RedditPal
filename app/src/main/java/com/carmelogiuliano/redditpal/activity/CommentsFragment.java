package com.carmelogiuliano.redditpal.activity;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.carmelogiuliano.redditpal.Constants;
import com.carmelogiuliano.redditpal.R;
import com.carmelogiuliano.redditpal.adapter.CommentAdapter;
import com.carmelogiuliano.redditpal.http.RedditService;
import com.carmelogiuliano.redditpal.model.Comment;
import com.carmelogiuliano.redditpal.model.CommentList;
import com.carmelogiuliano.redditpal.model.Post;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Displays post's comments.
 */
public class CommentsFragment extends Fragment implements Callback<CommentList> {
    private RedditService mClient;
    private ArrayList<Comment> mCommentList;
    private CommentAdapter mCommentAdapter;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private TextView mTitle;
    private TextView mAuthor;
    private TextView mSelfText;
    private TextView mTimestamp;
    private ProgressBar mProgressBar;
    private Post mPost;

    public CommentsFragment() {
        // Required empty public constructor
    }

    /** Initializes member variables. */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPost = (Post) getActivity().getIntent().getSerializableExtra(Constants.INTENT_POST);
        mCommentList = new ArrayList<>();
        mClient = RedditService.getInstance();

        Call<CommentList> call = mClient.getComments(mPost.getPermalink());
        call.enqueue(this);
    }

    /** Configures views. */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_comments, container, false);

        mTitle = (TextView) v.findViewById(R.id.fragment_comments_title);
        mAuthor = (TextView) v.findViewById(R.id.fragment_comments_author);
        mSelfText = (TextView) v.findViewById(R.id.fragment_comments_selftext);
        mTimestamp = (TextView) v.findViewById(R.id.fragment_comments_timestamp);
        mProgressBar = (ProgressBar) v.findViewById(R.id.fragment_comments_progressbar);

        mTitle.setText(mPost.getTitle());
        mAuthor.setText(mPost.getAuthor());
        mTimestamp.setText(DateUtils.getRelativeTimeSpanString(mPost.getCreatedUtc()).toString());

        PrettyTime pt = new PrettyTime();
        mTimestamp.setText(pt.format(new Date(mPost.getCreatedUtc()*1000L)));

        if(mPost.isSelf()) {
            String selfText = mPost.getSelfTextHtml();
            if(selfText != null) {
                mSelfText.setText(Html.fromHtml(selfText));
            }
        }

        mRecyclerView = (RecyclerView) v.findViewById(R.id.recycler_view_comments);
        mLayoutManager = new LinearLayoutManager(v.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mCommentAdapter = new CommentAdapter(v.getContext(), mCommentList);
        mRecyclerView.setAdapter(mCommentAdapter);
        mRecyclerView.setNestedScrollingEnabled(false);

        return v;
    }

    /** Updates dataset from network call. */
    @Override
    public void onResponse(Call<CommentList> call, Response<CommentList> response) {
        mCommentList.addAll(response.body().getComments());
        mCommentAdapter.notifyDataSetChanged();
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onFailure(Call<CommentList> call, Throwable t) {
        return;
    }
}
