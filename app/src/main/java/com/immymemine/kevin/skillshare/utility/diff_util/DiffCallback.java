package com.immymemine.kevin.skillshare.utility.diff_util;

import android.support.v7.util.DiffUtil;

import java.util.List;

/**
 * Created by quf93 on 2017-12-04.
 */

public class DiffCallback<T> extends DiffUtil.Callback {

    protected List<T> oldData;
    protected List<T> newData;

    public DiffCallback(List<T> oldData, List<T> newData) {
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
        return false;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return false;
    }
}