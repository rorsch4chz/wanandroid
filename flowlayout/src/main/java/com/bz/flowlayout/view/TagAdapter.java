package com.bz.flowlayout.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Author: 柏洲
 * Email:  baizhoussr@gmail.com
 * Date:   2019-06-29 23:50
 * Desc:
 */
public abstract class TagAdapter {

    public abstract int getItemCount();

    public abstract void bindView(View view, int position);

    public abstract View createView(LayoutInflater inflater, ViewGroup parent, int position);

    public void onItemViewClick(View view, int position) {

    }

    public void tipForSelectMax(View view, int maxSelectCount) {

    }

    public void onItemSelected(View view, int position) {

    }

    public void onItemUnSelected(View view, int position) {

    }

    public interface OnDataChangedListener {
        void onDataChanged();
    }

    public OnDataChangedListener onDataChangedListener;

    public void setOnDataChangedListener(OnDataChangedListener onDataChangedListener) {
        this.onDataChangedListener = onDataChangedListener;
    }

    public void notifyDataSetChanged() {
        if (onDataChangedListener != null) {
            onDataChangedListener.onDataChanged();
        }
    }
}
