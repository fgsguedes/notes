package com.fgsguedes.notes.app.common

// To be used with view-click in conjunction with single-line `when` clauses
fun returningTrue(f: () -> Unit): Boolean {
    f()
    return true
}
