package ru.galt.app.presentation.screens.subscriptions.fragment

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.tony.tinkoffnews.data.entity.NewsItem
import com.tony.tinkoffnews.presentation.view.adapter.NewsAdapter
import com.tony.tinkoffnewsmini.R
import com.tony.tinkoffnewsmini.domain.command.GetNewsCommand
import com.tony.tinkoffnewsmini.presentation.screens.news.fragment.NewsContentFragment
import ru.galt.app.domain.base.BaseException
import ru.galt.app.domain.base.BaseSingle
import ru.galt.app.extensions.bind
import ru.galt.app.extensions.makeTransactionAdd
import ru.galt.app.presentation.base.BaseFragment
import ru.galt.app.presentation.di.AppComponent
import javax.inject.Inject


class NewsListFragment : BaseFragment() {

    companion object {
        const val TAG = "NewsListFragment"
    }

    private val srNews by bind<SwipeRefreshLayout>(R.id.srNews)
    private val rvNews by bind<RecyclerView>(R.id.rvNews)


    @Inject
    lateinit var getNewsCommand: GetNewsCommand

    private var adapter: NewsAdapter? = null
    private var toastError: Toast? = null

    override fun inject(appComponent: AppComponent) {
        appComponent.newsComponent().build().inject(this)
    }

    override fun getLayoutId() = R.layout.fragment_news_list

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        srNews.setColorSchemeColors(ContextCompat.getColor(activity!!, R.color.colorPrimary))
        initListeners()
        getNews()
    }

    private fun initListeners() {
        srNews.setOnRefreshListener {
            fetchNews()
        }
    }

    private fun fetchNews() {
        val newsSingle = BaseSingle<List<NewsItem>>().apply {
            onStart = { srNews.isRefreshing = true }
            onTerminate = { srNews.isRefreshing = false }
            onSuccess = {
                setNews(it)
            }
            onError = {
                if (it is BaseException) {
                    showError(it.getMessage(activity!!))
                } else {
                    showError(it.localizedMessage)
                }
            }
        }
        getNewsCommand.fetch = true
        addSubscription(getNewsCommand.execute()
                .singleOrError()
                .subscribeWith(newsSingle))
    }


    private fun getNews() {
        val newsSingle = BaseSingle<List<NewsItem>>().apply {
            onStart = { srNews.isRefreshing = true }
            onTerminate = { srNews.isRefreshing = false }
            onSuccess = {
                setNews(it)
            }
            onError = {
                if (it is BaseException) {
                    showError(it.getMessage(activity!!))
                } else {
                    showError(it.localizedMessage)
                }
            }
        }
        getNewsCommand.fetch = false
        addSubscription(getNewsCommand.execute()
                .singleOrError()
                .subscribeWith(newsSingle))
    }

    private fun setNews(news: List<NewsItem>) {
        if (rvNews.adapter == null) {
            adapter = NewsAdapter(activity!!, news.toMutableList())
            addSubscription(adapter!!.getOnClickSubject()
                    .subscribe {
                        openNewsContent(it.id)
                    })
            rvNews.layoutManager = LinearLayoutManager(activity!!)
            rvNews.layoutAnimation = AnimationUtils.loadLayoutAnimation(activity!!, R.anim.layout_anim_fall_down)
            rvNews.adapter = adapter

        } else {
            adapter?.addAll(news.toMutableList())
            rvNews.scheduleLayoutAnimation()
        }

    }

    private fun showError(msg: String) {
        toastError?.cancel()
        toastError = Toast.makeText(activity, msg, Toast.LENGTH_SHORT)
        toastError?.show()
    }

    private fun openNewsContent(id: String) {
        makeTransactionAdd(R.id.container, NewsContentFragment.getInstance(id), NewsContentFragment.TAG, true)
    }

}
