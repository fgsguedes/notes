package br.com.hardcoded.notes.domain.model

import java.util.*

data class Note(
    val id: Long,
    val title: String,
    val content: String,
    val label: Label,
    val remindOn: Date
)
