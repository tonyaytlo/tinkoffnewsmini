package ru.galt.app.presentation.screens.subscriptions.activity

import android.os.Bundle
import com.tony.tinkoffnewsmini.R
import com.tony.tinkoffnewsmini.presentation.screens.news.fragment.NewsContentFragment
import ru.galt.app.extensions.makeTransactionAdd
import ru.galt.app.presentation.base.BaseActivity
import ru.galt.app.presentation.di.AppComponent
import ru.galt.app.presentation.screens.subscriptions.fragment.NewsListFragment

class MainActivity : BaseActivity() {

    override fun inject(appComponent: AppComponent) {
        appComponent.newsComponent().build().inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (supportFragmentManager.findFragmentByTag(NewsListFragment.TAG) == null) {
            makeTransactionAdd(R.id.container, NewsListFragment(), NewsListFragment.TAG)
        }
    }

}
