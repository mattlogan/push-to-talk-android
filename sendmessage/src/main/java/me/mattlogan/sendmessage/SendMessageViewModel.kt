package me.mattlogan.sendmessage

import androidx.lifecycle.ViewModel
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class SendMessageViewModel @Inject constructor(private val startRecordingTransformer: StartRecordingTransformer,
                                               private val stopRecordingTransformer: StopRecordingTransformer)
  : ViewModel() {

  private val relay = PublishRelay.create<SendMessageUpdate>()
  private val disposables = CompositeDisposable()

  fun updates(): Observable<SendMessageUpdate> = relay

  fun attach(target: Target) {
    disposables.addAll(
        target.startRecordingEvents()
            .compose(startRecordingTransformer)
            .subscribe(relay::accept),
        target.stopRecordingEvents()
            .compose(stopRecordingTransformer)
            .subscribe(relay::accept)
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
