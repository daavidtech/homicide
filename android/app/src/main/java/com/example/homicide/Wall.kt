package com.example.homicide

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect

class Wall {
    lateinit var bitmap: Bitmap;

    var src: Rect? = null;

    constructor(
        bitmap: Bitmap,
        src: Rect? = null,
    ): super() {
        this.bitmap = bitmap;
        this.src = src;
    }

/*    override public fun draw(canvas: Canvas) {
        canvas.drawBitmap(this.bitmap, null, this.hitbox, null);
    }*/
}