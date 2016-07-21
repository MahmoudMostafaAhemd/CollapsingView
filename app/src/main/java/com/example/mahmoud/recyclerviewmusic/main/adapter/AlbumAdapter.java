package com.example.mahmoud.recyclerviewmusic.main.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mahmoud.recyclerviewmusic.R;
import com.example.mahmoud.recyclerviewmusic.main.model.Album;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mahmoud on 19-Jul-16.
 */
public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder> {

    public static final String ACTION_LIKE_BUTTON_CLICKED = "action_like_button_button";
    public static final String ACTION_UNLIKE_BUTTON_CLICKED = "action_unlike_button_button";
    public static final String ACTION_LIKE_IMAGE_CLICKED = "action_like_image_button";
    public static final String ACTION_UNLIKE_IMAGE_CLICKED = "action_unlike_image_button";
    List<Album> albumList;
    Context context;
    OnFeedItemClickListener onFeedItemClickListener;

    public AlbumAdapter(Context context){
        albumList = new ArrayList<>();
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.album_card,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        setupClickableViews(view, viewHolder);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Picasso.with(context).load(albumList.get(position).getThumbnail()).into(holder.thumbnail);
        holder.title.setText(albumList.get(position).getName());
        holder.count.setText(albumList.get(position).getNumOfSongs() + " songs");
        if(albumList.get(position).isLiked()){
            Picasso.with(context).load(R.drawable.ic_heart_red).into(holder.btnLike);
        }else {
            Picasso.with(context).load(R.drawable.ic_heart_outline_grey).into(holder.btnLike);
        }
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }

    public void insertItems(List<Album> albumList){
        this.albumList = albumList;
        notifyDataSetChanged();
    }

    public void setOnFeedItemClickListener(OnFeedItemClickListener onFeedItemClickListener) {
        this.onFeedItemClickListener = onFeedItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView thumbnail, overflow;
        TextView title, count;
        CardView cardView;
        public ImageButton btnLike;
        public ImageView ivLike;
        public View vBgLike;
        public ViewHolder(View itemView) {
            super(itemView);
            thumbnail = (ImageView)itemView.findViewById(R.id.thumbnail);
            overflow = (ImageView)itemView.findViewById(R.id.overflow);
            title = (TextView)itemView.findViewById(R.id.title);
            count = (TextView)itemView.findViewById(R.id.count);
            cardView = (CardView)itemView.findViewById(R.id.card_view);
            btnLike = (ImageButton)itemView.findViewById(R.id.btnLike);
            ivLike = (ImageView)itemView.findViewById(R.id.ivLike);
            vBgLike = itemView.findViewById(R.id.vBgLike);
        }
    }

    private void setupClickableViews(final View view, final ViewHolder cellFeedViewHolder) {

        cellFeedViewHolder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFeedItemClickListener.onMoreClick(v, cellFeedViewHolder.getAdapterPosition());
            }
        });

        cellFeedViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFeedItemClickListener.onCardClick(v,cellFeedViewHolder.getAdapterPosition());
            }
        });

        cellFeedViewHolder.btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = cellFeedViewHolder.getAdapterPosition();
//                feedItems.get(adapterPosition).likesCount++;
                if(!albumList.get(adapterPosition).isLiked()){
                    notifyItemChanged(adapterPosition, ACTION_LIKE_BUTTON_CLICKED);
                }else {
                    notifyItemChanged(adapterPosition, ACTION_UNLIKE_BUTTON_CLICKED);
                }

                albumList.get(adapterPosition).setLiked(!albumList.get(adapterPosition).isLiked());

//                if (context instanceof MainActivity) {
//                    ((MainActivity) context).showLikedSnackbar();
//                }
            }
        });

        cellFeedViewHolder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = cellFeedViewHolder.getAdapterPosition();
                if(!albumList.get(adapterPosition).isLiked())
                    notifyItemChanged(adapterPosition, ACTION_LIKE_IMAGE_CLICKED);
                else {
                    notifyItemChanged(adapterPosition, ACTION_UNLIKE_IMAGE_CLICKED);
                }
                albumList.get(adapterPosition).setLiked(!albumList.get(adapterPosition).isLiked());
            }
        });

    }


    public interface OnFeedItemClickListener {

        void onMoreClick(View v, int position);
        void onCardClick(View v, int position);
//        void onLikeBtnClick(View v, int position);
    }
    
}
