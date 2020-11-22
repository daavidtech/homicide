package com.example.homicide

import android.graphics.Rect

interface Spawner {
    fun spawn(position: Position): Entity
    fun spawn(rect: Rect): Entity
}