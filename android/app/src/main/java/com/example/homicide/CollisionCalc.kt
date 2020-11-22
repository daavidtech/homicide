package com.example.homicide

import android.graphics.Rect

public fun doesRectanglesCollide(a: Rect, b: Rect): Boolean {
    if (a.left < b.right || a.right > b.right) {
        return false;
    }

    if (a.top < b.top || a.bottom > b.bottom) {
        return false;
    }

    return true;
}