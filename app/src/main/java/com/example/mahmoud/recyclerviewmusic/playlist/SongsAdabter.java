package com.example.mahmoud.recyclerviewmusic.playlist;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mahmoud.recyclerviewmusic.R;
import com.example.mahmoud.recyclerviewmusic.main.model.Album;

/**
 * Created by mahmoud on 21-Jul-16.
 */
public class SongsAdabter extends RecyclerView.Adapter<SongsAdabter.ViewHolder>{

    Album album;
    public SongsAdabter (Album album){
        this.album = album;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.album_card,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return album.getNumOfSongs();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(View itemView) {
            super(itemView);

        }
    }
}
