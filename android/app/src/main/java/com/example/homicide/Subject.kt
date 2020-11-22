package com.example.homicide

abstract class Subject<T> {
    private val observers = mutableSetOf<Observer<T>>();

    protected fun send(event: T) {
        for (observer in this.observers) {
            observer.onNotify(event);
        }
    }

    public fun addObserver(observer: Observer<T>) {
        this.observers.add(observer);
    }

    public fun removeObserver(observer: Observer<T>) {
        this.observers.remove(observer);
    }
}