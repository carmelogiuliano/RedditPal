package com.carmelogiuliano.redditpal.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.carmelogiuliano.redditpal.R;
import com.carmelogiuliano.redditpal.model.ImagePreview;
import com.carmelogiuliano.redditpal.model.Listing;
import com.carmelogiuliano.redditpal.model.Post;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * Created by Carmelo on 14/09/2016.
 */
public class PostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Post> mPostList;
    private LayoutInflater mInflater;
    private Context mContext;
    private String mAfter;
    private OnLoadMoreListener mOnLoadMoreListener;
    private static final int VIEW_TYPE_POST = 0;
    private static final int VIEW_TYPE_LOADING = 1;
    private static final int IMAGE_PREVIEW_INDEX = 2;
    private static final int VISIBLE_THRESHOLD = 3;

    private boolean mLoading = true;


    public PostAdapter(Context context, RecyclerView recyclerView, ArrayList<Post> posts) {
        mContext = context;
        mPostList = posts;
        mInflater = LayoutInflater.from(context);

        final LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int totalItemCount = layoutManager.getItemCount();
                int lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                if (mAfter != null && !mLoading && (totalItemCount <= lastVisibleItem + VISIBLE_THRESHOLD)) {
                    if(mOnLoadMoreListener != null) {
                        mOnLoadMoreListener.onLoadMore();
                    }
                }
            }
        });
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
            //if(!post.isNSFW()) {
                postHolder.title.setText(post.getTitle());
                postHolder.numComments.setText(post.getNumComments());

                if(post.getImagePreviews() != null) { // if not null, at least one ImagePreview exists
                    try {
                        String previewUrl = post.getImagePreviews().get(IMAGE_PREVIEW_INDEX).getUrl();
                        Picasso.with(mContext).load(previewUrl).into(postHolder.image);
                    } catch (IndexOutOfBoundsException e) {
                        int index = post.getImagePreviews().size() - 1;
                        String previewUrl = post.getImagePreviews().get(index).getUrl();
                        Picasso.with(mContext).load(previewUrl).into(postHolder.image);
                    }
                }
            //}
        } else if(holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingHolder = (LoadingViewHolder) holder;
            loadingHolder.progressBar.setIndeterminate(true);
        }

    }

    @Override
    public int getItemViewType(int position) {
        /*if (position == mPostList.size() - 1) {
            return VIEW_TYPE_LOADING;
        } else {
            return VIEW_TYPE_POST;
        }*/
        //return mPostList.get(position) != null ? VIEW_TYPE_POST : VIEW_TYPE_LOADING;

        if(mPostList.get(position) != null) {
            return VIEW_TYPE_POST;
        } else {
            return VIEW_TYPE_LOADING;
        }
    }

    @Override
    public int getItemCount() {
        return mPostList.size();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.mOnLoadMoreListener = mOnLoadMoreListener;
    }

    public void setLoaded() {
        mLoading = !mLoading;
    }

    public void setAfter(String after) {
        mAfter = after;
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
