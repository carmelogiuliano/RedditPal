package com.carmelogiuliano.redditpal.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.carmelogiuliano.redditpal.R;
import com.carmelogiuliano.redditpal.activity.DetailActivity;
import com.carmelogiuliano.redditpal.activity.ImageActivity;
import com.carmelogiuliano.redditpal.activity.MainActivity;
import com.carmelogiuliano.redditpal.model.Post;

import java.util.ArrayList;

import uk.co.senab.photoview.PhotoViewAttacher;


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
    private static final int IMAGE_PREVIEW_INDEX = 3;
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
        RecyclerView.ViewHolder holder = null;
        if(viewType == VIEW_TYPE_POST) {
            View itemView = mInflater.inflate(R.layout.post_item, parent, false);
            holder = new PostViewHolder(itemView);
        } else if(viewType == VIEW_TYPE_LOADING) {
            View itemView = mInflater.inflate(R.layout.loading_item, parent, false);
            holder = new LoadingViewHolder(itemView);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof PostViewHolder) {
            PostViewHolder postHolder = (PostViewHolder) holder;
            Post post = mPostList.get(position);
            postHolder.title.setText(post.getTitle());
            postHolder.numComments.setText(mContext.getString(R.string.num_comments, post.getNumComments()));
            postHolder.domain.setText(post.getDomain());

            if (post.getImagePreviews() != null) {
                if(post.getImagePreviews().size() != 0) {
                    try {
                        String previewUrl = post.getImagePreviews().get(IMAGE_PREVIEW_INDEX).getUrl();
                        Glide.with(mContext).load(previewUrl).into(postHolder.image);
                        postHolder.image.setVisibility(View.VISIBLE);
                    } catch (IndexOutOfBoundsException e) {
                        int index = post.getImagePreviews().size() - 1;
                        String previewUrl = post.getImagePreviews().get(index).getUrl();
                        Glide.with(mContext).load(previewUrl).into(postHolder.image);
                        postHolder.image.setVisibility(View.VISIBLE);
                    }
                }
            } else if (post.isImage()) {
                Glide.with(mContext).load(post.getUrl()).into(postHolder.image);
                postHolder.image.setVisibility(View.VISIBLE);
            } else {
                postHolder.image.setVisibility(View.GONE);
            }
        } else if(holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingHolder = (LoadingViewHolder) holder;
            loadingHolder.progressBar.setIndeterminate(true);
        }

    }

    @Override
    public int getItemViewType(int position) {
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

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        mOnLoadMoreListener = onLoadMoreListener;
    }

    public void setLoaded(boolean loading) {
        mLoading = loading;
    }

    public void setAfter(String after) {
        mAfter = after;
    }



    public class PostViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView numComments;
        private TextView domain;
        private ImageView image;
        private RelativeLayout details;
        private ImageButton shareBtn;

        public PostViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.post_title);
            numComments = (TextView) itemView.findViewById(R.id.post_num_comments);
            domain = (TextView) itemView.findViewById(R.id.post_domain);
            image = (ImageView) itemView.findViewById(R.id.post_image);
            details = (RelativeLayout) itemView.findViewById(R.id.post_details);
            shareBtn = (ImageButton) itemView.findViewById(R.id.imageButton_share);
            shareBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Post post = mPostList.get(getAdapterPosition());
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_SEND);
                    intent.putExtra(Intent.EXTRA_TEXT, post.getUrl());
                    intent.setType("text/plain");
                    Intent.createChooser(intent, "Share via");
                    mContext.startActivity(intent);
                }
            });

            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ImageActivity.class);
                    Post post = mPostList.get(getAdapterPosition());

                    String imgUrl = "";
                    if (post.getImagePreviews() != null) {
                        try {
                            imgUrl = post.getImagePreviews().get(IMAGE_PREVIEW_INDEX).getUrl();
                        } catch (IndexOutOfBoundsException e) {
                            int index = post.getImagePreviews().size() - 1;
                            imgUrl = post.getImagePreviews().get(index).getUrl();
                        }
                    } else if (post.isImage()) {
                        imgUrl = post.getUrl();
                    }
                    intent.putExtra("IMG_URL", imgUrl);
                    //intent.putExtra("IMG_URL", post.getUrl());
                    mContext.startActivity(intent);

                }
            });

            details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Post post = mPostList.get(getAdapterPosition());
                    Intent intent = new Intent(mContext, DetailActivity.class);
                    intent.putExtra("POST", post);
                    mContext.startActivity(intent);
                }
            });
        }
    }

    public class LoadingViewHolder extends RecyclerView.ViewHolder {
        private ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.load_item_progressBar);
        }
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

}
