package com.immymemine.kevin.skillshare.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.immymemine.kevin.skillshare.R;

/**
 * Created by quf93 on 2017-11-17.
 */

public class GeneralRecyclerViewAdapter extends RecyclerView.Adapter<GeneralRecyclerViewAdapter.GeneralViewHolder>{

    public GeneralRecyclerViewAdapter() {

    }

    @Override
    public GeneralViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item_general, parent, false);
        return new GeneralViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GeneralViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    class GeneralViewHolder extends RecyclerView.ViewHolder {
        LinearLayout recyclerViewItemGroup;
        ImageView imageView;
        TextView textViewTime, textViewTitle, textViewAuthor;

        public GeneralViewHolder(View v) {
            super(v);
            recyclerViewItemGroup = v.findViewById(R.id.recycler_view_item_general);
            imageView = v.findViewById(R.id.imageView);
            textViewTime = v.findViewById(R.id.text_view_time);
            textViewTitle = v.findViewById(R.id.text_view_title);
            textViewAuthor = v.findViewById(R.id.text_view_author);

            // item layout 자체에 onClick listener 를 달아준다. >>> item 어디를 클릭해도 이동
            recyclerViewItemGroup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // click 시 해당 클래스 화면으로 이동 처리
                }
            });
        }
    }
}

