package me.mattlogan.pushtotalk

import dagger.Module
import dagger.android.ContributesAndroidInjector
import me.mattlogan.sendmessage.SendMessageFragment
import me.mattlogan.sendmessage.SendMessageModule
import me.mattlogan.uicommon.ViewModelFactoryModule

@Module(includes = [
  ViewModelFactoryModule::class
])
abstract class AndroidInjectorsModule {

  @ContributesAndroidInjector(modules = [SendMessageModule::class])
  abstract fun sendMessageFragment(): SendMessageFragment
}
