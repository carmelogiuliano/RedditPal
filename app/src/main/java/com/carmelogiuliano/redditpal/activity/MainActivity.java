package com.carmelogiuliano.redditpal.activity;

import android.app.SearchManager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.carmelogiuliano.redditpal.R;
import com.carmelogiuliano.redditpal.adapter.PostAdapter;
import com.carmelogiuliano.redditpal.http.RedditService;
import com.carmelogiuliano.redditpal.model.Listing;
import com.carmelogiuliano.redditpal.model.Post;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements Callback<Listing> {
    private RecyclerView mRecyclerView;
    private PostAdapter mPostAdapter;
    private ArrayList<Post> mPostList;
    private LinearLayoutManager mLayoutManager;
    private RedditService mClient;
    private String mSubreddit = "hackintosh";
    private String mAfter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_posts);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mPostList = new ArrayList<>();
        mPostAdapter = new PostAdapter(this, mRecyclerView, mPostList);
        mPostAdapter.setOnLoadMoreListener(new PostAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mPostList.add(null); // adapter will recognise null item and infalte progressbar
                mPostAdapter.notifyItemChanged(mPostList.size() - 1);

                mPostAdapter.setLoaded(true);
                Call<Listing> call = mClient.getPosts(mSubreddit, mAfter);
                call.enqueue(MainActivity.this);
            }
        });

        mRecyclerView.setAdapter(mPostAdapter);



        mClient = RedditService.getInstance();
        Call<Listing> call = mClient.getPosts(mSubreddit, null);
        call.enqueue(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Toast.makeText(MainActivity.this, query, Toast.LENGTH_LONG).show();
                mSubreddit = query;
                mPostList.clear();
                mPostAdapter.notifyDataSetChanged();
                Call<Listing> call = mClient.getPosts(mSubreddit, null);
                call.enqueue(MainActivity.this);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        };

        searchView.setOnQueryTextListener(queryTextListener);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    //region Retrofit
    @Override
    public void onResponse(Call<Listing> call, Response<Listing> response) {

        // remove progressbar
        if(mPostList.size() > 0) {
            mPostList.remove(mPostList.size() - 1);
        }

        mAfter = response.body().getAfter();
        mPostAdapter.setAfter(mAfter);
        mPostList.addAll(response.body().getPosts());
        mPostAdapter.notifyDataSetChanged();
        mPostAdapter.setLoaded(false);
    }

    @Override
    public void onFailure(Call<Listing> call, Throwable t) {
        Toast.makeText(this, "Network error", Toast.LENGTH_LONG).show();
    }
    //endregion
}
