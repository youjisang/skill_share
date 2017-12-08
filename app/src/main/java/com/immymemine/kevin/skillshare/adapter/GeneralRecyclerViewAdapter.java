package com.immymemine.kevin.skillshare.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.immymemine.kevin.skillshare.R;
import com.immymemine.kevin.skillshare.activity.ClassActivity;
import com.immymemine.kevin.skillshare.model.home.Class;

import java.util.List;

/**
 *
 *  Created by quf93 on 2017-11-17.
 */

public class GeneralRecyclerViewAdapter extends RecyclerView.Adapter<GeneralRecyclerViewAdapter.GeneralViewHolder>{
    // data 가 바뀔 일이 거의 없다 <<< 관리자가 바꿔주기 때문에
    // 바꾼 상태
    Context context;
    List<Class> classes;

    public GeneralRecyclerViewAdapter(Context context, List<Class> classes) {
        this.context = context;
        this.classes = classes;
    }

    @Override
    public GeneralViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_view_item_general, parent, false);
        return new GeneralViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GeneralViewHolder holder, int position) {
        if(classes != null) {
            Class mClass = classes.get(position);

            holder.id = mClass.get_id();
            holder.textViewTitle.setText(mClass.getTitle());
            holder.textViewTutor.setText(mClass.getTutorName());
            holder.textViewTime.setText(mClass.getDuration() + "m");
            holder.uri = mClass.getPictureUrl();
            Glide.with(context).load(Uri.parse(holder.uri))
                    .apply(RequestOptions.centerCropTransform())
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.RESOURCE))
                    .into(holder.imageView);
        }
    }

    @Override
    public int getItemCount() {
        if(classes != null)
            return classes.size();
        else
            return 5;
    }

    class GeneralViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textViewTime, textViewTitle, textViewTutor;

        String id;
        String uri;
        public GeneralViewHolder(View v) {
            super(v);

            imageView = v.findViewById(R.id.image_view_tutor);
            textViewTime = v.findViewById(R.id.text_view_time);
            textViewTitle = v.findViewById(R.id.text_view_title);
            textViewTutor = v.findViewById(R.id.text_view_profile);

            // item layout 자체에 onClick listener 를 달아준다. >>> item 어디를 클릭해도 이동
            v.setOnClickListener(view -> {
                Intent intent = new Intent(context, ClassActivity.class);
                intent.putExtra("_id", id); // data for identification
                intent.putExtra("URI", uri);
                // TODO Glide cache 된 파일을 Class Activity 로 넘어갔을 때 바로 사용하도록... 똑같은 url 이면 로딩을 하지 않는지 체크
                context.startActivity(intent);

                /* TODO 지상
                 클릭한 클래스의 고유의 id값을 프래그먼트가 있는 ClassActivity로 넘겼을때,
                  해당 id값에 포함된 데이터들을 선별적으로 프래그먼트에 넣는다.
                 */
            });
        }
    }
}

