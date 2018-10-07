package com.tony.tinkoffnewsmini.ext

import android.os.Build
import android.text.Html
import android.text.Spanned
import java.text.SimpleDateFormat
import java.util.*


fun Long.formatToDate(): String {
    val formatter = SimpleDateFormat("dd.MM.yyyy")
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this
    return formatter.format(calendar.time)
}

fun String.textFromHtml(): Spanned {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        return Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
    }
    return Html.fromHtml(this)
}