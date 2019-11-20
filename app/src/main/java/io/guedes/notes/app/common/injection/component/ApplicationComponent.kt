package io.guedes.notes.app.common.injection.component

import io.guedes.notes.app.App
import io.guedes.notes.app.common.injection.module.ActivityBindingModule
import io.guedes.notes.app.common.injection.module.FirebaseModule
import dagger.BindsInstance
import dagger.Component
import dagger.MembersInjector
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        ActivityBindingModule::class,
        FirebaseModule::class
    ]
)
interface ApplicationComponent : MembersInjector<App> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: App): Builder

        fun build(): ApplicationComponent
    }
}
