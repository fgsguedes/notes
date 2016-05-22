package br.com.hardcoded.notes.app.common.injection.module

import android.content.Context
import dagger.Module
import dagger.Provides
import justmakeitwork.DatabaseHelper
import javax.inject.Singleton

@Module
@Singleton
class SQLiteModule {

  @Provides
  fun provideDatabaseHelper(context: Context) = DatabaseHelper(context)
}
