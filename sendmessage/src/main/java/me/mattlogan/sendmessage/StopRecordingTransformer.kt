package me.mattlogan.sendmessage

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import me.mattlogan.pushtotalk.audio.AudioRecorder
import javax.inject.Inject

class StopRecordingTransformer @Inject constructor(private val audioRecorder: AudioRecorder)
  : ObservableTransformer<StopRecordingEvent, SendMessageUpdate> {

  override fun apply(upstream: Observable<StopRecordingEvent>): ObservableSource<SendMessageUpdate> {
    val showSending = upstream
        .map { SendMessageUpdate.ShowSending }

    val sendAudio = upstream
        .map { audioRecorder.stopRecording() }
        .flatMap { filePath -> Observable.just(filePath) }
        .map { SendMessageUpdate.ShowSent as SendMessageUpdate }
        .onErrorReturn { SendMessageUpdate.ShowError }

    return Observable.merge(showSending, sendAudio)
  }
}
