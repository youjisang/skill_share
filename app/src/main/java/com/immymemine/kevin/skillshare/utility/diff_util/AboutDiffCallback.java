package com.immymemine.kevin.skillshare.utility.diff_util;

import com.immymemine.kevin.skillshare.model.m_class.About;

import java.util.List;

/**
 * Created by quf93 on 2017-12-04.
 */

public class AboutDiffCallback extends DiffCallback<About> {

    public AboutDiffCallback(List oldData, List newData) {
        super(oldData, newData);
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        // TODO custom
        return super.areItemsTheSame(oldItemPosition, newItemPosition);
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return super.areContentsTheSame(oldItemPosition, newItemPosition);
    }
}
