package ru.galt.app.presentation.base

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import io.reactivex.subjects.PublishSubject
import java.lang.*
import java.lang.reflect.InvocationTargetException

abstract class BaseRecyclerAdapter<VH : BaseRecyclerAdapter<VH, D>.Holder, D>
(context: Context, data: MutableList<D>) :
        RecyclerView.Adapter<VH>(), Filterable {

    constructor(
            context: Context,
            data: MutableList<D>,
            layout: Int,
            clazz: Class<VH>) : this(context, data) {
        layoutId = layout
        this.clazz = clazz
    }

    var originalData = mutableListOf<D>()
    var filteredData = mutableListOf<D>()
    private var inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private var layoutId: Int = 0
    private lateinit var clazz: Class<VH>
    private val onClick: PublishSubject<D> = PublishSubject.create()
    private val filter = ItemFilter()
    var onFilterDone: (Int) -> Unit = {}

    init {
        originalData.addAll(data)
        filteredData.addAll(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH = createViewHolder(inflater, parent)


    private fun createViewHolder(inflater: LayoutInflater, parent: ViewGroup?): VH {
        try {
            val a1 = this.javaClass
            val a2 = View::class.java
            val constructor = clazz.getDeclaredConstructor(a1, a2)
            return constructor.newInstance(this, inflater.inflate(layoutId, parent, false))
        } catch (e: InstantiationException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        }
        throw IllegalArgumentException()
    }

    fun getClick() = onClick

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = filteredData[position]
        bindViewHolder(item, holder)
    }

    fun addAll(data: MutableList<D>) {
        originalData.clear()
        filteredData.clear()
        originalData.addAll(data)
        filteredData.addAll(data)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = filteredData.size

    abstract fun bindViewHolder(item: D, holder: VH)

    override fun getFilter(): Filter = filter

    abstract fun performFilter(query: String, item: D): Boolean

    abstract inner class Holder(view: View) : RecyclerView.ViewHolder(view) {

        init {
            view.setOnClickListener {
                if (adapterPosition == RecyclerView.NO_POSITION) {
                    return@setOnClickListener
                }
                onClick.onNext(filteredData[adapterPosition])
            }
        }
    }

    private inner class ItemFilter : Filter() {

        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filterString = constraint.toString().toLowerCase()

            //clear previous results
            filteredData.clear()

            val results = FilterResults()

            //filter by query
            originalData
                    .filter { performFilter(filterString, it) }
                    .forEach { filteredData.add(it) }
            results.values = filteredData
            results.count = filteredData.size
            return results
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            filteredData = results?.values as MutableList<D>
            onFilterDone.invoke(filteredData.size)
            notifyDataSetChanged()
        }
    }
}
