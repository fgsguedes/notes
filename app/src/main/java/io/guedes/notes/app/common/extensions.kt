package io.guedes.notes.app.common

import android.view.View
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

fun <T : View> AppCompatActivity.bind(@IdRes viewId: Int) =
    lazy { findViewById<T>(viewId) }

fun <T : View> RecyclerView.ViewHolder.bind(@IdRes viewId: Int) =
    lazy { itemView.findViewById<T>(viewId) }

// To be used with view-click in conjunction with single-line `when` clauses
fun returningTrue(action: () -> Unit) = true.also { action() }
