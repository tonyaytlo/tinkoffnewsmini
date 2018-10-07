package ru.galt.app.presentation.views

import android.content.Context
import android.graphics.Color
import android.support.v4.view.ViewCompat
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.RelativeLayout
import com.tony.tinkoffnewsmini.R

class LoadingView
@JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : RelativeLayout(context, attrs, defStyleAttr) {

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return true
    }

    init {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        inflate(context, R.layout.view_loading, this)
        ViewCompat.setElevation(this, 10f)
        setBackgroundColor(Color.WHITE)
        setOnTouchListener { _, _ ->
            true
        }
    }
}