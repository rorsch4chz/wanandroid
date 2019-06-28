package luyao.wanandroid.ui.system

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import luyao.base.BaseViewModel
import luyao.wanandroid.model.bean.ArticleList
import luyao.wanandroid.model.bean.SystemParent
import luyao.wanandroid.model.repository.SystemRepository

/**
 * Created by luyao
 * on 2019/4/8 16:40
 */
class SystemViewModel : BaseViewModel() {

    private val repository by lazy { SystemRepository() }
    val mArticleList: MutableLiveData<ArticleList> = MutableLiveData()
    val mSystemParentList: MutableLiveData<List<SystemParent>> = MutableLiveData()


    fun getSystemTypeDetail(id: Int, page: Int) {
        launch {
            val result = withContext(Dispatchers.IO) { repository.getSystemTypeDetail(id, page) }
            executeResponse(result, { mArticleList.value = result.data }, {})
        }
    }

    fun getSystemTypes() {
        launch {
            val result = withContext(Dispatchers.IO) { repository.getSystemTypes() }
            executeResponse(result, { mSystemParentList.value = result.data }, {})
        }
    }

    fun collectArticle(articleId: Int, boolean: Boolean) {
        launch {
            val result = withContext(Dispatchers.IO) {
                if (boolean) repository.collectArticle(articleId)
                else repository.unCollectArticle(articleId)
            }
        }
    }

    fun getBlogArticle(id: Int, page: Int) {
        launch {
            val result = withContext(Dispatchers.IO) { repository.getBlogArticle(id, page) }
            executeResponse(result, { mArticleList.value = result.data }, {})
        }
    }
}