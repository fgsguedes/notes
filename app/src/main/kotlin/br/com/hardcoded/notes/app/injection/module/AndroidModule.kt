package br.com.hardcoded.notes.app.injection.module

import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AndroidModule(val application: Application) {

  @Provides
  @Singleton
  fun provideApplicationContext() = application.applicationContext
}
