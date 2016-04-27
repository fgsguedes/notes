package br.com.hardcoded.notes.domain.model

import java.util.*

data class Note(
    val id: Long,
    val title: String,
    val content: String? = null,
    val label: Label? = null,
    val remindOn: Date? = null
)
