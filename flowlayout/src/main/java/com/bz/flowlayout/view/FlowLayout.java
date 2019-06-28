package com.bz.flowlayout.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义流式布局
 */
public class FlowLayout extends ViewGroup {

    private List<List<View>> mAllViews = new ArrayList<>();
    private List<Integer> mLineHeights = new ArrayList<>();

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mAllViews.clear();
        mLineHeights.clear();

        // 流式布局，宽度必须是确定值
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int cCount = getChildCount();

        //行高和行宽
        int lineWidth = 0;
        int lineHeight = 0;

        //布局高度，受所有子view的高度之和影响
        int height = 0;

        List<View> mLineViews = new ArrayList<>();

        //拿到当前所有 child 需要占据的高度，设置给我们的容器
        for (int i = 0; i < cCount; i++) {
            View child = getChildAt(i);

            if (child.getVisibility() == GONE) {
                continue;
            }

            measureChild(child, widthMeasureSpec, heightMeasureSpec);

            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            int cWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            int cHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;

            //当前view宽度之和大于FlowLayout宽度时，换行处理
            if (lineWidth + cWidth > widthSize) {
                height += lineHeight;

                mLineHeights.add(lineHeight);

                //将每一行的view集合添加到所有view集合
                mAllViews.add(mLineViews);
                mLineViews = new ArrayList<>();
                mLineViews.add(child);

                //重置
                lineWidth = cWidth;
                lineHeight = cHeight;
            } else {
                lineWidth += cWidth;
                lineHeight = Math.max(lineHeight, cHeight);

                mLineViews.add(child);
            }

            //最后一行时
            if (i == cCount - 1) {
                height += lineHeight;
                mLineHeights.add(lineHeight);
                mAllViews.add(mLineViews);
            }
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            height = Math.min(heightSize, height);
        }
        setMeasuredDimension(widthSize, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int left = 0;
        int top = 0;
        int lineCount = mAllViews.size();

        for (int i = 0; i < lineCount; i++) {
            List<View> lineViews = mAllViews.get(i);
            int lineHeight = mLineHeights.get(i);
            for (int j = 0; j < lineViews.size(); j++) {

                View child = lineViews.get(j);
                MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
                int lc = left + lp.leftMargin;
                int tc = top + lp.topMargin;
                int rc = lc + child.getMeasuredWidth();
                int bc = tc + child.getMeasuredHeight();
                child.layout(lc, tc, rc, bc);

                left += child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            }
            left = 0;
            top += lineHeight;
        }
    }

    //add view时 child 未设置lp时调用
    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    //inflate
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }


    //add view时 child设置了LayoutParams
    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    //检查
    @Override
    protected boolean checkLayoutParams(LayoutParams p) {
        return p instanceof MarginLayoutParams;
    }
}
