package com.immymemine.kevin.skillshare.adapter.fragment_adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.immymemine.kevin.skillshare.R;
import com.immymemine.kevin.skillshare.model.m_class.RelatedClass;

/**
 * Created by quf93 on 2017-12-05.
 */

public class RelatedClassAdapter extends RecyclerView.Adapter<RelatedClassAdapter.RelatedClassHolder> {

    Context context;
    RelatedClass[] relatedClasses;
    public RelatedClassAdapter(Context context, RelatedClass[] relatedClasses) {
        this.context = context;
        this.relatedClasses = relatedClasses;
    }

    @Override
    public RelatedClassHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_view_item_related_class, parent, false);
        return new RelatedClassHolder(view);
    }

    @Override
    public void onBindViewHolder(RelatedClassHolder holder, int position) {
        if(relatedClasses != null) {
            RelatedClass relatedClass = relatedClasses[position];
            holder.id = relatedClass.get_id();
            holder.textViewRelatedTitle.setText(relatedClass.getTitle());
            holder.textViewRelatedTutorName.setText(relatedClass.getTutorName());
            Glide.with(context).load(relatedClass.getThumbnailUrl()).into(holder.imageViewRelatedClass);
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    class RelatedClassHolder extends RecyclerView.ViewHolder {

        ImageView imageViewRelatedClass;
        TextView textViewRelatedTitle, textViewRelatedTutorName;

        String id;
        public RelatedClassHolder(View view) {
            super(view);
            imageViewRelatedClass = view.findViewById(R.id.image_view_related_class);
            textViewRelatedTitle = view.findViewById(R.id.text_view_related_title);
            textViewRelatedTutorName = view.findViewById(R.id.text_view_related_tutor_name);

            view.setOnClickListener(v -> {
                // class 이동
            });
        }
    }
}
