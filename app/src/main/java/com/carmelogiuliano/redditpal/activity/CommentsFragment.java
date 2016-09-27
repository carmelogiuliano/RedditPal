package com.carmelogiuliano.redditpal.activity;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.carmelogiuliano.redditpal.R;
import com.carmelogiuliano.redditpal.adapter.CommentAdapter;
import com.carmelogiuliano.redditpal.http.RedditService;
import com.carmelogiuliano.redditpal.model.Comment;
import com.carmelogiuliano.redditpal.model.CommentList;
import com.carmelogiuliano.redditpal.model.Listing;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommentsFragment extends Fragment implements Callback<CommentList> {
    private RedditService mClient;
    private String mPermalink;
    private ArrayList<Comment> mCommentList;
    private CommentAdapter mCommentAdapter;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;

    public CommentsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCommentList = new ArrayList<>();
        mClient = RedditService.getInstance();
        mPermalink = getActivity().getIntent().getStringExtra("PERMALINK");

        Call<CommentList> call = mClient.getComments(mPermalink);
        call.enqueue(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_comments, container, false);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.recycler_view_comments);
        mLayoutManager = new LinearLayoutManager(v.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mCommentAdapter = new CommentAdapter(v.getContext(), mCommentList);
        mRecyclerView.setAdapter(mCommentAdapter);

        return v;
    }

    @Override
    public void onResponse(Call<CommentList> call, Response<CommentList> response) {
        mCommentList.addAll(response.body().getComments());
        mCommentAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFailure(Call<CommentList> call, Throwable t) {
        return;
    }
}
