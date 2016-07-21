package com.example.mahmoud.recyclerviewmusic.playlist;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mahmoud.recyclerviewmusic.FeedItemAnimator;
import com.example.mahmoud.recyclerviewmusic.GridSpacingItemDecoration;
import com.example.mahmoud.recyclerviewmusic.R;
import com.example.mahmoud.recyclerviewmusic.RevealBackgroundView;
import com.example.mahmoud.recyclerviewmusic.Utils;
import com.example.mahmoud.recyclerviewmusic.main.adapter.AlbumAdapter;
import com.example.mahmoud.recyclerviewmusic.main.model.Album;
import com.example.mahmoud.recyclerviewmusic.main.view.FeedContextMenuManager;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

public class PlaylistActivity extends AppCompatActivity{
    public static final String ARG_REVEAL_START_LOCATION = "reveal_start_location";
    public static final String SINGER_NAME = "singerName";

    Toolbar toolbar;
    AppBarLayout appBarLayout;
    CollapsingToolbarLayout collapsingToolbar;
    Album album;
    Gson gson;
    private RecyclerView recyclerView;
    SongsAdabter adapter;
    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        gson = new Gson();
        image = (ImageView)findViewById(R.id.image);
        album = gson.fromJson(getIntent().getStringExtra(SINGER_NAME),Album.class);
        initRecyclerView();
        initCollapsingToolbar();
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.back_icon));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void initCollapsingToolbar() {
        collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(album.getName());
        appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        Picasso.with(this).load(album.getThumbnail()).into(image);
        appBarLayout.setExpanded(true);
        collapsingToolbar.setCollapsedTitleTextColor(getResources().getColor(R.color.album_title));
        collapsingToolbar.setExpandedTitleColor(getResources().getColor(R.color.album_title));
    }

    private void initRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        adapter = new SongsAdabter(album);
//        adapter.setOnFeedItemClickListener(this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

//        recyclerView.setItemAnimator(new FeedItemAnimator());
    }

    public static void startUserProfileFromLocation(int[] startingLocation, Activity startingActivity, String album) {
        Intent intent = new Intent(startingActivity, PlaylistActivity.class);
        intent.putExtra(ARG_REVEAL_START_LOCATION, startingLocation);
        intent.putExtra(SINGER_NAME,album);
        startingActivity.startActivity(intent);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        PlaylistActivity.this.overridePendingTransition(0,R.anim.slide_out_done);
    }
}
