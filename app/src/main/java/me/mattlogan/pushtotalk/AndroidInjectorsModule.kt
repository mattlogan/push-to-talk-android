package me.mattlogan.pushtotalk

import dagger.Module
import dagger.android.ContributesAndroidInjector
import me.mattlogan.pushtotalk.audio.AudioRecorderModule
import me.mattlogan.pushtotalk.sendmessage.SendMessageFragment

@Module
abstract class AndroidInjectorsModule {

  @ContributesAndroidInjector(modules = [
    AudioRecorderModule::class
  ])
  abstract fun sendMessageFragment(): SendMessageFragment
}
