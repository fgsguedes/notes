package com.fgsguedes.notes.domain.usecase

import rx.Observable
import rx.Subscriber
import rx.subscriptions.Subscriptions

abstract class UseCase<T> {

  private var subscription = Subscriptions.empty()

  internal fun internalSubscribe(subscriber: Subscriber<T>) {
    subscription = buildObservable().subscribe(subscriber)
  }

  fun unsubscribe() = subscription.unsubscribe()

  abstract protected fun buildObservable(): Observable<T>
}
