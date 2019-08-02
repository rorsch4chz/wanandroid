package com.bz.flowlayout.view

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import com.bz.flowlayout.R
import com.bz.flowlayout.bean.SortBean
import com.bz.flowlayout.bean.SortStatus

/**
 * Author by 柏洲
 * Email 422166210@qq.com
 * Date on 2019/8/2 14:16.
 * Describe 自定义排序ViewGroup
 */
class SortViewGroup(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : ConstraintLayout(context, attrs, defStyleAttr) {
    constructor(context: Context?) : this(context, attrs = null, defStyleAttr = 0)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs = attrs, defStyleAttr = 0)

    var allData = mutableListOf<SortBean>()
    var allViews = mutableListOf<View>()

    lateinit var onConditionSelectedListener: OnConditionSelectedListener

    //价格降序图标
    private val descDrawable = ResourcesCompat.getDrawable(resources, R.drawable.ic_default_price_down, null)
    //价格升序图标
    private val ascDrawable = ResourcesCompat.getDrawable(resources, R.drawable.ic_default_price_up, null)
    //默认图标
    private val normalDrawable = ResourcesCompat.getDrawable(resources, R.drawable.ic_default_prict_sort, null)

    init {
        descDrawable?.setBounds(0, 0, descDrawable.minimumWidth, descDrawable.minimumHeight)
        ascDrawable?.setBounds(0, 0, ascDrawable.minimumWidth, ascDrawable.minimumHeight)
        normalDrawable?.setBounds(0, 0, normalDrawable.minimumWidth, normalDrawable.minimumHeight)
    }


    /**
     * 设置数据，根据数据生成排序子view
     */
    fun setData(data: MutableList<SortBean>) {
        allData = data
        allData.forEach {
            val textView = TextView(context)
            textView.text = it.name
            textView.textSize = 14f
            textView.gravity = Gravity.CENTER
            textView.compoundDrawablePadding = 16
            // TODO dp2px
            textView.setPadding(30, 0, 30, 0)

            textView.setOnClickListener { itView ->
                allViews.forEach { view ->

                    val indexData = allData[allViews.indexOf(view)]
                    //当前已选定view
                    if (view == itView) {
                        when (indexData.defaultSortStatus) {
                            //已是升序，改为降序
                            SortStatus.ASC -> indexData.defaultSortStatus = SortStatus.DESC

                            //已是降序，改为升序
                            SortStatus.DESC -> indexData.defaultSortStatus = SortStatus.ASC

                            //未排序，改为升序
                            SortStatus.UNSORTED -> indexData.defaultSortStatus = SortStatus.ASC

                            //已选择，改为未选择 TODO  如果已选择且不能切换为未选中，直接return
                            SortStatus.SELECTED -> {
                                //如果未选中的value 为空，则不能切换到未选中状态
                                if (indexData.statusValues[1].isEmpty()) {
                                    return@setOnClickListener
                                } else {
                                    indexData.defaultSortStatus = SortStatus.UNSELECTED
                                }
                            }

                            //未选择，改为已选择
                            SortStatus.UNSELECTED -> indexData.defaultSortStatus = SortStatus.SELECTED
                        }
//                        onConditionSelectedListener.onConditionSelected(indexData.statusValues[indexData.defaultSortStatus.value])
                        Log.e("SortViewGroup", "sort:${indexData.statusValues[indexData.defaultSortStatus.value]}")
                    } else {
                        when (indexData.defaultSortStatus) {
                            SortStatus.ASC,
                            SortStatus.DESC,
                            SortStatus.UNSORTED -> indexData.defaultSortStatus = SortStatus.UNSORTED
                            SortStatus.SELECTED,
                            SortStatus.UNSELECTED -> indexData.defaultSortStatus = SortStatus.UNSELECTED
                        }
                    }
                }
                updateViews()
            }
            allViews.add(textView)
        }
        updateViews()
    }

    /**
     * 点击后更新TextView状态
     */
    private fun updateViews() {
        allData.forEach {
            var textColor = Color.parseColor("#4A90FA")
            var textDrawable: Drawable? = normalDrawable
            //根据默认状态添加TextView
            when (it.defaultSortStatus) {
                SortStatus.ASC -> textDrawable = ascDrawable
                SortStatus.DESC -> textDrawable = descDrawable
                SortStatus.UNSORTED -> {
                    textDrawable = normalDrawable
                    textColor = Color.parseColor("#222222")
                }
                SortStatus.SELECTED -> textDrawable = null
                SortStatus.UNSELECTED -> {
                    textDrawable = null
                    textColor = Color.parseColor("#222222")
                }
            }
            (allViews[allData.indexOf(it)] as TextView).setTextColor(textColor)
            (allViews[allData.indexOf(it)] as TextView).setCompoundDrawables(null, null, textDrawable, null)
        }
        removeAllViews()
        setViews(allViews)

    }

    /**
     * 将view添加到容器中
     */
    private fun setViews(allViews: MutableList<View>) {
        allViews.forEach {
            it.id = View.generateViewId()
        }
        allViews.forEach {
            val index = allViews.indexOf(it)
            val lp = LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT)
            when {
                allViews.size == 1 -> {
                    lp.leftToLeft = id
                    lp.rightToRight = id
                }
                index == 0 -> {
                    lp.leftToLeft = id
                    lp.rightToLeft = allViews[1].id
                }
                index == allViews.size - 1 -> {
                    lp.leftToRight = allViews[index - 1].id
                    lp.rightToRight = id
                }
                else -> {
                    lp.leftToRight = allViews[index - 1].id
                    lp.rightToLeft = allViews[index + 1].id
                }
            }
            it.layoutParams = lp
            addView(it)
        }
    }
}

interface OnConditionSelectedListener {
    /**
     * 当选择单个筛选条件
     */
    fun onConditionSelected(conditions: String)
}
