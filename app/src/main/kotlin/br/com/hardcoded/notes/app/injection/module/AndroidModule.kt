package br.com.hardcoded.notes.app.injection.module

import android.app.Application
import android.content.Context
import android.preference.PreferenceManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
@Singleton
class AndroidModule(val application: Application) {

  @Provides
  fun provideApplicationContext() = application.applicationContext

  @Provides
  fun provideSharedPreferences(context: Context) = PreferenceManager.getDefaultSharedPreferences(context)
}
