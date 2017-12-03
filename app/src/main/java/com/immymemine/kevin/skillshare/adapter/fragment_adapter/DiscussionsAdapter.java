package com.immymemine.kevin.skillshare.adapter.fragment_adapter;

import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.immymemine.kevin.skillshare.R;
import com.immymemine.kevin.skillshare.model.online_class.Discussion;
import com.immymemine.kevin.skillshare.utility.DiscussionDiffCallback;
import com.immymemine.kevin.skillshare.view.ExpandableTextView;

import java.util.List;

/**
 * Created by JisangYou on 2017-11-22.
 */

public class DiscussionsAdapter extends RecyclerView.Adapter<DiscussionsAdapter.Holder> {

    List<Discussion> discussions;
    public DiscussionsAdapter(List<Discussion> discussions) {
        this.discussions = discussions;
    }

    public void updateData(List<Discussion> discussions) {
        DiscussionDiffCallback callback = new DiscussionDiffCallback(this.discussions, discussions);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);

        this.discussions.clear();
        this.discussions.addAll(discussions);
        result.dispatchUpdatesTo(this);
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item_discussions, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        // 일단 생략
    }

    @Override
    public int getItemCount() {
        return discussions.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        ExpandableTextView expandable_text_view;

        public Holder(View v) {
            super(v);
            expandable_text_view = v.findViewById(R.id.expandable_text_view);
            expandable_text_view.setTrimLength(5);
            expandable_text_view.setText(v.getContext().getText(R.string.test), TextView.BufferType.NORMAL);
        }
    }
}
