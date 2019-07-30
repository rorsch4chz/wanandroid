package com.bz.flowlayout

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import cn.scshuimukeji.comm.ui.multiple.MultipleViewHolder
import cn.scshuimukeji.comm.ui.multiple.MultipleViewManager
import com.bz.flowlayout.view.TagAdapter
import com.bz.flowlayout.view.TagFlowLayout

/**
 * Author by 柏洲
 * Email 422166210@qq.com
 * Date on 2019/7/26 17:16.
 * Describe  精选商城筛选条件搜索item manager
 */
class FilterBeanItemManager : MultipleViewManager<FilterBean>() {
    override fun getLayoutResId(layoutCode: Int): Int {
        return if (layoutCode == 0x110) {
            R.layout.item_mall_filter
        } else {
            R.layout.item_mall_type
        }
    }

    override fun onBindViewHolder(holder: MultipleViewHolder, position: Int, data: FilterBean) {
        val nameTv = holder.getView<TextView>(R.id.filter_name)
        val hideImg = holder.getView<ImageView>(R.id.hide_img)
        val tagFlowLayout = holder.getView<TagFlowLayout>(R.id.filter_tag_flow_layout)

        nameTv.text = data.filterName
        if (data.filterType != "area" && data.attributes.isNotEmpty()) {
            hideImg.visibility = if (data.attributes.size > 6) View.VISIBLE else View.GONE

            val tagAdapter = object : TagAdapter() {
                override fun createView(inflater: LayoutInflater?, parent: ViewGroup?, position: Int): View {
                    return inflater!!.inflate(R.layout.item_filter_select_tag, parent, false)
                }

                override fun getItemCount(): Int {
                    return data.attributes.size
                }

                override fun bindView(view: View?, position: Int) {
                    val tag = view!!.findViewById<TextView>(R.id.tag_tv)
                    tag.text = data.attributes[position]
                }

                override fun onItemSelected(view: View?, position: Int) {
                    val tag = view!!.findViewById<TextView>(R.id.tag_tv)
                    tag.setTextColor(Color.parseColor("#4A90FA"))
                }

                override fun onItemUnSelected(view: View?, position: Int) {
                    val tag = view!!.findViewById<TextView>(R.id.tag_tv)
                    tag.setTextColor(Color.parseColor("#222222"))
                }
            }
            tagFlowLayout.setMaxSelectCount(999)
            tagFlowLayout.setAdapter(tagAdapter)

            hideImg.setOnClickListener {
                if (tagFlowLayout.maxLines == 2) {
                    tagFlowLayout.maxLines = 0
                    hideImg.setImageResource(R.drawable.filter_hide)
                } else {
                    tagFlowLayout.maxLines = 2
                    hideImg.setImageResource(R.drawable.filter_open)
                }
                tagFlowLayout.onDataChanged(tagFlowLayout.positionsOfSelectView)
            }
        }

    }

}