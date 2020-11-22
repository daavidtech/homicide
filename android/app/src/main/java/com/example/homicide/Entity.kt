package com.example.homicide

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect
import android.os.SystemClock

data class PositionChange(val dx: Int, val dy: Int, val xVelocity: Int, val yVelocity: Int);

abstract class Entity {
    protected lateinit var bitmap: Bitmap;
    protected var src: Rect? = null;
    public val hitbox = Rect();

    public var noclip: Boolean = false;
    public var xAcceleration: Int = 0;
    public var yAcceleration: Int = 0;
    public var xVelocity: Int = 0;
    public var yVelocity: Int = 0;
    public var weight: Int = 0;
    public var movable: Boolean = true;
    public var rotation: Int = 0;

    public var isPlatform = false;

    public var isCollidingBottom = false;

    private var lastdx = 0;
    private var lastdy = 0;

    private var lastDrawTime = 0L;

    constructor(
        bitmap: Bitmap,
        hitbox: Rect,
        xAcceleration: Int = 0,
        yAcceleration: Int = 0,
        isPlatform: Boolean = false
    ) {
        this.bitmap = bitmap;
        this.src = null;
        this.hitbox.set(hitbox);
        this.xAcceleration = xAcceleration;
        this.yAcceleration = yAcceleration;

        this.isPlatform = isPlatform;
    }

    public fun destroy() {

    }


    public fun draw(canvas: Canvas) {
        canvas.drawBitmap(this.bitmap, this.src, this.hitbox, null);
    }

    public fun update() {

    }

    public fun calculateChanges(): PositionChange {
        if (lastDrawTime == 0L) {
            this.lastDrawTime = SystemClock.elapsedRealtime();

            return PositionChange(
                dx = 0,
                dy = 0,
                xVelocity = this.xVelocity,
                yVelocity = this.yVelocity
            )
        }

        val currentTime = SystemClock.elapsedRealtime();

        val timeFromLastCalculation = (currentTime - this.lastDrawTime).toDouble() / 1000;
        this.lastDrawTime = currentTime;

        if (this.xVelocity != 0) {
            println(this.xVelocity);
        }

        val xVelocity = this.xVelocity + this.xAcceleration;
        val yVelocity = this.yVelocity + this.yAcceleration;

        val dx = (timeFromLastCalculation * xVelocity).toInt();
        val dy = (timeFromLastCalculation * yVelocity).toInt();

        if (dx == 0 && dy == 0) {
            return PositionChange(
                dx = dx,
                dy = dy,
                xVelocity = xVelocity,
                yVelocity = yVelocity
            )
        }

/*        if (dx != this.lastdx) {
            println("dx: " + dx);
        }

        if (dy != this.lastdy) {
            println("dy: " + dy);
        }*/

        this.lastdx = dx;
        this.lastdy = dy;

        return PositionChange(
            dx = dx,
            dy = dy,
            xVelocity = xVelocity,
            yVelocity = yVelocity
        )
    }

    public fun moveWithCollidingTop(
        dx: Int,
        top: Int
    ) {
        val newLeft = this.hitbox.left + dx;
        val newBottom = top + (this.hitbox.bottom - this.hitbox.top);
        val newRight = this.hitbox.right + dx;

        this.hitbox.set(newLeft, top, newRight, newBottom);
    }

    public fun moveWithCollidingBottom(
        dx: Int,
        bottom: Int
    ) {
        val newLeft = this.hitbox.left + dx;
        val newTop = bottom - (this.hitbox.bottom - this.hitbox.top);
        val newRight = this.hitbox.right + dx;

        this.hitbox.set(newLeft, newTop, newRight, bottom);
    }

    public fun moveWithCollidingLeft(
        dy: Int,
        left: Int
    ) {
        val newRight = left + (this.hitbox.right - this.hitbox.left);
        val newTop = this.hitbox.top + dy;
        val newBottom = this.hitbox.bottom + dy;

        this.hitbox.set(left, newTop, newRight, newBottom);
    }

    public fun moveWithCollidingRight(
        dy: Int,
        right: Int
    ) {
        val newLeft = right - (this.hitbox.right - this.hitbox.left);
        val newTop = this.hitbox.top + dy;
        val newBottom = this.hitbox.bottom + dy;

        this.hitbox.set(newLeft, newTop, right, newBottom);
    }

    public fun move(
        dx: Int = 0,
        dy: Int = 0,
    ) {
        val newLeft = this.hitbox.left + dx;
        val newTop = this.hitbox.top + dy;
        val newRight = this.hitbox.right + dx;
        val newBottom = this.hitbox.bottom + dy;

        this.hitbox.set(newLeft, newTop, newRight, newBottom);
    }
}