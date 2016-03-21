package br.com.hardcoded.notes.app.injection.module

import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
@Singleton
class AndroidModule(val application: Application) {

  @Provides
  fun provideApplicationContext() = application.applicationContext
}
