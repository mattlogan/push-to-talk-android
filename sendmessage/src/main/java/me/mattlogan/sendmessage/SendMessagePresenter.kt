package me.mattlogan.sendmessage

import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class SendMessagePresenter @Inject constructor(private val startRecordingTransformer: StartRecordingTransformer,
                                               private val stopRecordingTransformer: StopRecordingTransformer) {

  private val relay = PublishRelay.create<SendMessageUpdate>()
  private val disposables = CompositeDisposable()

  fun updates(): Observable<SendMessageUpdate> = relay

  fun attach(target: Target) {
    disposables.addAll(
        target.downTouches()
            .compose(startRecordingTransformer)
            .subscribe(relay::accept),
        target.upTouches()
            .compose(stopRecordingTransformer)
            .subscribe(relay::accept)
    )
  }

  fun detach() = disposables.dispose()

  interface Target {
    fun downTouches(): Observable<Unit>
    fun upTouches(): Observable<Unit>
  }
}
