package me.mattlogan.sendmessage

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import me.mattlogan.uicommon.toLiveData
import javax.inject.Inject

class SendMessageViewModel @Inject constructor(
    startRecordingTransformer: StartRecordingTransformer,
    stopRecordingTransformer: StopRecordingTransformer
) : ViewModel() {

  val uiEvents = SendMessageUiEvents()

  val state: LiveData<SendMessageUpdate>

  init {
    state = Observable.merge(
        uiEvents.startRecording
            .compose(startRecordingTransformer),
        uiEvents.stopRecording
            .compose(stopRecordingTransformer)
    ).toLiveData()
  }
}
