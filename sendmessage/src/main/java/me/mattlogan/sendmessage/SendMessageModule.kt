package me.mattlogan.sendmessage

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import me.mattlogan.pushtotalk.audio.AudioRecorderModule
import me.mattlogan.uicommon.ViewModelKey
import me.mattlogan.upload.UploadClientModule

@Module(includes = [
  AudioRecorderModule::class,
  UploadClientModule::class
])
abstract class SendMessageModule {

  @Binds
  @IntoMap
  @ViewModelKey(SendMessageViewModel::class)
  abstract fun bindMainViewModel(sendMessageViewModel: SendMessageViewModel): ViewModel
}
