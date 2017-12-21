package com.immymemine.kevin.skillshare.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.immymemine.kevin.skillshare.R;

/**
 * Created by JisangYou on 2017-11-29.
 */

public class ProfileRecyclerViewAdapter extends RecyclerView.Adapter<ProfileRecyclerViewAdapter.Holder> {


    @Override
    public ProfileRecyclerViewAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item_tutor, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(ProfileRecyclerViewAdapter.Holder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public class Holder extends RecyclerView.ViewHolder {
        public Holder(View itemView) {
            super(itemView);
        }
    }
}
