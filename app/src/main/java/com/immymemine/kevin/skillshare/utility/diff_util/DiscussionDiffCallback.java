package com.immymemine.kevin.skillshare.utility.diff_util;

import com.immymemine.kevin.skillshare.model.m_class.Discussion;
import com.immymemine.kevin.skillshare.model.m_class.Reply;

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
        List<Reply> oldReplies = oldData.get(oldItemPosition).getReplies();
        List<Reply> newReplies = newData.get(newItemPosition).getReplies();

        if(oldReplies == null && newReplies == null)
            return true;
        else if(oldReplies == null && newReplies != null)
            return false;
        else
            return oldReplies.size() == newReplies.size();
    }
}