//package com.immymemine.kevin.skillshare.adapter;
//
//
//import android.app.Activity;
//import android.content.Context;
//import android.net.Uri;
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.PopupMenu;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.request.RequestOptions;
//import com.immymemine.kevin.skillshare.R;
//import com.immymemine.kevin.skillshare.model.dummy.Saved;
//import com.immymemine.kevin.skillshare.utility.ConstantUtil;
//
//import java.util.List;
//
///**
// * Created by JisangYou on 2017-12-14.
// */
//
//public class SavedRecyclerViewAdapter extends RecyclerView.Adapter<SavedRecyclerViewAdapter.ViewHolder> {
//
//    List<Saved> savedData;
//    Context context;
//    int size;
//
//
//    public SavedRecyclerViewAdapter(Context context, List<Saved> savedData, int size) {
//        this.savedData = savedData;
//        this.context = context;
//        this.size = size;
//
//
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//
//        if (size == 0)
//            return ConstantUtil.NO_ITEM;
//        else
//            return super.getItemViewType(position);
//    }
//
//    @Override
//    public SavedRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view;
//        if (viewType == ConstantUtil.NO_ITEM)
//            view = LayoutInflater.from(context).inflate(R.layout.recycler_view_item_no_saved, parent, false);
//        else
//            view = LayoutInflater.from(context).inflate(R.layout.recycler_view_item_saved, parent, false);
//
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(SavedRecyclerViewAdapter.ViewHolder holder, int position) {
//
//        if (size != 0) {
//            Saved saved = savedData.get(position);
//            holder.title.setText(saved.getTitle());
//            holder.tutorName.setText(saved.getTutorName());
//            holder.duration.setText(saved.getDuration());
//            holder.thumbUpPercent.setText(saved.getThumbPercent());
//            holder.attendance.setText(saved.getAttendanceNum());
//            Glide.with(context).load(Uri.parse(saved.getProfileImage())).into(holder.imageUrl);
//            holder.dots.setImageResource(R.drawable.image_triple_dot);
//        }
//
//    }
//
//    @Override
//    public int getItemCount() {
//
//        if (size == 0)
//            return 1;
//        else
//            return savedData.size();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//
//        TextView title, tutorName, duration, thumbUpPercent, attendance;
//        ImageView imageUrl, dots;
//
//        public ViewHolder(View v) {
//            super(v);
//
//            if (size != 0) {
////                title = v.findViewById(R.id.text_view_title5);
//                tutorName = v.findViewById(R.id.text_view_tutor3);
//                duration = v.findViewById(R.id.text_view_duration_s);
//                thumbUpPercent = v.findViewById(R.id.text_view_thumbUp_s);
//                attendance = v.findViewById(R.id.text_view_attendanceStudents_s);
////                imageUrl = v.findViewById(R.id.image_view_profile);
//                dots = v.findViewById(R.id.image_view_dots);
//
//                dots.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View vi) {
//                        PopupMenu popupMenu = new PopupMenu(vi.getContext(), vi);
//                        ((Activity) context).getMenuInflater().inflate(R.menu.pop_up, popupMenu.getMenu());
//                        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                            @Override
//                            public boolean onMenuItemClick(MenuItem item) {
//
//                                savedData.remove(getLayoutPosition());
//                                notifyDataSetChanged();
//                                Log.e("savedData","savedData"+savedData.size());
//
//                                return true;
//                            }
//                        });
//                        popupMenu.show();
//                    }
//                });
//
//
//            }
//        }
//    }
//}
