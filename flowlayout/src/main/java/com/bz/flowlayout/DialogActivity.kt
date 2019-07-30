package com.bz.flowlayout

import android.app.Dialog
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import cn.scshuimukeji.comm.ui.multiple.MultipleLayoutSupport
import cn.scshuimukeji.comm.ui.multiple.MultipleViewAdapter
import kotlinx.android.synthetic.main.dialog_activity.*

/**
 * filterName:筛选属性
 * filterType:单选，多选，填空
 *
 */
data class FilterBean(var filterName: String, var filterType: String, var attributes: List<String>) : MultipleLayoutSupport {
    var selectType = 0x110
    var typeType = 0x119
    override fun getMultipleLayoutCode(): Int {
        return when (filterType) {
            "multipleSelect", "singleSelect" -> selectType
            "area" -> typeType
            else -> selectType
        }
    }
}


/**
 * Author by 柏洲
 * Email 422166210@qq.com
 * Date on 2019/7/26 16:15.
 * Describe
 */
class DialogActivity : AppCompatActivity() {

    lateinit var dialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_activity)

        initDialog()


        button.setOnClickListener { dialog.show() }
    }

    private fun initRecyclerView(recyclerView: RecyclerView) {
        var adapter = MultipleViewAdapter()
        adapter.initRecyclerView(recyclerView)
        adapter.register(FilterBean::class.java, FilterBeanItemManager(), 0x110, 0x119)

        var filterBeanList = listOf(FilterBean("功效", "multipleSelect", listOf("美白", "补水", "紧致", "去黑头", "舒缓修复", "收缩毛孔", "美白", "补水", "紧致", "去黑头", "舒缓修复", "收缩毛孔", "美白", "补水", "紧致", "去黑头", "舒缓修复", "收缩毛孔")),
                FilterBean("规格", "singleSelect", listOf("1片", "2-5片", "6-10片", "11-20片", "21片及以上", "31-50g/ml", "1片", "2-5片", "6-10片", "11-20片", "21片及以上", "31-50g/ml", "1片", "2-5片", "6-10片", "11-20片", "21片及以上", "31-50g/ml")),
                FilterBean("价格区间", "area", listOf()), FilterBean("商品服务", "multipleSelect", listOf("正品保证", "货到付款", "7天无理由", "促销", "新品")))

        adapter.setAllData(filterBeanList)

    }

    private fun initDialog() {
        dialog = Dialog(this, R.style.MallFilterDialogStyle)
        dialog.setContentView(LayoutInflater.from(this).inflate(R.layout.dialog_mall_filter, null))
        dialog.setCanceledOnTouchOutside(true)
        val window = dialog.window
        val wLp = window.attributes
        wLp.gravity = Gravity.END
        wLp.width = WindowManager.LayoutParams.WRAP_CONTENT
        wLp.height = WindowManager.LayoutParams.MATCH_PARENT
//        wLp.flags = WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
        window.attributes = wLp

        initRecyclerView(dialog.findViewById(R.id.recycler_view))

    }
}