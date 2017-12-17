package com.immymemine.kevin.skillshare.adapter;


import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.immymemine.kevin.skillshare.R;
import com.immymemine.kevin.skillshare.model.dummy.Saved;

import java.util.List;

/**
 * Created by JisangYou on 2017-12-14.
 */

public class SavedRecyclerViewAdapter extends RecyclerView.Adapter<SavedRecyclerViewAdapter.ViewHolder> {

    List<Saved> savedData;
    Context context;

    public SavedRecyclerViewAdapter(List<Saved> savedData, Context context) {
        this.savedData = savedData;
        this.context = context;
    }


    @Override
    public SavedRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_view_item_saved, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SavedRecyclerViewAdapter.ViewHolder holder, int position) {
//        Saved saved = savedData.get(position);
//        holder.title.setText(saved.getTitle());
//        holder.tutorName.setText(saved.getTutorName());
//        holder.duration.setText(saved.getDuration());
//        holder.thumbUpPercent.setText(saved.getThumbPercent());
//        holder.attendance.setText(saved.getAttendanceNum());
//        Glide.with(context).load(Uri.parse(saved.getProfileImage())).into(holder.imageUrl);
//        holder.delete.setImageResource(R.drawable.image_triple_dot);


    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title, tutorName, duration, thumbUpPercent, attendance;
        ImageView imageUrl, dots;

        public ViewHolder(View v) {
            super(v);
            title = v.findViewById(R.id.text_view_title5);
            tutorName = v.findViewById(R.id.text_view_tutor3);
            duration = v.findViewById(R.id.text_view_duration_s);
            thumbUpPercent = v.findViewById(R.id.text_view_thumbUp_s);
            attendance = v.findViewById(R.id.text_view_attendanceStudents_s);
            imageUrl = v.findViewById(R.id.image_view_profile);
            dots = v.findViewById(R.id.image_view_dots);

            dots.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu p = new PopupMenu(v.getContext(), v);
                    ((Activity) context).getMenuInflater().inflate(R.menu.pop_up,p.getMenu());
                    p.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            Toast.makeText(context,
                                    "removed",
                                    Toast.LENGTH_SHORT).show();

                            return false;
                        }
                    });
                    p.show();
                }
            });

        }
    }
}
