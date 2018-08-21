package me.mattlogan.sendmessage

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import me.mattlogan.pushtotalk.audio.AudioRecorder
import org.junit.Test

class StartRecordingTransformerTest {

  @Test
  fun startRecordingEventStartsRecordingAndShowsRecordingStatus() {
    val audioRecorder = mock<AudioRecorder>()
    val transformer = StartRecordingTransformer(audioRecorder)
    val upstream = Observable.just(StartRecordingEvent(filePath = "test_path"))
    val observer = TestObserver<SendMessageUpdate>()
    transformer.apply(upstream).subscribe(observer)
    observer.assertValue(SendMessageUpdate.ShowRecording)
  }

  @Test
  fun startRecordingEventStartsRecordingAndShowsError() {
    val audioRecorder = mock<AudioRecorder>()
    whenever(audioRecorder.startRecording("test_path")).thenThrow(IllegalStateException())
    val transformer = StartRecordingTransformer(audioRecorder)
    val upstream = Observable.just(StartRecordingEvent(filePath = "test_path"))
    val observer = TestObserver<SendMessageUpdate>()
    transformer.apply(upstream).subscribe(observer)
    observer.assertValue(SendMessageUpdate.ShowError)
  }
}
