package me.mattlogan.sendmessage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class SendMessageViewModel @Inject constructor(
    startRecordingTransformer: StartRecordingTransformer,
    stopRecordingTransformer: StopRecordingTransformer
) : ViewModel() {

  val uiEvents = SendMessageUiEvents()

  fun state(): LiveData<SendMessageUpdate> = mutableLiveData

  private val mutableLiveData = MutableLiveData<SendMessageUpdate>()
  private val disposable: Disposable

  init {
    disposable = Observable.merge(
        uiEvents.startRecording
            .compose(startRecordingTransformer),
        uiEvents.stopRecording
            .compose(stopRecordingTransformer)
    ).subscribe(mutableLiveData::setValue)
  }

  override fun onCleared() {
    super.onCleared()
    disposable.dispose()
  }
}
