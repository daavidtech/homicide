package com.example.homicide

import android.graphics.Canvas
import android.graphics.Rect

interface MapObject {
    val hitbox: Rect
    fun draw(canvas: Canvas)
    fun calculateChanges()
}