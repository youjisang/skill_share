package com.immymemine.kevin.skillshare.adapter;

import android.content.Context;
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
    // data 가 바뀔 일이 거의 없다 <<< 관리자가 바꿔주기 때문에
    Context context;
    public GeneralRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    @Override
    public GeneralViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_view_item_general, parent, false);
        return new GeneralViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GeneralViewHolder holder, int position) {
        // OnlineClass class = data.get(position);

        // holder.textViewTitle.setText(/* title */);
        // holder.textViewAuthor.setText(/* tutor */);
        // holder.textViewTime.setText(/* String type time */);
        // Glide.with(context).load(/* Uri */).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        // return data.size();
        return 5;
    }

    class GeneralViewHolder extends RecyclerView.ViewHolder {
        LinearLayout recyclerViewItemGroup;
        ImageView imageView;
        TextView textViewTime, textViewTitle, textViewTutor;

        public GeneralViewHolder(View v) {
            super(v);
            recyclerViewItemGroup = v.findViewById(R.id.recycler_view_item_general);
            imageView = v.findViewById(R.id.imageView);
            textViewTime = v.findViewById(R.id.text_view_time);
            textViewTitle = v.findViewById(R.id.text_view_title);
            textViewTutor = v.findViewById(R.id.text_view_author);

            // item layout 자체에 onClick listener 를 달아준다. >>> item 어디를 클릭해도 이동
            recyclerViewItemGroup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // TODO click 시 해당 클래스 화면으로 이동 처리
                }
            });
        }
    }
}

