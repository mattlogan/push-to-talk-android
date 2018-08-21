package me.mattlogan.pushtotalk.sendmessage

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import me.mattlogan.pushtotalk.audio.AudioRecorder
import javax.inject.Inject

class StartRecordingTransformer @Inject constructor(private val audioRecorder: AudioRecorder)
  : ObservableTransformer<Unit, SendMessageUpdate> {

  override fun apply(upstream: Observable<Unit>): ObservableSource<SendMessageUpdate> {
    return upstream
        .doOnNext { audioRecorder.startRecording() }
        .map { SendMessageUpdate.ShowRecording }
  }
}
