package me.mattlogan.pushtotalk

import dagger.Module
import dagger.android.ContributesAndroidInjector
import me.mattlogan.pushtotalk.audio.AudioRecorderModule
import me.mattlogan.sendmessage.SendMessageFragment
import me.mattlogan.sendmessage.SendMessageModule
import me.mattlogan.uicommon.ViewModelFactoryModule
import me.mattlogan.upload.UploadClientModule

@Module(includes = [
  ViewModelFactoryModule::class
])
abstract class AndroidInjectorsModule {

  @ContributesAndroidInjector(modules = [SendMessageModule::class])
  abstract fun sendMessageFragment(): SendMessageFragment
}
