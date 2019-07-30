package com.bz.flowlayout.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: 柏洲
 * Email:  baizhoussr@gmail.com
 * Date:   2019-06-29 23:41
 * Desc:   自定义显示tag的流式布局,支持单选、多选
 */
public class TagFlowLayout extends FlowLayout implements TagAdapter.OnDataChangedListener {

    public TagAdapter mAdapter;

    private int mMaxSelectCount;

    public void setMaxSelectCount(int maxSelectCount) {
        this.mMaxSelectCount = maxSelectCount;
    }

    public TagFlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setAdapter(TagAdapter adapter) {
        mAdapter = adapter;
        mAdapter.setOnDataChangedListener(this);
        onDataChanged();
    }

    public void onDataChanged(List<Integer> selected) {
        removeAllViews();
        TagAdapter adapter = mAdapter;
        for (int i = 0; i < adapter.getItemCount(); i++) {
            View view = adapter.createView(LayoutInflater.from(getContext()), this, i);
            adapter.bindView(view, i);
            addView(view);

            /**
             * 将已选择的item置为选中状态
             */
            if (selected != null) {
                view.setSelected(selected.contains(i));
            }

            if (view.isSelected()) {
                mAdapter.onItemSelected(view, i);
            } else {
                mAdapter.onItemUnSelected(view, i);
            }

            bindViewMethod(view, i);
        }
    }

    @Override
    public void onDataChanged() {
        onDataChanged(null);
    }

    /**
     * 单选：可以直接选择一个，当选择下一个，上一个自动取消
     * 多选：需要手动反选
     *
     * @param view
     * @param position
     */
    private void bindViewMethod(final View view, final int position) {
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.onItemViewClick(view, position);

                if (mMaxSelectCount <= 0) {
                    return;
                }

                //特殊case
                if (!v.isSelected()) {
                    if (mMaxSelectCount <= getSelectedCount()) {
                        if (mMaxSelectCount <= 1) {
                            View selectedView = getSelectedView();
                            if (selectedView != null) {
                                selectedView.setSelected(false);
                                mAdapter.onItemUnSelected(selectedView, getPositionOfSelectedView(selectedView));
                                v.setSelected(true);
                                mAdapter.onItemSelected(v, position);
                            }
                        } else {
                            mAdapter.tipForSelectMax(view, mMaxSelectCount);
                        }
                        return;
                    }
                }

                if (v.isSelected()) {
                    v.setSelected(false);
                    mAdapter.onItemUnSelected(v, position);
                } else {
                    v.setSelected(true);
                    mAdapter.onItemSelected(v, position);
                }

            }
        });
    }

    /**
     * 单选情况时，获取当前已选择的view的position
     *
     * @return position
     */
    private int getPositionOfSelectedView(View selectedView) {
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if (view == selectedView) {
                return i;
            }
        }
        return 0;
    }

    /**
     * 获取所有已选择的view的postion集合
     *
     * @return
     */
    public List<Integer> getPositionsOfSelectView() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if (view.isSelected()) {
                list.add(i);
            }
        }
        return list;
    }


    /**
     * 单选情况时，获取当前已选择的view
     *
     * @return 当前已选择view
     */
    private View getSelectedView() {
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if (view.isSelected()) {
                return view;
            }
        }
        return null;
    }

    public String getSelectedViewStr() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < getChildCount(); i++) {
            TextView view = (TextView) getChildAt(i);
            if (view.isSelected()) {
                sb.append(view.getText().toString()).append("  ");
            }
        }
        return sb.toString();
    }

    /**
     * 获取已选择view的数量
     *
     * @return 已选择总数
     */
    private int getSelectedCount() {
        int result = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if (view.isSelected()) {
                result++;
            }
        }
        return result;
    }
}
