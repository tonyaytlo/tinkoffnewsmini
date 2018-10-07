package ru.galt.app.extensions

import android.content.Context
import android.view.View
import android.view.ViewTreeObserver
import android.widget.Toast

fun View.setVisibility(visible: Boolean) {
    if (visible) makeVisible() else makeGone()
}

fun Context.showToast(str: String) {
    showToast(this, str)
}

fun showToast(context: Context, str: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(context, str, length).show()
}

fun View.makeVisible() {
    visibility = View.VISIBLE
}

fun View.makeInvisible() {
    visibility = View.INVISIBLE
}

fun View.makeGone() {
    visibility = View.GONE
}

inline fun View.waitForLayout(crossinline f: () -> Unit) = with(viewTreeObserver) {
    addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            viewTreeObserver.removeOnGlobalLayoutListener(this)
            f()
        }
    })
}