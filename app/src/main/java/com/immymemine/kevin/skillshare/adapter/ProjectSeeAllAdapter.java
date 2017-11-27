package com.immymemine.kevin.skillshare.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.immymemine.kevin.skillshare.R;
import com.immymemine.kevin.skillshare.sampleModel.ProjectSeeAllModel;

import java.util.List;

/**
 * Created by JisangYou on 2017-11-24.
 */

public class ProjectSeeAllAdapter extends RecyclerView.Adapter<ProjectSeeAllAdapter.Holder> {
    List<ProjectSeeAllModel> seeAllProjectData;
    Context context;

    public ProjectSeeAllAdapter(List<ProjectSeeAllModel> seeAllProjectData, Context context) {
        this.seeAllProjectData = seeAllProjectData;
        this.context = context;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_projects, parent, false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public class Holder extends RecyclerView.ViewHolder {

        TextView heartNumTextView, seeAllContentNameTextView, seeAllAuthorTextView;
        ImageView seeAllProjectImageView;

        public Holder(View itemView) {
            super(itemView);
            seeAllProjectImageView = itemView.findViewById(R.id.seeAllProjectImageView);
            heartNumTextView = itemView.findViewById(R.id.heartNumTextView);
            seeAllContentNameTextView = itemView.findViewById(R.id.seeAllContentNameTextView);
            seeAllAuthorTextView = itemView.findViewById(R.id.seeAllAuthorTextView);

        }
    }
}
