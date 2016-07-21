package com.example.mahmoud.recyclerviewmusic.main.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.mahmoud.recyclerviewmusic.R;
import com.example.mahmoud.recyclerviewmusic.Utils;

/**
 * Created by froger_mcs on 15.12.14.
 */
public class FeedContextMenu extends LinearLayout {
    private static final int CONTEXT_MENU_WIDTH = Utils.dpToPx(200);

    private int feedItem = -1;
    Button btnReport, btnSharePhoto, btnCopyShareUrl, btnCancel;

    private OnFeedContextMenuItemClickListener onItemClickListener;

    public FeedContextMenu(Context context) {
        super(context);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_context_menu, this, true);
        setBackgroundResource(R.drawable.bg_container_shadow);
        setOrientation(VERTICAL);
        setLayoutParams(new LayoutParams(CONTEXT_MENU_WIDTH, ViewGroup.LayoutParams.WRAP_CONTENT));
        btnCancel = (Button)findViewById(R.id.btnCancel);
        btnSharePhoto = (Button)findViewById(R.id.btnSharePhoto);
        btnReport = (Button)findViewById(R.id.btnReport);
        btnCopyShareUrl = (Button)findViewById(R.id.btnCopyShareUrl);
        btnCancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onCancelClick();
            }
        });
        btnCopyShareUrl.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onCopyShareUrlClick();
            }
        });
        btnSharePhoto.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onSharePhotoClick();
            }
        });
        btnReport.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onReportClick();
            }
        });
    }

    public void bindToItem(int feedItem) {
        this.feedItem = feedItem;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    public void dismiss() {
        ((ViewGroup) getParent()).removeView(FeedContextMenu.this);
    }

    public void onReportClick() {
        if (onItemClickListener != null) {
            onItemClickListener.onReportClick(feedItem);
        }
    }

    public void onSharePhotoClick() {
        if (onItemClickListener != null) {
            onItemClickListener.onSharePhotoClick(feedItem);
        }
    }

    public void onCopyShareUrlClick() {
        if (onItemClickListener != null) {
            onItemClickListener.onCopyShareUrlClick(feedItem);
        }
    }

    public void onCancelClick() {
        if (onItemClickListener != null) {
            onItemClickListener.onCancelClick(feedItem);
        }
    }

    public void setOnFeedMenuItemClickListener(OnFeedContextMenuItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()){
//            case R.id.btnCancel:
//                onCancelClick();
//                break;
//            case R.id.btnCopyShareUrl:
//                onCopyShareUrlClick();
//                break;
//            case R.id.btnReport:
//                onReportClick();
//                break;
//            case R.id.btnSharePhoto:
//                onSharePhotoClick();
//                break;
//        }
//    }

    public interface OnFeedContextMenuItemClickListener {
         void onReportClick(int feedItem);

         void onSharePhotoClick(int feedItem);

         void onCopyShareUrlClick(int feedItem);

         void onCancelClick(int feedItem);
    }
}