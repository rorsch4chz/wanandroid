package luyao.wanandroid.model.repository

import luyao.base.BaseRepository
import luyao.base.WanResponse
import luyao.wanandroid.model.api.WanRetrofitClient
import luyao.wanandroid.model.bean.ArticleList

/**
 * Created by luyao
 * on 2019/4/10 14:01
 */
class CollectRepository : BaseRepository(){

    suspend fun getCollectArticles(page: Int): WanResponse<ArticleList> {
        return apiCall { WanRetrofitClient.service.getCollectArticles(page) }
    }

    suspend fun collectArticle(articleId: Int): WanResponse<ArticleList> {
        return apiCall { WanRetrofitClient.service.collectArticle(articleId) }
    }

    suspend fun unCollectArticle(articleId: Int): WanResponse<ArticleList> {
        return apiCall { WanRetrofitClient.service.cancelCollectArticle(articleId) }
    }
}