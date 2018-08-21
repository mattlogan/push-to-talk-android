package me.mattlogan.pushtotalk.audio

import dagger.Binds
import dagger.Module

@Module
abstract class AudioRecorderModule {
  @Binds abstract fun audioRecorder(recorder: RealAudioRecorder): AudioRecorder
}
