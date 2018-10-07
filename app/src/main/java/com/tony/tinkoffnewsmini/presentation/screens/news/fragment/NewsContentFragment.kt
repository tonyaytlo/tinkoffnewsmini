package com.tony.tinkoffnewsmini.presentation.screens.news.fragment

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.tony.tinkoffnews.data.entity.NewsContent
import com.tony.tinkoffnewsmini.R
import com.tony.tinkoffnewsmini.domain.command.GetContentCommand
import com.tony.tinkoffnewsmini.ext.formatToDate
import com.tony.tinkoffnewsmini.ext.textFromHtml
import ru.galt.app.domain.base.BaseException
import ru.galt.app.domain.base.BaseSingle
import ru.galt.app.extensions.bind
import ru.galt.app.presentation.base.BaseFragment
import ru.galt.app.presentation.di.AppComponent
import ru.galt.app.presentation.views.LoadingView
import java.lang.ref.WeakReference
import javax.inject.Inject

class NewsContentFragment : BaseFragment() {


    companion object {
        const val TAG = "NewsContentFragment"
        private const val ARG_ID = "id"

        fun getInstance(id: String) = NewsContentFragment()
                .apply {
                    arguments = Bundle().apply { putString(ARG_ID, id) }
                }
    }

    private val id by lazy { arguments?.getString(ARG_ID) }


    private val tvTitle by bind<TextView>(R.id.tvTitle)
    private val tvContent by bind<TextView>(R.id.tvContent)
    private val tvDate by bind<TextView>(R.id.tvDate)
    private val loading by bind<LoadingView>(R.id.loading)

    @Inject
    lateinit var getContentCommand: GetContentCommand

    override fun inject(appComponent: AppComponent) {
        appComponent.newsComponent().build().inject(this)
    }

    override fun getLayoutId() = R.layout.fragment_news_content


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvContent.movementMethod = LinkMovementMethod.getInstance()
        getContent()
    }

    private fun getContent() {
        val newsSingle = BaseSingle<NewsContent>().apply {
            loadingView = WeakReference(loading)
            onSuccess = {
                setNewsContent(it)
            }
            onError = {
                if (it is BaseException) {
                    showError(it.getMessage(activity!!))
                }else {
                    showError(it.localizedMessage)
                }
            }
        }
        getContentCommand.id = this.id ?: ""
        addSubscription(getContentCommand.execute()
                .singleOrError()
                .subscribeWith(newsSingle))
    }

    private fun setNewsContent(content: NewsContent) {
        tvTitle.text = content.title.name
        tvContent.text = content.content.textFromHtml()
        tvDate.text = content.title.publicationDate.milliseconds.formatToDate()
    }

    private fun showError(msg: String) {
        Toast.makeText(activity!!, msg, Toast.LENGTH_SHORT).show()
    }

}