package com.example.mahmoud.recyclerviewmusic.main.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.res.Resources;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mahmoud.recyclerviewmusic.FeedItemAnimator;
import com.example.mahmoud.recyclerviewmusic.main.adapter.MyPagerAdapter;
import com.example.mahmoud.recyclerviewmusic.main.fragment.CoverFragment;
import com.example.mahmoud.recyclerviewmusic.GridSpacingItemDecoration;
import com.example.mahmoud.recyclerviewmusic.R;
import com.example.mahmoud.recyclerviewmusic.main.adapter.AlbumAdapter;
import com.example.mahmoud.recyclerviewmusic.main.model.Album;
import com.example.mahmoud.recyclerviewmusic.main.presenter.MainPresenterImpl;
import com.example.mahmoud.recyclerviewmusic.main.presenter.ViewPresenter;
import com.example.mahmoud.recyclerviewmusic.playlist.PlaylistActivity;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends FragmentActivity implements ViewPresenter, ViewPager.OnPageChangeListener , FeedContextMenu.OnFeedContextMenuItemClickListener,
        AlbumAdapter.OnFeedItemClickListener{

    private RecyclerView recyclerView;
    private AlbumAdapter adapter;
    private List<Album> albumList;
    private MainPresenterImpl presenter;
    private ViewPager viewPager;
    int currentPage;
    LinearLayout pagerIndicator;
    MyPagerAdapter myPagerAdapter;
    int dotsCount;
    private ImageView[] dots;
    AppBarLayout appBarLayout;
    CollapsingToolbarLayout collapsingToolbar;
    FloatingActionButton fabCreate;
    private static final int ANIM_DURATION_TOOLBAR = 300;
    private static final int ANIM_DURATION_FAB = 400;
    boolean pendingIntroAnimation;
    Gson googleJson ;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        if (savedInstanceState == null) {
//            pendingIntroAnimation = true;
//        }
        presenter = new MainPresenterImpl(this);
        fabCreate = (FloatingActionButton)findViewById(R.id.fab);
        fabCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                intentToPlaylist(v);
            }
        });
        googleJson = new Gson();
        initCollapsingToolbar();
        initRecyclerView();
        pagerIndicator = (LinearLayout) findViewById(R.id.viewPagerCountDots);
        text = (TextView)findViewById(R.id.text);
        albumList = new ArrayList<>();
        presenter.loadPosts();
        initViewPager();
        timeTaskToUpdateView(updateViewRunnable());
        viewPager.addOnPageChangeListener(this);
        setUiPageViewController();
        startIntroAnimation();
    }

    @Override
    public void onPostsLoad(List<Album> images) {
        this.albumList = images;
        adapter.insertItems(images);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        currentPage = position;
        for (int i = 0; i < dotsCount; i++) {
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));
        }



        dots[position].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (pendingIntroAnimation) {
            pendingIntroAnimation = false;
//            startIntroAnimation();
        }
        return true;
    }

    private void initRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        adapter = new AlbumAdapter(this);
        adapter.setOnFeedItemClickListener(this);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(5), true));
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                FeedContextMenuManager.getInstance().onScrolled(recyclerView, dx, dy);
            }
        });
        recyclerView.setItemAnimator(new FeedItemAnimator());
    }

    private void initViewPager() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        FragmentManager fragmentManager = getSupportFragmentManager();
        myPagerAdapter = new MyPagerAdapter(fragmentManager);
        viewPager.setAdapter(myPagerAdapter);
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    private void setUiPageViewController() {
        dotsCount = myPagerAdapter.getCount();
        dots = new ImageView[dotsCount];

        addIndicatorView();

        if (dots.length <= 1) {
            pagerIndicator.setVisibility(View.GONE);
        } else {
            dots[0].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));
            pagerIndicator.setVisibility(View.VISIBLE);
        }
    }

    private void addIndicatorView() {
        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(4, 0, 4, 0);

            pagerIndicator.addView(dots[i], params);
        }
    }

    private Runnable updateViewRunnable() {
        return new Runnable() {
            public void run() {
                if (currentPage == 5) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
            }
        };
    }

    private void timeTaskToUpdateView(final Runnable update) {
        final Handler handler = new Handler();
        new Timer().schedule(new TimerTask() {

            @Override
            public void run() {
                handler.post(update);
            }
        }, 3000, 2000);
    }

    private void initCollapsingToolbar() {
        collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(getString(R.string.app_name));
        appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);
        collapsingToolbar.setCollapsedTitleTextColor(getResources().getColor(R.color.album_title));
        collapsingToolbar.setExpandedTitleColor(getResources().getColor(R.color.album_title));
    }

    @Override
    public void onEnterAnimationComplete() {
        super.onEnterAnimationComplete();
        recyclerView.setAdapter(adapter);
        recyclerView.scheduleLayoutAnimation();
    }

    private void startIntroAnimation() {
        fabCreate.setTranslationX(2 * getResources().getDimensionPixelOffset(R.dimen.comment_avatar_size));

        int actionbarSize = dpToPx(56);
        text.setTranslationY(-actionbarSize);
        text.animate()
                .translationY(0)
                .setDuration(ANIM_DURATION_TOOLBAR)
                .setStartDelay(500).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                startContentAnimation();
            }
        });
    }

    private void startContentAnimation() {
        fabCreate.animate()
                .translationX(0)
                .setInterpolator(new OvershootInterpolator(1.f))
                .setDuration(ANIM_DURATION_FAB)
                .start();
    }

    @Override
    public void onReportClick(int feedItem) {
        FeedContextMenuManager.getInstance().hideContextMenu();
    }

    @Override
    public void onSharePhotoClick(int feedItem) {
        FeedContextMenuManager.getInstance().hideContextMenu();
    }

    @Override
    public void onCopyShareUrlClick(int feedItem) {
        FeedContextMenuManager.getInstance().hideContextMenu();
    }

    @Override
    public void onCancelClick(int feedItem) {
        FeedContextMenuManager.getInstance().hideContextMenu();
    }

    @Override
    public void onMoreClick(View v, int position) {
        FeedContextMenuManager.getInstance().toggleContextMenuFromView(v, position, this);
    }

    @Override
    public void onCardClick(View v, int position) {
        intentToPlaylist(v, googleJson.toJson(albumList.get(position)));
    }

    public void intentToPlaylist(View v, String singer){
        int[] startingLocation = new int[2];
        v.getLocationOnScreen(startingLocation);
        startingLocation[0] += v.getWidth() / 2;

        PlaylistActivity.startUserProfileFromLocation(startingLocation, this, singer);
        overridePendingTransition(0, 0);
    }

}