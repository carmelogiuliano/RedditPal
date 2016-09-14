package com.carmelogiuliano.redditpal.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.carmelogiuliano.redditpal.R;
import com.carmelogiuliano.redditpal.model.Post;

import java.util.ArrayList;

/**
 * Created by Carmelo on 14/09/2016.
 */
public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    private ArrayList<Post> mPostList;
    private LayoutInflater mInflater;

    public PostAdapter(Context context, ArrayList<Post> posts) {
        mPostList = posts;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.post_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Post post = mPostList.get(position);
        holder.title.setText(post.getData().getTitle());
    }

    @Override
    public int getItemCount() {
        return mPostList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.post_title);
        }

    }
}
