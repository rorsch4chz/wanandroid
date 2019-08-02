package com.bz.flowlayout.bean

/**
 * Author by 柏洲
 * Email 422166210@qq.com
 * Date on 2019/8/2 15:36.
 * Describe
 */

/**
 * type: 0 升降序排列{DESC,ASC,UNSORTED,}， 1 选中未选中排列{SELECTED,UNSELECTED}
 */
data class SortBean(var name: String,
                    var type: Int,
                    var defaultSortStatus: SortStatus = if (type == 0) SortStatus.UNSORTED else SortStatus.UNSELECTED,
                    /**跟枚举的排序一一对应，作为排序返回值。
                     * 当 UNSELECTED 不为空的时候，已选中状态下可点击取消选中状态。
                     * 当 UNSELECTED 为空的时候该排序item，在已选中状态下不可点击。
                     */
                    var statusValues: MutableList<String> = if (type == 0) mutableListOf("PRICE_DESC", "PRICE_ASC", "") else mutableListOf("SALE_NUM_DESC", "SERVICE_SORT")
)

enum class SortStatus(var value: Int) {
    /**
     * 降序
     */
    DESC(0),
    /**
     * 升序
     */
    ASC(1),
    /**
     * 未选中升降序
     */
    UNSORTED(2),
    /**
     * 选择
     */
    SELECTED(0),
    /**
     * 未选择
     */
    UNSELECTED(1)
}