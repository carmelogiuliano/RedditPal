package com.carmelogiuliano.redditpal.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.carmelogiuliano.redditpal.R;
import com.carmelogiuliano.redditpal.model.Post;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Carmelo on 14/09/2016.
 */
public class PostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Post> mPostList;
    private LayoutInflater mInflater;
    private Context mContext;
    private static final int VIEW_TYPE_POST = 0;
    private static final int VIEW_TYPE_LOADING = 1;

    public PostAdapter(Context context, ArrayList<Post> posts) {
        mContext = context;
        mPostList = posts;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == VIEW_TYPE_POST) {
            View itemView = mInflater.inflate(R.layout.post_item, parent, false);
            return new PostViewHolder(itemView);
        } else if(viewType == VIEW_TYPE_LOADING) {
            View itemView = mInflater.inflate(R.layout.loading_item, parent, false);
            return new LoadingViewHolder(itemView);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof PostViewHolder) {
            PostViewHolder postHolder = (PostViewHolder) holder;
            Post post = mPostList.get(position);
            postHolder.title.setText(post.getTitle());
            postHolder.numComments.setText(post.getNumComments());


        } else if(holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingHolder = (LoadingViewHolder) holder;
            loadingHolder.progressBar.setIndeterminate(true);
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (position == mPostList.size() - 1) {
            return VIEW_TYPE_LOADING;
        } else {
            return VIEW_TYPE_POST;
        }
    }

    @Override
    public int getItemCount() {
        return mPostList.size();
    }


    public class PostViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView numComments;
        private ImageView image;

        public PostViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.post_title);
            numComments = (TextView) itemView.findViewById(R.id.post_num_comments);
            image = (ImageView) itemView.findViewById(R.id.post_image);
        }
    }

    public class LoadingViewHolder extends RecyclerView.ViewHolder {
        private ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.load_item_progressBar);
        }
    }
}
