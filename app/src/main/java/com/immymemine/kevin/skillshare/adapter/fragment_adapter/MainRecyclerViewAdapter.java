package com.immymemine.kevin.skillshare.adapter.fragment_adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.immymemine.kevin.skillshare.R;

/**
 * Created by quf93 on 2017-11-27.
 */

public class MainRecyclerViewAdapter extends RecyclerView.Adapter<MainRecyclerViewAdapter.MainHolder> {

    @Override
    public MainHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item_discover, parent, false);
        return new MainHolder(view);
    }

    @Override
    public void onBindViewHolder(MainHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 3;
    }

    class MainHolder extends RecyclerView.ViewHolder {
        ImageView imageViewFeaturedClass;
        TextView textViewTitle, textViewTutor;

        public MainHolder(View v) {
            super(v);
            imageViewFeaturedClass = v.findViewById(R.id.image_view_featured_class);
            textViewTitle = v.findViewById(R.id.text_view_title);
            textViewTutor = v.findViewById(R.id.text_view_tutor);

            v.setOnClickListener(view -> {

            });
        }
    }
}
