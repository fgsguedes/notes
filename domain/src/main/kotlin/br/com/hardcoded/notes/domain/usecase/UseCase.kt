package br.com.hardcoded.notes.domain.usecase

import rx.Observable
import rx.Subscriber
import rx.subscriptions.Subscriptions

abstract class UseCase<T> {

  private val observable by lazy { buildObservable() }
  private var subscription = Subscriptions.empty()

  fun subscribe(subscriber: Subscriber<T>) {
    subscription = observable.subscribe(subscriber)
  }

  fun unsubscribe() = subscription.unsubscribe()

  abstract protected fun buildObservable(): Observable<T>
}
