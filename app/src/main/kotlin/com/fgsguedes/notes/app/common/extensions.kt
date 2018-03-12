package com.fgsguedes.notes.app.common

import android.support.annotation.IdRes
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.view.View

fun <T : View> AppCompatActivity.bind(
    @IdRes viewId: Int
) = lazy {
    ActivityCompat.requireViewById<T>(this, viewId)
}

// To be used with view-click in conjunction with single-line `when` clauses
fun returningTrue(f: () -> Unit): Boolean {
    f()
    return true
}
