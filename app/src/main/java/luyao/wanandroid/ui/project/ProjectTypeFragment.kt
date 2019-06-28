package luyao.wanandroid.ui.project

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import dp2px
import kotlinx.android.synthetic.main.fragment_projecttype.*
import kotlinx.android.synthetic.main.fragment_systemtype.*
import luyao.base.BaseFragment
import luyao.wanandroid.R
import luyao.wanandroid.adapter.ProjectAdapter
import luyao.wanandroid.model.bean.ArticleList
import luyao.wanandroid.ui.BrowserNormalActivity
import luyao.wanandroid.ui.login.LoginActivity
import luyao.wanandroid.util.Preference
import luyao.wanandroid.view.CustomLoadMoreView
import luyao.wanandroid.view.SpaceItemDecoration

/**
 * Created by Lu
 * on 2018/4/1 17:06
 */
class ProjectTypeFragment : BaseFragment<ProjectViewModel>() {

    private val isLogin by Preference(Preference.IS_LOGIN, false)
    override fun providerVMClass(): Class<ProjectViewModel>? = ProjectViewModel::class.java
    private val cid by lazy { arguments?.getInt(ProjectTypeFragment.CID) }
    private val isLasted by lazy { arguments?.getBoolean(ProjectTypeFragment.LASTED) } // 区分是最新项目 还是项目分类
    private var currentPage = 0
    private val projectAdapter by lazy { ProjectAdapter() }

    override fun getLayoutResId() = R.layout.fragment_projecttype

    companion object {

        private const val CID = "projectCid"
        private const val LASTED = "lasted"
        fun newInstance(cid: Int, isLasted: Boolean): ProjectTypeFragment {
            val fragment = ProjectTypeFragment()
            val bundle = Bundle()
            bundle.putInt(CID, cid)
            bundle.putBoolean(LASTED, isLasted)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun initView() {
        initRecycleView()


        projectRefreshLayout.run {
            isRefreshing = true
            setOnRefreshListener { refresh() }
        }
        refresh()
    }

    private fun refresh() {
        projectAdapter.setEnableLoadMore(false)
        projectRefreshLayout.isRefreshing = true

        isLasted?.run {
            if (this) {
                currentPage = 0
                mViewModel.getLastedProject(currentPage);
            } else {
                currentPage = 1
                cid?.let {
                    mViewModel.getProjectTypeDetailList(currentPage, it)
                }
            }
        }
    }

    private fun initRecycleView() {
        projectAdapter.run {
            setOnItemClickListener { _, _, position ->
                val intent = Intent(activity, BrowserNormalActivity::class.java)
                intent.putExtra(BrowserNormalActivity.URL, projectAdapter.data[position].link)
                startActivity(intent)
            }
            setLoadMoreView(CustomLoadMoreView())
            setOnLoadMoreListener({ loadMore() }, typeRecycleView)
        }
        projectRecycleView.run {
            layoutManager = LinearLayoutManager(activity)
            addItemDecoration(SpaceItemDecoration(projectRecycleView.dp2px(10f)))
            adapter = projectAdapter
        }
    }

    private fun loadMore() {
        isLasted?.run {
            if (this)
                mViewModel.getLastedProject(currentPage)
            else
                cid?.let {
                    mViewModel.getProjectTypeDetailList(currentPage, it)
                }
        }
    }

    override fun initData() {
    }


    private fun getProjectTypeDetailList(articleList: ArticleList) {
        projectAdapter.run {
            if (articleList.offset >= articleList.total) {
                loadMoreEnd()
                return
            }
            onItemChildClickListener = this@ProjectTypeFragment.onItemChildClickListener

            if (projectRefreshLayout.isRefreshing) replaceData(articleList.datas)
            else addData(articleList.datas)
            setEnableLoadMore(true)
            loadMoreComplete()
        }
        projectRefreshLayout.isRefreshing = false
        currentPage++
    }

    private val onItemChildClickListener = BaseQuickAdapter.OnItemChildClickListener { _, view, position ->
        when (view.id) {
            R.id.articleStar -> {
                if (isLogin) {
                    projectAdapter.run {
                        data[position].run {
                            collect = !collect
                            mViewModel.collectArticle(id, collect)
                        }
                        notifyDataSetChanged()
                    }
                } else {
                    Intent(activity, LoginActivity::class.java).run { startActivity(this) }
                }
            }
        }
    }

    override fun startObserve() {
        mViewModel.run {
            mArticleList.observe(this@ProjectTypeFragment, Observer {
                it?.run { getProjectTypeDetailList(it) }
            })
        }
    }
}