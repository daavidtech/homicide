package com.example.homicide

import android.graphics.Rect

abstract class Mission {
    protected var screenWidth: Int = 0;
    protected var screenHeight: Int = 0;

    public open fun setScreenSize(width: Int, height: Int) {
        this.screenWidth = width;
        this.screenHeight = height;
    }

    public fun setArea(area: Rect) {

    }
}