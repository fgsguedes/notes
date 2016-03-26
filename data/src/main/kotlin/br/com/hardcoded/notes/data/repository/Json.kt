package br.com.hardcoded.notes.data.repository

import com.google.gson.Gson
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Json @Inject constructor() {

  private val gson = Gson()

  fun <T : Any> parse(string: String, modelClass: Class<T>) = gson.fromJson(string, modelClass)

  fun format(any: Any) = gson.toJson(any)
}
