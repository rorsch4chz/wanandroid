package luyao.wanandroid.ui.home

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import luyao.base.BaseViewModel
import luyao.wanandroid.model.repository.HomeRepository
import luyao.wanandroid.model.bean.ArticleList
import luyao.wanandroid.model.bean.Banner

/**
 * Created by luyao
 * on 2019/1/29 10:27
 */
class HomeViewModel : BaseViewModel() {

    private val repository by lazy { HomeRepository() }
    val mBanners: MutableLiveData<List<Banner>> = MutableLiveData()
    val mArticleList: MutableLiveData<ArticleList> = MutableLiveData()

    fun getBanners() {
        launch {
            val result = withContext(Dispatchers.IO) { repository.getBanners() }
            executeResponse(result, { mBanners.value = result.data }, {})
        }
    }

    fun getArticleList(page: Int) {
        launch {
            val result = withContext(Dispatchers.IO) { repository.getArticleList(page) }
            executeResponse(result, { mArticleList.value = result.data }, {})
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

}