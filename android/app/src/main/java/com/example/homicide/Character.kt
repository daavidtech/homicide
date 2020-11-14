package com.example.homicide

import android.graphics.*

enum class CharacterMoveDirection {
    LEFT,
    RIGHT,
    STATIONARY
}

class Character: ObjectPhycis {
    // Which direction character is indenting to move
    var moveDirection = CharacterMoveDirection.STATIONARY;

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

    private lateinit var dstRect: Rect;

    private lateinit var paint: Paint;

    constructor(
        gameMap: GameMap,
        spriteBitmap: Bitmap,
        gravity: Int? = null,
        animationSpeed: Float = 1.0f,
        animationBoxes: List<Rect>,
        leftAnimationCycles: List<Int>,
        rightAnimationCycles: List<Int>,
        speed: Int? = null,
        hitbox: Rect? = null,
    ): super(gameMap = gameMap) {
        this.spriteBitmap = spriteBitmap;

        if (gravity != null) {
            this.gravity = gravity;
        }

        if (speed != null) {
            this.speed = speed;
        }

        if (hitbox != null) {
            this.hitbox.set(hitbox);
        }

/*        if (xPosition != null) {
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
        }*/

        this.animationSpeed = animationSpeed;
        this.animationBoxes = animationBoxes;
        this.leftAnimationCycles = leftAnimationCycles;
        this.rightAnimationCycles = rightAnimationCycles;

/*
        this.dstRect = Rect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height);
*/

        this.paint = Paint();
        this.paint.color = Color.DKGRAY;
    }

    public fun move(m: CharacterMoveDirection) {
        this.moveDirection = m;

        when(m) {
            CharacterMoveDirection.RIGHT -> {
                this.xVelocity = 600;
            }
            CharacterMoveDirection.LEFT -> {
                this.xVelocity = -600;
            }
            CharacterMoveDirection.STATIONARY -> {
                this.xVelocity = 0;
            }
        }
    }

    public fun jump() {
        this.yVelocity = -1200;
    }

    public fun draw(canvas: Canvas) {
        this.calculateChanges();
        
        when (this.moveDirection) {
            CharacterMoveDirection.STATIONARY -> {
                canvas.drawBitmap(this.spriteBitmap, this.animationBoxes[1], this.hitbox, null);
            }
            CharacterMoveDirection.RIGHT -> {
                canvas.drawBitmap(this.spriteBitmap, this.animationBoxes[1], this.hitbox, null);
            }
            CharacterMoveDirection.LEFT -> {
                canvas.drawBitmap(this.spriteBitmap, this.animationBoxes[0], this.hitbox, null);
            }
        }
    }
}