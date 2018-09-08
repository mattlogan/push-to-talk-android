package me.mattlogan.sendmessage

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import me.mattlogan.pushtotalk.audio.AudioRecorder
import me.mattlogan.upload.UploadClient
import javax.inject.Inject

class StopRecordingTransformer @Inject constructor(private val audioRecorder: AudioRecorder,
                                                   private val uploadClient: UploadClient)
  : ObservableTransformer<StopRecordingEvent, SendMessageUpdate> {

  override fun apply(upstream: Observable<StopRecordingEvent>): ObservableSource<SendMessageUpdate> {
    val showSending = upstream
        .map { SendMessageUpdate.ShowSending }

    val sendAudio = upstream
        .map { audioRecorder.stopRecording() }
        .flatMap { filePath -> uploadClient.uploadFile(filePath).toObservable() }
        .map { result ->
          when (result) {
            is UploadClient.Result.Success -> {
              SendMessageUpdate.ShowSent(result.fileUrl)
            }
            is UploadClient.Result.Error -> {
              SendMessageUpdate.ShowError
            }
          }
        }
        .onErrorReturn { SendMessageUpdate.ShowError }

    return Observable.merge(showSending, sendAudio)
  }
}
