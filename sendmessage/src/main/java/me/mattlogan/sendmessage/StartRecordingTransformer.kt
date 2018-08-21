package me.mattlogan.sendmessage

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import me.mattlogan.pushtotalk.audio.AudioRecorder
import javax.inject.Inject

class StartRecordingTransformer @Inject constructor(private val audioRecorder: AudioRecorder)
  : ObservableTransformer<StartRecordingEvent, SendMessageUpdate> {

  override fun apply(upstream: Observable<StartRecordingEvent>): ObservableSource<SendMessageUpdate> {
    return upstream
        .map { event -> audioRecorder.startRecording(event.filePath) }
        .map { SendMessageUpdate.ShowRecording as SendMessageUpdate }
        .onErrorReturn { SendMessageUpdate.ShowError }
  }
}
