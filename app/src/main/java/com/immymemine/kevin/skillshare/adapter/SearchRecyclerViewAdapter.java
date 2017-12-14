package com.immymemine.kevin.skillshare.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.immymemine.kevin.skillshare.R;
import com.immymemine.kevin.skillshare.model.dummy.dummyData;
import com.immymemine.kevin.skillshare.utility.ConstantUtil;

import java.util.List;

/**
 * Created by JisangYou on 2017-12-11.
 */

public class SearchRecyclerViewAdapter extends RecyclerView.Adapter<SearchRecyclerViewAdapter.ViewHolder> {
    Context context;
    List<dummyData> data;
    int state;

    public SearchRecyclerViewAdapter(Context context, List<dummyData> data) {
        this.context = context;
        this.data = data;
    }

    public void setData(List<dummyData> data, int state) {
        this.data = data;
        this.state = state;
        notifyDataSetChanged();
    }



    @Override
    public int getItemViewType(int position) {
        if (state == 0)
            return ConstantUtil.FAIL_SEARCH;

        return super.getItemViewType(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == ConstantUtil.FAIL_SEARCH) {
            view = LayoutInflater.from(context).inflate(R.layout.recycler_view_item_no_search, parent, false);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.recycler_view_item_see_all_classes, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (state != 0) {
            dummyData dummy = data.get(position);
            holder.text_view_title.setText(dummy.getTitle());
            holder.text_view_tutor.setText(dummy.getTutor());
            holder.text_view_duration.setText(dummy.getDuration());
            holder.text_view_attendanceStudents.setText(dummy.getAttendanceNum());
            holder.text_view_thumbUp.setText(dummy.getThumbup());
            Glide.with(context).load(Uri.parse(dummy.getImageUrl())).apply(RequestOptions.centerCropTransform()).into(holder.image_view_profile);
        }

    }

    @Override
    public int getItemCount() {
        if (state == 0)
            return 1;
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView text_view_title, text_view_tutor, text_view_duration, text_view_thumbUp, text_view_attendanceStudents;
        ImageView image_view_profile, image_view_duration, image_view_thumUp, image_view_attendanceStudents;

        public ViewHolder(View v) {
            super(v);
            if (state != 0) {
                text_view_title = v.findViewById(R.id.text_view_title5);
                text_view_tutor = v.findViewById(R.id.text_view_tutor3);
                text_view_duration = v.findViewById(R.id.text_view_duration);
                text_view_thumbUp = v.findViewById(R.id.text_view_thumbUp);
                text_view_attendanceStudents = v.findViewById(R.id.text_view_attendanceStudents);
                image_view_profile = v.findViewById(R.id.image_view_profile);
                image_view_duration = v.findViewById(R.id.image_view_duration);
                image_view_thumUp = v.findViewById(R.id.image_view_thumUp);
                image_view_attendanceStudents = v.findViewById(R.id.image_view_attendanceStudents);
            }

        }
    }
}
