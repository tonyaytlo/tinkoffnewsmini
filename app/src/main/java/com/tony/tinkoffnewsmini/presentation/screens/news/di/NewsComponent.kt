package ru.galt.app.presentation.screens.subscriptions.di

import com.tony.tinkoffnewsmini.presentation.screens.news.fragment.NewsContentFragment
import dagger.Subcomponent
import ru.galt.app.presentation.di.ActivityScope
import ru.galt.app.presentation.screens.subscriptions.activity.MainActivity
import ru.galt.app.presentation.screens.subscriptions.fragment.NewsListFragment

@ActivityScope
@Subcomponent
interface NewsComponent {

    fun inject(activity: MainActivity)

    fun inject(fragment: NewsListFragment)
    fun inject(fragment: NewsContentFragment)

    @Subcomponent.Builder
    interface Builder {
        fun build(): NewsComponent
    }

}