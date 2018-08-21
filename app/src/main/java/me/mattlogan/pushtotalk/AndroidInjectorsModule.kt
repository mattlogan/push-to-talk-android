package me.mattlogan.pushtotalk

import dagger.Module
import dagger.android.ContributesAndroidInjector
import me.mattlogan.pushtotalk.audio.AudioRecorderModule
import me.mattlogan.sendmessage.SendMessageFragment
import me.mattlogan.upload.UploadClientModule

@Module
abstract class AndroidInjectorsModule {

  @ContributesAndroidInjector(modules = [
    AudioRecorderModule::class,
    UploadClientModule::class
  ])
  abstract fun sendMessageFragment(): SendMessageFragment
}
