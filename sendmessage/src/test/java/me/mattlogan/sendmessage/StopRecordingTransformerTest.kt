package me.mattlogan.sendmessage

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import me.mattlogan.pushtotalk.audio.AudioRecorder
import me.mattlogan.upload.UploadClient
import org.junit.Test

class StopRecordingTransformerTest {

  @Test
  fun whenAudioRecorderSucceedsAndUploadSucceeds_showsSentStatus() {
    val audioRecorder = mock<AudioRecorder>()
    whenever(audioRecorder.stopRecording()).thenReturn("test_path")
    val uploadClient = mock<UploadClient>()
    whenever(uploadClient.uploadFile("test_path"))
        .thenReturn(Single.just(UploadClient.Result.Success("test_file_url")))
    val transformer = StopRecordingTransformer(audioRecorder, uploadClient)
    val upstream = Observable.just(StopRecordingEvent)
    val observer = TestObserver<SendMessageUpdate>()
    transformer.apply(upstream).subscribe(observer)
    observer.assertValues(
        SendMessageUpdate.ShowSending,
        SendMessageUpdate.ShowSent("test_file_url")
    )
  }

  @Test
  fun whenAudioRecorderFails_showsError() {
    val audioRecorder = mock<AudioRecorder>()
    whenever(audioRecorder.stopRecording()).thenThrow(IllegalStateException())
    val uploadClient = mock<UploadClient>()
    val transformer = StopRecordingTransformer(audioRecorder, uploadClient)
    val upstream = Observable.just(StopRecordingEvent)
    val observer = TestObserver<SendMessageUpdate>()
    transformer.apply(upstream).subscribe(observer)
    observer.assertValues(
        SendMessageUpdate.ShowSending,
        SendMessageUpdate.ShowError
    )
  }

  @Test
  fun whenUploadClientFails_showsError() {
    val audioRecorder = mock<AudioRecorder>()
    whenever(audioRecorder.stopRecording()).thenReturn("test_path")
    val uploadClient = mock<UploadClient>()
    whenever(uploadClient.uploadFile("test_path"))
        .thenReturn(Single.just(UploadClient.Result.Error))
    val transformer = StopRecordingTransformer(audioRecorder, uploadClient)
    val upstream = Observable.just(StopRecordingEvent)
    val observer = TestObserver<SendMessageUpdate>()
    transformer.apply(upstream).subscribe(observer)
    observer.assertValues(
        SendMessageUpdate.ShowSending,
        SendMessageUpdate.ShowError
    )
  }
}
