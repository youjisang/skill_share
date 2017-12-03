package com.immymemine.kevin.skillshare.utility;

import android.support.v7.util.DiffUtil;

import com.immymemine.kevin.skillshare.model.online_class.Discussion;

import java.util.List;

/**
 * Created by quf93 on 2017-12-03.
 */

public class DiscussionDiffCallback extends DiffUtil.Callback {

    private List<Discussion> oldData;
    private List<Discussion> newData;

    public DiscussionDiffCallback(List<Discussion> oldData, List<Discussion> newData) {
        this.oldData = oldData;
        this.newData = newData;
    }

    @Override
    public int getOldListSize() {
        return oldData.size();
    }

    @Override
    public int getNewListSize() {
        return newData.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldData.get(oldItemPosition).getId().equals(newData.get(newItemPosition).getId());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        Discussion oldItem = oldData.get(oldItemPosition);
        Discussion newItem = newData.get(newItemPosition);

        return oldItem.getLike() == newItem.getLike() &&
                oldItem.getReDiscussions().length == newItem.getReDiscussions().length;
    }
}
