package com.example.homicide

import android.graphics.Rect

fun scaleRect(r: Rect, scale: Float): Rect {
    return Rect(
        r.left,
        r.top,
        ((r.left + (r.right - r.left)).toFloat() * scale).toInt(),
        ((r.top + (r.bottom - r.top)).toFloat() * scale).toInt()
    )
}