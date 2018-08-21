package me.mattlogan.pushtotalk.audio

import android.media.MediaRecorder
import javax.inject.Inject
import javax.inject.Singleton

interface AudioRecorder {
  /** Start recording audio at the provided file path. */
  fun startRecording(path: String)

  /** Stop recording and return the path to the audio file */
  fun stopRecording(): String
}

@Singleton
class RealAudioRecorder @Inject constructor() : AudioRecorder {

  private val mediaRecorder = MediaRecorder()

  // We can hold on to a bit of state here so the UI doesn't have to
  private var filePath: String? = null

  override fun startRecording(path: String) {
    filePath = path

    mediaRecorder.apply {
      setAudioSource(MediaRecorder.AudioSource.MIC)
      // Adaptive Multi-Rate (AMR) audio codec is optimized for speech
      setOutputFormat(MediaRecorder.OutputFormat.AMR_NB)
      setAudioSamplingRate(44_100)
      setAudioEncodingBitRate(96_000)
      setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
      setOutputFile(path)
      prepare()
      start()
    }
  }

  override fun stopRecording(): String {
    val path = filePath
        ?: throw IllegalStateException("You have to call startRecording() before stopRecording()")
    mediaRecorder.stop()
    return path
  }
}
