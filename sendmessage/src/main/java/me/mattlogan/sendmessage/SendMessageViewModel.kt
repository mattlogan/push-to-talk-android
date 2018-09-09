package me.mattlogan.sendmessage

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import javax.inject.Inject

class SendMessageViewModel @Inject constructor(
    startRecordingTransformer: StartRecordingTransformer,
    stopRecordingTransformer: StopRecordingTransformer
) : ViewModel() {

  val uiEvents = SendMessageUiEvents()

  val state: LiveData<SendMessageUpdate>

  init {
    state = LiveDataReactiveStreams.fromPublisher(
        Observable.merge(
            uiEvents.startRecording
                .compose(startRecordingTransformer),
            uiEvents.stopRecording
                .compose(stopRecordingTransformer)
        ).toFlowable(BackpressureStrategy.LATEST)
    )
  }
}
