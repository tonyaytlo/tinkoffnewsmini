package ru.galt.app.extensions

import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity

fun AppCompatActivity.makeTransactionAdd(container: Int, fragment: Fragment, tag: String = "",
                                         addToStack: Boolean = false) {
    val transaction = supportFragmentManager.beginTransaction()
    transaction.add(container, fragment, tag)
    if (addToStack) {
        transaction.addToBackStack(null)
    }
    transaction.commit()
}

fun Fragment.makeTransactionAdd(container: Int, fragment: Fragment, tag: String = "",
                                addToStack: Boolean = false) {
    activity?.let {
        val transaction = it.supportFragmentManager.beginTransaction()
        transaction.add(container, fragment, tag)
        if (addToStack) {
            transaction.addToBackStack(null)
        }
        transaction.commit()
    }
}
