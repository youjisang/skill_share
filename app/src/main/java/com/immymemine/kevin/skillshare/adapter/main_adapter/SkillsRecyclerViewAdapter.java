package com.immymemine.kevin.skillshare.adapter.main_adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.immymemine.kevin.skillshare.R;

import java.util.List;

/**
 * Created by quf93 on 2017-12-14.
 */

public class SkillsRecyclerViewAdapter extends RecyclerView.Adapter<SkillsRecyclerViewAdapter.Holder> {

    List<String> skills;
    Context context;

    public SkillsRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    public SkillsRecyclerViewAdapter(Context context, List<String> skills) {
        this.context = context;
        this.skills = skills;
    }

    public void update(List<String> skills) {
        this.skills = skills;
        notifyDataSetChanged();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_view_item_skills, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        if(skills != null)
            holder.textViewSkill.setText(skills.get(position));
    }

    @Override
    public int getItemCount() {
        if(skills != null)
            return skills.size();
        return 0;
    }

    class Holder extends RecyclerView.ViewHolder {

        TextView textViewSkill;

        public Holder(View view) {
            super(view);
            textViewSkill = view.findViewById(R.id.text_view_skill);
        }
    }
}
