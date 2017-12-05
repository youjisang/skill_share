package com.immymemine.kevin.skillshare.utility.diff_util;

import com.immymemine.kevin.skillshare.model.model_class.Discussion;

import java.util.List;

/**
 * Created by quf93 on 2017-12-03.
 */

public class DiscussionDiffCallback extends DiffCallback<Discussion> {

    public DiscussionDiffCallback(List<Discussion> oldData, List<Discussion> newData) {
        super(oldData, newData);
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldData.get(oldItemPosition).get_id().equals(newData.get(newItemPosition).get_id());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        Discussion oldItem = oldData.get(oldItemPosition);
        Discussion newItem = newData.get(newItemPosition);

        return oldItem.getLike() == newItem.getLike() &&
                oldItem.getReDiscussions().length == newItem.getReDiscussions().length;
    }
}
