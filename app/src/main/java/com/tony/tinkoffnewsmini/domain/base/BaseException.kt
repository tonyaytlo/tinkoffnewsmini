package ru.galt.app.domain.base

import android.content.Context
import android.util.Pair
import com.tony.tinkoffnewsmini.R

data class BaseException(val errorMessage: String?,
                         val code: Int?,
                         private val messageId: Int) : Throwable() {

    constructor(messageId: Int) : this(null, null, messageId)

    constructor(message: String) : this(message, null, R.string.error_unknown)

    constructor(data: Pair<String?, Int>, code: Int? = null) : this(data.first, code, data.second)

    fun getMessage(context: Context): String {
        return if (errorMessage != null && errorMessage.isNotEmpty()) {
            errorMessage
        } else {
            context.getString(messageId)
        }
    }
}