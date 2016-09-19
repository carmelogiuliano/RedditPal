package com.carmelogiuliano.redditpal.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.carmelogiuliano.redditpal.R;
import com.carmelogiuliano.redditpal.adapter.PostAdapter;
import com.carmelogiuliano.redditpal.http.RedditService;
import com.carmelogiuliano.redditpal.model.Listing;
import com.carmelogiuliano.redditpal.model.Post;

import java.io.FileOutputStream;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Callback<Listing> {
    private RecyclerView mRecyclerView;
    private PostAdapter mPostAdapter;
    private ArrayList<Post> mPostList;
    private LinearLayoutManager mLayoutManager;
    private RedditService mClient;
    private String mSubreddit = "pics";
    //private String mSubreddit = "moooosseey";
    private String mAfter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //region drawer
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //endregion

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

                mPostAdapter.setLoaded();
                Call<Listing> call = mClient.getPosts(mSubreddit, mAfter);
                call.enqueue(MainActivity.this);
            }
        });

        mRecyclerView.setAdapter(mPostAdapter);



        mClient = new RedditService();
        Call<Listing> call = mClient.getPosts(mSubreddit, null);
        call.enqueue(this);
    }

    //region drawer
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //endregion


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
        mPostAdapter.setLoaded();
    }

    @Override
    public void onFailure(Call<Listing> call, Throwable t) {
        Toast.makeText(this, "Network error", Toast.LENGTH_LONG).show();
    }
    //endregion
}
