package com.immymemine.kevin.skillshare.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by quf93 on 2017-11-20.
 */

public class SeeAllRecyclerViewAdapter extends RecyclerView.Adapter<SeeAllRecyclerViewAdapter.SeeAllHolder> {

    @Override
    public SeeAllHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(SeeAllHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class SeeAllHolder extends RecyclerView.ViewHolder {

        public SeeAllHolder(View itemView) {
            super(itemView);
        }
    }
}