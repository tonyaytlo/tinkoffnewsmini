package com.tony.tinkoffnews.presentation.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.tony.tinkoffnews.data.entity.NewsItem
import com.tony.tinkoffnewsmini.R
import com.tony.tinkoffnewsmini.ext.formatToDate
import io.reactivex.subjects.PublishSubject
import ru.galt.app.extensions.bind


class NewsAdapter constructor(val context: Context, var data: MutableList<NewsItem>)
    : RecyclerView.Adapter<NewsAdapter.Holder>() {

    private val inflater
            by lazy { context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater }
    private val onClick: PublishSubject<NewsItem> by lazy { PublishSubject.create<NewsItem>() }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            Holder(inflater.inflate(R.layout.item_news, parent, false))

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.populate(data[position])
    }

    fun addAll(newData: MutableList<NewsItem>) {
        this.data.clear()
        this.data.addAll(newData)
        notifyDataSetChanged()
    }

    fun getOnClickSubject() = onClick


    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTitle by bind<TextView>(R.id.tvTitle)
        private val tvText by bind<TextView>(R.id.tvText)
        private val tvDate by bind<TextView>(R.id.tvDate)

        init {
            itemView.setOnClickListener {
                if (adapterPosition == RecyclerView.NO_POSITION) {
                    return@setOnClickListener
                }
                onClick.onNext(data[adapterPosition])
            }
        }

        fun populate(item: NewsItem) {
            tvTitle.text = item.name
            tvText.text = item.text
            tvDate.text = item.publicationDate.milliseconds.formatToDate()
        }
    }

}