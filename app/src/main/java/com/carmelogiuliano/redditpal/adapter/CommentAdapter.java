package com.carmelogiuliano.redditpal.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.carmelogiuliano.redditpal.R;
import com.carmelogiuliano.redditpal.model.Comment;

import java.util.ArrayList;

/**
 * Prepares comment item views for RecyclerView.
 */
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {
    private ArrayList<Comment> mCommentList;
    private Context mContext;
    private LayoutInflater mInflater;

    public CommentAdapter(Context context, ArrayList<Comment> comments) {
        mContext = context;
        mCommentList = comments;
        mInflater = LayoutInflater.from(context);
    }


    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.comment_item, parent, false);
        return new CommentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position) {
        Comment comment = mCommentList.get(position);
        holder.body.setText(Html.fromHtml(comment.getBodyHtml()));
        holder.author.setText(comment.getAuthor());
    }

    @Override
    public int getItemCount() {
        return mCommentList.size();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {
        public TextView author;
        private TextView body;

        public CommentViewHolder(View itemView) {
            super(itemView);
            author = (TextView) itemView.findViewById(R.id.comment_item_author);
            body = (TextView) itemView.findViewById(R.id.comment_item_body);
        }
    }
}
