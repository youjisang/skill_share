package com.immymemine.kevin.skillshare.adapter.fragment_adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.immymemine.kevin.skillshare.R;
import com.immymemine.kevin.skillshare.sampleModel.DiscussionModel;
import com.immymemine.kevin.skillshare.view.ExpandableTextView;

import java.util.List;

/**
 * Created by JisangYou on 2017-11-22.
 */

public class DiscussionsAdapter extends RecyclerView.Adapter<DiscussionsAdapter.Holder> {
    List<DiscussionModel> discussionsData;
    Context context;

    public DiscussionsAdapter(List<DiscussionModel> discussionsData, Context context) {
        this.discussionsData = discussionsData;
        this.context = context;
    }


    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item_discussions, parent, false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        // 일단 생략
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class Holder extends RecyclerView.ViewHolder {

        ExpandableTextView expandableTextView;

        public Holder(View v) {
            super(v);
            expandableTextView = v.findViewById(R.id.expandable_text_view);
            expandableTextView.setTrimLength(5);
            expandableTextView.setText(v.getContext().getText(R.string.test), TextView.BufferType.NORMAL);
        }
    }
}
