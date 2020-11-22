package com.example.homicide

abstract class Observer<T> {
    public abstract fun onNotify(event: T);
}