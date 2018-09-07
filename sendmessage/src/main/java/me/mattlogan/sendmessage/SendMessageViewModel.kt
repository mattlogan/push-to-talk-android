package me.mattlogan.sendmessage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class SendMessageViewModel @Inject constructor(private val startRecordingTransformer: StartRecordingTransformer,
                                               private val stopRecordingTransformer: StopRecordingTransformer)
  : ViewModel() {

  private val disposables = CompositeDisposable()

  private val liveData = MutableLiveData<SendMessageUpdate>()

  fun liveData(): LiveData<SendMessageUpdate> = liveData

  fun attach(target: Target) {
    disposables.addAll(
        target.startRecordingEvents()
            .compose(startRecordingTransformer)
            .subscribe(liveData::postValue),
        target.stopRecordingEvents()
            .compose(stopRecordingTransformer)
            .subscribe(liveData::postValue)
    )
  }

  override fun onCleared() {
    super.onCleared()
    disposables.dispose()
  }

  interface Target {
    fun startRecordingEvents(): Observable<StartRecordingEvent>
    fun stopRecordingEvents(): Observable<StopRecordingEvent>
  }
}
