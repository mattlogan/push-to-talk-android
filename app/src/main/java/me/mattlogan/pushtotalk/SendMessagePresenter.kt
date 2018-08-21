package me.mattlogan.pushtotalk

import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable

class SendMessagePresenter {

  private val relay = PublishRelay.create<SendMessageUpdate>()
  private val disposables = CompositeDisposable()

  fun updates(): Observable<SendMessageUpdate> = relay

  fun attach(target: Target) {
    
  }

  fun detach() {

  }

  interface Target {
    fun downTouches(): Observable<Unit>
    fun upTouches(): Observable<Unit>
  }
}
