package me.mattlogan.pushtotalk

import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
  AndroidSupportInjectionModule::class,
  AndroidInjectorsModule::class
])
interface AppComponent {
  fun inject(app: PushToTalkApp)
}
