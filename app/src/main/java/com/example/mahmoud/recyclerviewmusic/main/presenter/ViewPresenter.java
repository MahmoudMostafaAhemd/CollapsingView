package com.example.mahmoud.recyclerviewmusic.main.presenter;

import com.example.mahmoud.recyclerviewmusic.main.model.Album;

import java.util.List;

/**
 * Created by mahmoud on 19-Jul-16.
 */
public interface ViewPresenter {
    void onPostsLoad(List<Album> images);
}
