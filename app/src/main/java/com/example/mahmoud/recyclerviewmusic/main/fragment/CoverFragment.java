package com.example.mahmoud.recyclerviewmusic.main.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.mahmoud.recyclerviewmusic.R;
import com.squareup.picasso.Picasso;


public class CoverFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cover, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Picasso.with(getContext()).load(getArguments().getInt("image")).into((ImageView)view.findViewById(R.id.backdrop));
    }

    public static CoverFragment newInstance(int image) {

        CoverFragment f = new CoverFragment();
        Bundle b = new Bundle();
        b.putInt("image", image);

        f.setArguments(b);

        return f;
    }

}
