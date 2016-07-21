package com.example.mahmoud.recyclerviewmusic.main.presenter;

import com.example.mahmoud.recyclerviewmusic.R;
import com.example.mahmoud.recyclerviewmusic.main.model.Album;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mahmoud on 19-Jul-16.
 */
public class MainPresenterImpl implements MainPresenter{

    ViewPresenter viewPresenter;
    private List<Album> albumList;

    public MainPresenterImpl(ViewPresenter viewPresenter){
        this.viewPresenter = viewPresenter;
        albumList = new ArrayList<>();
    }
    @Override
    public void loadPosts() {
        viewPresenter.onPostsLoad(prepareAlbums());
    }

    private List<Album> prepareAlbums() {
        int[] covers = new int[]{
                R.drawable.album1,
                R.drawable.album2,
                R.drawable.album3,
                R.drawable.album4,
                R.drawable.album5,
                R.drawable.album6,
                R.drawable.album7,
                R.drawable.album8,
                R.drawable.album9,
                R.drawable.album10,
                R.drawable.album11
        };

        Album a = new Album("True Romance", 13, covers[0]);
        albumList.add(a);

        a = new Album("Xscpae", 8, covers[1]);
        albumList.add(a);

        a = new Album("Maroon 5", 11, covers[2]);
        albumList.add(a);

        a = new Album("Born to Die", 12, covers[3]);
        albumList.add(a);

        a = new Album("Honeymoon", 14, covers[4]);
        albumList.add(a);

        a = new Album("I Need a Doctor", 1, covers[5]);
        albumList.add(a);

        a = new Album("Loud", 11, covers[6]);
        albumList.add(a);

        a = new Album("Legend", 14, covers[7]);
        albumList.add(a);

        a = new Album("Hello", 11, covers[8]);
        albumList.add(a);

        a = new Album("Greatest Hits", 17, covers[9]);
        albumList.add(a);

        a = new Album("Amr Diab", 11, covers[10]);
        albumList.add(a);


        return albumList;
    }
}
