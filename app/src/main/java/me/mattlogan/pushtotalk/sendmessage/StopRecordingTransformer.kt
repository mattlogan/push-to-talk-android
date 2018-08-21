package me.mattlogan.pushtotalk.sendmessage

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import me.mattlogan.pushtotalk.audio.AudioRecorder
import javax.inject.Inject

class StopRecordingTransformer @Inject constructor(private val audioRecorder: AudioRecorder)
  : ObservableTransformer<Unit, SendMessageUpdate> {

  override fun apply(upstream: Observable<Unit>): ObservableSource<SendMessageUpdate> {
    val showSending = upstream
        .doOnNext { audioRecorder.stopRecording() }
        .map { SendMessageUpdate.ShowSending }

    val sendAudio = upstream
        .map { SendMessageUpdate.ShowSent }

    return Observable.merge(showSending, sendAudio)
  }
}
