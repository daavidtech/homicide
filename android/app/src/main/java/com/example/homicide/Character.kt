package com.example.homicide

import android.graphics.*
import android.os.SystemClock.elapsedRealtime

enum class CharacterMoveDirection {
    LEFT,
    RIGHT,
    STATIONARY
}

class Character {
    // Which direction character is indenting to move
    var moveDirection = CharacterMoveDirection.STATIONARY;

    var xVelocity = 0;

    // Characters velocity related to y axis gravity affects
    // how fast this value decreases.
    var yVelocity = 0;

    var gravity = 1;

    // Speed which character is able to move.
    var speed = 100;

    // Determines how fast the walk cycle animation is displayed.
    var animationSpeed = 1.0f;

    // Determines which animation is currently in use.
    var animationNumber = 0

    lateinit var animationBoxes: List<Rect>;

    lateinit var leftAnimationCycles: List<Int>;
    lateinit var rightAnimationCycles: List<Int>;

    lateinit var spriteBitmap: Bitmap;

    var xPosition = 0;
    var yPosition = 0;
    var width = 100;
    var height = 100;

    private lateinit var dstRect: Rect;

    var lastDrawTime = 0L;

    private lateinit var paint: Paint;

    constructor(
        spriteBitmap: Bitmap,
        gravity: Int? = null,
        animationSpeed: Float = 1.0f,
        animationBoxes: List<Rect>,
        leftAnimationCycles: List<Int>,
        rightAnimationCycles: List<Int>,
        speed: Int? = null,
        xPosition: Int? = null,
        yPosition: Int? = null,
        width: Int? = null,
        height: Int? = null,
    ) {
        this.spriteBitmap = spriteBitmap;

        if (gravity != null) {
            this.gravity = gravity;
        }

        if (speed != null) {
            this.speed = speed;
        }

        if (xPosition != null) {
            this.xPosition = xPosition;
        }

        if (yPosition != null) {
            this.yPosition = yPosition;
        }

        if (width != null) {
            this.width = width;
        }

        if (height != null) {
            this.height = height;
        }

        this.animationSpeed = animationSpeed;
        this.animationBoxes = animationBoxes;
        this.leftAnimationCycles = leftAnimationCycles;
        this.rightAnimationCycles = rightAnimationCycles;

        this.dstRect = Rect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height);

        this.paint = Paint();
        this.paint.color = Color.DKGRAY;
    }

    public fun move(m: CharacterMoveDirection) {
        this.moveDirection = m;
    }

    public fun jump() {
        this.yVelocity = 35;
    }

    public fun onDraw(canvas: Canvas) {
        if (this.lastDrawTime > 0) {
            val currentTime = elapsedRealtime();

            val distance = (currentTime - this.lastDrawTime)

            when(this.moveDirection) {
                CharacterMoveDirection.LEFT -> {
                    this.xPosition -= distance.toInt()
                }
                CharacterMoveDirection.RIGHT -> {
                    this.xPosition += distance.toInt()
                }
            }

            if (this.yPosition < 750) {
                this.yVelocity -= 1;
            } else {
                this.yPosition = 750;
            }

            this.yPosition -= this.yVelocity;
        }

        this.dstRect.set(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height);

        this.lastDrawTime = elapsedRealtime();

        when (this.moveDirection) {
            CharacterMoveDirection.STATIONARY -> {
                canvas.drawBitmap(this.spriteBitmap, this.animationBoxes[1], this.dstRect, this.paint);
            }
            CharacterMoveDirection.RIGHT -> {
                canvas.drawBitmap(this.spriteBitmap, this.animationBoxes[1], this.dstRect, this.paint);
            }
            CharacterMoveDirection.LEFT -> {
                canvas.drawBitmap(this.spriteBitmap, this.animationBoxes[0], this.dstRect, this.paint);
            }
        }
    }
}