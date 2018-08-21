package me.mattlogan.pushtotalk.audio

import javax.inject.Inject
import javax.inject.Singleton

interface AudioRecorder {
  fun startRecording()
  fun stopRecording()
}

@Singleton
class RealAudioRecorder @Inject constructor() : AudioRecorder {

  init {

  }

  override fun startRecording() {

  }

  override fun stopRecording() {

  }
}
